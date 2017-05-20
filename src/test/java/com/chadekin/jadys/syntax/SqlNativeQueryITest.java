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

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.syntax.from.generic.FromClauseBuilder;
import com.chadekin.jadys.syntax.orderby.generic.impl.OrderByClauseBuilderImpl;
import com.chadekin.jadys.syntax.factory.DynamicSqlFactory;
import com.chadekin.jadys.syntax.select.generic.impl.SelectDefaultBuilder;
import com.chadekin.jadys.syntax.where.generic.WhereClauseExtendedBuilder;
import com.google.common.collect.Lists;
import org.junit.Test;

/**
 * SqlNativeQuery Integretion Test
 */
public class SqlNativeQueryITest {

	@Test
	public void shouldBuildSimpleNativeQuery(){
		// Arrange
		JadysSqlQueryBuilder builder = (JadysSqlQueryBuilder) DynamicSqlFactory.newQuery()
				.select("cus.*")
				.from("Customer").as("cus");

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("SELECT cus.* FROM Customer AS cus"));
		assertThat(builder, instanceOf(FromClauseBuilder.class));
		assertThat(builder.getChild(), nullValue());
	}

	@Test
	public void shouldBuildSimpleNativeQueryWithAlias(){
		// Arrange
		JadysSqlQueryBuilder builder = (JadysSqlQueryBuilder) DynamicSqlFactory.newQuery()
				.select("cr.*")
				.from("CalculationResult").as("cr");

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("SELECT cr.* FROM CalculationResult AS cr"));

		assertThat(builder, instanceOf(FromClauseBuilder.class));
		assertThat(builder.getParent(), instanceOf(SelectDefaultBuilder.class));
		assertThat(builder.getParent().getParent(), nullValue());
	}


	@Test
	public void shouldBuildNativeQueryWithWhere(){
		// Arrange
		JadysSqlQueryBuilder builder = (JadysSqlQueryBuilder) DynamicSqlFactory.newQuery()
				.select("cr.*")
				.from("CalculationResult").as("cr")
				.where("cr.companyId").equal(10154).and("cr.calculationId").equal(24501).and("cr.resultType").equal("PCL");
		
		// Act
		String sql = builder.build();
		
		// Assert
		assertThat(sql, is("" +
				"SELECT cr.* " +
				"FROM CalculationResult AS cr " +
				"WHERE cr.companyId=10154 AND cr.calculationId=24501 AND cr.resultType='PCL'"));

		assertThat(builder, instanceOf(WhereClauseExtendedBuilder.class));
		assertThat(builder.getParent(), instanceOf(FromClauseBuilder.class));
		assertThat(builder.getParent().getParent(), instanceOf(SelectDefaultBuilder.class));
		assertThat(builder.getParent().getParent().getParent(), nullValue());

	}

	@Test
	public void shouldBuildFullSqlNativeQuery(){
		// Arrange
		JadysSqlQueryBuilder builder = (JadysSqlQueryBuilder) DynamicSqlFactory.newQuery()
				.select("cus.firstName, cus.lastName")
				.from("customer").as("cus")
				.join("location", "loc").on("loc.id").equal("cus.locationId")
				.where("loc.country").like("DE").and("cus.poBox").equal(1531)
				.orderBy("loc.city", "loc.country").asc();
		
		// Act
		String sql = builder.build();
		
		// Assert
		assertThat(sql, is(
				"SELECT cus.firstName, cus.lastName " +
				"FROM customer AS cus " +
				"JOIN location loc ON loc.id=cus.locationId " +
				"WHERE loc.country LIKE '%DE%' AND cus.poBox=1531 " +
				"ORDER BY loc.city ASC, loc.country ASC"));
		
		assertThat(builder, instanceOf(OrderByClauseBuilderImpl.class));
		assertThat(builder.getParent(), instanceOf(WhereClauseExtendedBuilder.class));
		assertThat(builder.getParent().getParent(), instanceOf(FromClauseBuilder.class));
		assertThat(builder.getParent().getParent().getParent(), instanceOf(SelectDefaultBuilder.class));
		assertThat(builder.getChild(), nullValue());
	}

	@Test
	public void shouldBuildAggregateSqlQuery(){
		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select("cus.firstName, cus.lastName").count("cus.members").as("mbrs")
				.from("customer").as("cus")
				.join("location", "loc").on("loc.id").equal("cus.locationId")
				.where("loc.country").like("DE").and("loc.poBox").equal(1531)
				.groupBy("cus.area")
				.having("cus.area").not().equal("South").and().count("cus.area").greaterThan(2)
				.orderBy("cus.city")
				.build();

		// Assert
		assertThat(sql, is("" +
				"SELECT cus.firstName, cus.lastName, COUNT(cus.members) AS mbrs " +
				"FROM customer AS cus " +
				"JOIN location loc ON loc.id=cus.locationId " +
				"WHERE loc.country LIKE '%DE%' AND loc.poBox=1531 " +
				"GROUP BY cus.area " +
				"HAVING cus.area<>'South' AND COUNT(cus.area)>2 " +
				"ORDER BY cus.city"));
	}

	@Test
	public void shouldBuildAggregateSqlQueryAddingAllJointure(){
		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select("cus.firstName, cus.lastName").count("cus.members").as("mbrs")
				.from("customer").as("cus")
				.join("location", "loc").on("loc.id").equal("cus.locationId")
				.join("friend", "frd").on("frd.id").equal("loc.friendId")
				.join("car", "car").on("car.id").equal("frd.carId")
				.where("loc.country").like("DE").and("loc.poBox").equal(1531)
				.groupBy("cus.area")
				.having("cus.area").not().equal("South").and().count("cus.area").greaterThan(2)
				.orderBy("car.name")
				.build();

		// Assert
		assertThat(sql, is("" +
				"SELECT cus.firstName, cus.lastName, COUNT(cus.members) AS mbrs " +
				"FROM customer AS cus " +
				"JOIN location loc ON loc.id=cus.locationId " +
				"JOIN friend frd ON frd.id=loc.friendId " +
				"JOIN car car ON car.id=frd.carId " +
				"WHERE loc.country LIKE '%DE%' AND loc.poBox=1531 " +
				"GROUP BY cus.area " +
				"HAVING cus.area<>'South' AND COUNT(cus.area)>2 " +
				"ORDER BY car.name"));
	}

	@Test
	public void shouldBuildAggregateSqlQueryRemovingUnusedJointure(){
		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select("cus.firstName, cus.lastName").count("cus.members").as("mbrs")
				.from("customer").as("cus")
				.join("maintenance", "mtn").on("mtn.id").equal("frd.maintenanceId")
				.join("car", "car").on("car.id").equal("mtn.carId")
				.join("location", "loc").on("loc.id").equal("cus.locationId")
				.join("friend", "frd").on("frd.id").equal("loc.friendId")
				.where("loc.country").like("DE").and("loc.poBox").equal(1531)
				.groupBy("cus.area")
				.having("cus.area").not().equal("South").and().count("cus.area").greaterThan(2)
				.build();

		// Assert
		assertThat(sql, is("" +
				"SELECT cus.firstName, cus.lastName, COUNT(cus.members) AS mbrs " +
				"FROM customer AS cus " +
				"JOIN location loc ON loc.id=cus.locationId " +
				"WHERE loc.country LIKE '%DE%' AND loc.poBox=1531 " +
				"GROUP BY cus.area " +
				"HAVING cus.area<>'South' AND COUNT(cus.area)>2"));
	}

	@Test
	public void shouldBuildExtendedAggregateSqlQuery(){
		// Arrange
		Calendar calendar = Calendar.getInstance();
		LocalDate localDate = LocalDate.of(2016, Month.DECEMBER, 14);
		Date date =  Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		String internalSql = DynamicSqlFactory.newQuery()
				.lazy()
				.select("cus.Id_").count("cus.*").as("numberOfLocation")
				.from("dct_customer").as("cus")
				.join("dct_location", "loc").on("loc.customerId").equal("cus.id_")
				.where("cus.customerId").like(1258).and("cus.customerName").like("BSD GmbH").and("cus.externalId").like("456753")
				.and("cus.customerType").like(CustomerType.KEY_ACCOUNT).and("cus.modifiedDate").lessThanOrEqual(date).and("cus.modifiedByUserId").in(Lists.newArrayList(125,36,587))
				.groupBy("loc.customerId")
				.having().count("loc.customerId").equal(2)
				.orderBy("city")
				.build();

		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select().count()
				.from(internalSql).as("subQuery")
				.build();

		// Assert
		assertThat(sql, is("" +
				"SELECT COUNT(subQuery.*) " +
				"FROM " +
				"(SELECT cus.Id_, COUNT(cus.*) AS numberOfLocation " +
				"FROM dct_customer AS cus " +
				"JOIN dct_location loc ON loc.customerId=cus.id_ " +
				"WHERE cus.customerId LIKE '%1258%' " +
				"AND cus.customerName LIKE '%BSD GmbH%' " +
				"AND cus.externalId LIKE '%456753%' AND cus.customerType LIKE '%KEY_ACCOUNT%' " +
				"AND cus.modifiedDate<='2016-12-14' " +
				"AND cus.modifiedByUserId IN (125,36,587) " +
				"GROUP BY loc.customerId " +
				"HAVING COUNT(loc.customerId)=2 " +
				"ORDER BY city" +
				") AS subQuery"));
	}

	private enum CustomerType{
		KEY_ACCOUNT
	}

}
