/**
 *                     GNU General Public License (GPL)
 *                        version 3.0, 29 June 2007
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 *
 * Copyright (C) 2017 Marc Mamiah.
 * License GPLv3+: GNU GPL version 3
 */
package com.chadekin.jadys.syntax;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.commons.AbstractSqlQueryBuilder;
import com.chadekin.jadys.syntax.select.impl.DynamicSqlFactory;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Detailed ITest for SqlNativeQuery
 */
public class SqlNativeQuerySectionDetailedITest extends SqlNativeQueryAssertion {

	@Test
	public void shouldBuildAllSqlQuerySection(){
		// Arrange
		Calendar calendar = Calendar.getInstance();
		LocalDate localDate = LocalDate.of(2016, Month.DECEMBER, 14);
		Date date =  Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		String internalSql = DynamicSqlFactory.newQuery()
				.select("cus.Id_").count("*", "cus.numberOfLocation")
				.from("dct_customer").as("cus")
				.join("dct_location", "loc").on("loc.customerId").equal("cus.id_")
				.where("cus.companyId").equal(142576)
				.and("cus.customerId").equal(1258)
				.and("cus.customerName").like("BSD GmbH")
				.and("zipCode").like(StringUtils.EMPTY)
				.and("cus.city").like("Paris")
				.and("country").like(null)
				.and("cus.externalId").like("456753")
				.and("cus.customerType").like(CustomerType.KEY_ACCOUNT)
				.and("cus.modifiedDate").greaterThanOrEqual("2016-10-14")
				.and("cus.modifiedDate").lessThanOrEqual(date)
				.and("cus.modifiedByUserId").in(Lists.newArrayList(125,36,587))
				.groupBy("loc.customerId")
				.having("COUNT(loc.customerId)").equal(2)
				.orderBy("cus.externalId").asc()
				.build();

		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select().count()
				.from(internalSql).as("subQuery").build();

		// Assert
		assertThatSelect(sql);
		assertThatFrom(sql);
		assertThatJoin(sql);
		assertThatWhere(sql);
		assertThatAnd(sql);
		assertThatGroupBy(sql);
		assertThatHaving(sql);
		assertThatOrderBy(sql);
		assertThatAll(sql);
	}
	
	@Test
	public void shouldExcludeAllNonNecessaryStatement(){
		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select("calculation.*")
				.from("Calculation").as("calculation")
				.join("Customer", "customer").on("customer.Id_").equal("calculation.customerId")
				.join("ProductCalculation", "pc").on("pc.calculationId").equal("calculation.id_")
				.join("Product", "product").on("product.Id_").equal("pc.productId")
				.where("calculation.companyId").equal(125478)
				.and("customer.Id_").equal(96325)
				.and("customer.name").like("Antony")
				.and("calculation.name").like(null)
				.and("calculation.version").like('5')
				.and("calculation.status").in(null)
				.and("calculation.createdDate").greaterThanOrEqual(null)
				.and("calculation.createdDate").lessThanOrEqual(null)
				.and("calculation.createdByUserId").in(Lists.newArrayList(1,5,78))
				.and("product.id_").in(null)
				.orderBy(null).desc()
				.build();
		
		// Assert
		assertThat(sql, is("" +
				"SELECT calculation.* " +
				"FROM Calculation AS calculation " +
				"JOIN Customer customer ON customer.Id_=calculation.customerId " +
				"WHERE calculation.companyId=125478 AND customer.Id_=96325 AND customer.name LIKE '%Antony%' " +
				"AND calculation.version LIKE '%5%' AND calculation.createdByUserId IN (1,5,78)"));
	}
	
	@Test
	public void shouldConsiderJoinedTableWhenParameterUsedInOtherClause(){
		JadysSqlQueryBuilder builder = (JadysSqlQueryBuilder) DynamicSqlFactory.newQuery()
				.select("product.id_, product.name, product.dctCode, cr.resultFte, cr.year")
				.from("CalculationResult").as("cr")
				.join("ProductCustomCalculationItem", "pcci").on("pcci.id_").equal("cr.productCustomCalculationItemId")
				.join("ProductCustomCalculation", "pcc").on("pcc.id_").equal("pcci.productCustomCalculationId")
				.join("Beta", "bet").on("bet.id_").equal("pcc.betaId")
				.join("Alpha", "alp").on("alp.id_").equal("bet.alphaId")
				.join("ProductCustom", "pc").on("pc.id_").equal("alp.productCustomId")
				.join("Product", "product").on("product.id_").equal("pc.associatedProductId")
				.where("cr.resultType").equal("PCCI").and("cr.companyId").equal(123456)
				.and("pc.calculationId").equal(123456);
		
		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("" +
				"SELECT product.id_, product.name, product.dctCode, cr.resultFte, cr.year " +
				"FROM CalculationResult AS cr " +
				"JOIN ProductCustomCalculationItem pcci ON pcci.id_=cr.productCustomCalculationItemId " +
				"JOIN ProductCustomCalculation pcc ON pcc.id_=pcci.productCustomCalculationId " +
				"JOIN Beta bet ON bet.id_=pcc.betaId JOIN Alpha alp ON alp.id_=bet.alphaId " +
				"JOIN ProductCustom pc ON pc.id_=alp.productCustomId " +
				"JOIN Product product ON product.id_=pc.associatedProductId " +
				"WHERE cr.resultType='PCCI' AND cr.companyId=123456 AND pc.calculationId=123456"));
	}

	@Test
	public void shouldConsiderJoinedTableWhenParameterUsedInSelectClauseWithoutWhereClause(){
		JadysSqlQueryBuilder builder = (JadysSqlQueryBuilder) DynamicSqlFactory.newQuery()
				.select("product.id_, product.name, product.dctCode, cr.resultFte, cr.year")
				.from("CalculationResult").as("cr")
				.join("ProductCustomCalculationItem", "pcci").on("pcci.id_").equal("cr.productCustomCalculationItemId")
				.join("ProductCustomCalculation", "pcc").on("pcc.id_").equal("pcci.productCustomCalculationId")
				.join("ProductCustom", "pc").on("pc.id_").equal("pcc.productCustomId")
				.join("Product", "product").on("product.id_").equal("pc.associatedProductId");

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("" +
				"SELECT product.id_, product.name, product.dctCode, cr.resultFte, cr.year " +
				"FROM CalculationResult AS cr " +
				"JOIN ProductCustomCalculationItem pcci ON pcci.id_=cr.productCustomCalculationItemId " +
				"JOIN ProductCustomCalculation pcc ON pcc.id_=pcci.productCustomCalculationId " +
				"JOIN ProductCustom pc ON pc.id_=pcc.productCustomId " +
				"JOIN Product product ON product.id_=pc.associatedProductId"));
	}
	
	private enum CustomerType{
		KEY_ACCOUNT
	}
}
