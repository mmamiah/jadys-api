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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.commons.enums.JadysSqlOperation;
import com.chadekin.jadys.commons.expression.impl.SqlExpressionFactory;
import com.chadekin.jadys.sqldialect.DefaultPostGroupByTerm;
import com.chadekin.jadys.sqldialect.DefaultPostHavingTerm;
import com.chadekin.jadys.sqldialect.DefaultPostOrderByTerm;
import com.chadekin.jadys.syntax.having.HavingBuilder;
import com.chadekin.jadys.syntax.orderby.OrderByClauseBuilder;
import com.chadekin.jadys.syntax.where.WhereClauseBuilder;
import com.chadekin.jadys.syntax.where.WhereClauseExtendedBuilder;
import com.chadekin.jadys.syntax.where.impl.WhereClauseBuilderImpl;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit test for WhereClauseBuilder
 */
public class WhereClauseBuilderTest<W extends WhereClauseExtendedBuilder<W, G, H, O>, O extends DefaultPostOrderByTerm<O>,
		G extends DefaultPostGroupByTerm<H>, H extends DefaultPostHavingTerm<H, O>> {
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	private WhereClauseBuilderImpl<W, O> builder;
	
	@Before
	public void init(){
		builder = WhereClauseBuilderImpl.<W, O>newWhereStatement(null);
	}
	
	@Test
	public void shouldBuilderConfirmName(){
		// Act
		SqlLexical type = builder.getType();
		
		// Assert
		assertThat(type, CoreMatchers.is(SqlLexical.WHERE));
		assertThat(builder.getChild(), nullValue());
		
	}
	
	@Test
	public void shouldBuildQueryWithANDConjunction(){
		// Arrange
		builder.where("age").lessThan(30).and("weight").lessThanOrEqual(120);
		
		// Act
		String sql = builder.build();
		
		// Assert
		assertThat(sql, is("WHERE age<30 AND weight<=120"));
		assertThat(builder, instanceOf(WhereClauseBuilder.class));
		assertThat(builder.getChild(), instanceOf(WhereClauseExtendedBuilder.class));
		assertThat(builder.getChild().getChild(), nullValue());
	}
	
	@Test
	public void shouldBuildQueryWithORConjunction(){
		// Arrange
		builder.where("age").lessThan(30).or("area").not().equal(852);
		
		// Act
		String sql = builder.build();
		
		// Assert
		assertThat(sql, is("WHERE age<30 OR area<>852"));
		assertThat(builder, instanceOf(WhereClauseBuilder.class));
		assertThat(builder.getChild(), instanceOf(WhereClauseExtendedBuilder.class));
		assertThat(builder.getChild().getChild(), nullValue());
	}
	
	@Test
	public void shouldBuildCombinedConjunction(){
		// Arrange
		builder.where("age").lessThan(30)
				.and("area").greaterThanOrEqual(741)
				.or("weight").greaterThan(120)
				.orderBy("size").desc();
		
		// Act
		String sql = builder.build();
		
		// Arrange
		assertThat(sql, is("WHERE age<30 AND area>=741 OR weight>120 ORDER BY size DESC"));
		assertThat(builder, instanceOf(WhereClauseBuilder.class));
		assertThat(builder.getChild(), instanceOf(WhereClauseExtendedBuilder.class));
		assertThat(builder.getChild().getChild(), instanceOf(OrderByClauseBuilder.class));
		assertThat(builder.getChild().getChild().getChild(), nullValue());
	}
	
	@Test
	public void shouldBuildOrderedQueryAsc(){
		// Arrange
		builder.where("age").lessThan(30)
				.and("area").greaterThanOrEqual(741)
				.or("weight").greaterThan(120)
				.orderBy("size").asc();

		// Act
		String sql = builder.build();

		// Arrange
		assertThat(sql, is("WHERE age<30 AND area>=741 OR weight>120 ORDER BY size ASC"));
		assertThat(builder, instanceOf(WhereClauseBuilder.class));
		assertThat(builder.getChild(), instanceOf(WhereClauseExtendedBuilder.class));
		assertThat(builder.getChild().getChild(), instanceOf(OrderByClauseBuilder.class));
		assertThat(builder.getChild().getChild().getChild(), nullValue());
	}

	@Test
	public void shouldBuildOrderedQueryDesc(){
		// Arrange
		builder.where("age").lessThan(30)
				.and("area").greaterThanOrEqual(741)
				.or("size").greaterThan(120)
				.orderBy("weight").desc();

		// Act
		String sql = builder.build();

		// Arrange
		assertThat(sql, is("WHERE age<30 AND area>=741 OR size>120 ORDER BY weight DESC"));
		assertThat(builder, instanceOf(WhereClauseBuilder.class));
		assertThat(builder.getChild(), instanceOf(WhereClauseExtendedBuilder.class));
		assertThat(builder.getChild().getChild(), instanceOf(OrderByClauseBuilder.class));
		assertThat(builder.getChild().getChild().getChild(), nullValue());
	}
	
	@Test
	public void shouldBuildCombinedExpressionQueryWhenLogicalOperatorIsOR(){
		// Arrange
		SqlExpressionFactory expression = SqlExpressionFactory.newExpression("name", JadysSqlOperation.EQUAL, "alex").or("weight", JadysSqlOperation.GREATER_THAN_OR_EQUAL, 34);
		builder.where("age").greaterThanOrEqual(20).and(expression);
		
		// Act
		String sql = builder.build();
		
		// Assert
		assertThat(sql, is("WHERE age>=20 AND (name='alex' OR weight>=34)"));
		assertThat(builder, instanceOf(WhereClauseBuilder.class));
		assertThat(builder.getChild(), instanceOf(WhereClauseExtendedBuilder.class));
		assertThat(builder.getChild().getChild(), nullValue());
	}

	@Test
	public void shouldBuildCombinedExpressionQueryWhenLogicalOperatorIsXOR(){
		// Arrange
		SqlExpressionFactory expression = SqlExpressionFactory.newExpression("name", JadysSqlOperation.LIKE, "tony")
																.xor("weight", JadysSqlOperation.GREATER_THAN_OR_EQUAL, 70);
		builder.where("age").lessThan(25).and(expression);
		
		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("WHERE age<25 AND (name LIKE '%tony%' XOR weight>=70)"));
		assertThat(builder, instanceOf(WhereClauseBuilder.class));
		assertThat(builder.getChild(), instanceOf(WhereClauseExtendedBuilder.class));
		assertThat(builder.getChild().getChild(), nullValue());
	}

	@Test
	public void shouldOmitInvalidExpressionForCombinedExpressionQuery(){
		// Arrange
		SqlExpressionFactory expression = SqlExpressionFactory.newExpression("name", JadysSqlOperation.LIKE, null)
																.and("weight", JadysSqlOperation.GREATER_THAN_OR_EQUAL, 70);
		builder.where("age").lessThan(25).and(expression);
		
		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("WHERE age<25 AND (weight>=70)"));
		assertThat(builder, instanceOf(WhereClauseBuilder.class));
		assertThat(builder.getChild(), instanceOf(WhereClauseExtendedBuilder.class));
		assertThat(builder.getChild().getChild(), nullValue());
	}

	@Test
	public void shouldBuildQueryWithHavingClauseWhenEqualityOperator(){
		// Arrange
		builder.where("age").lessThan(25)
				.having("weight").greaterThanOrEqual(70);

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("WHERE age<25 HAVING weight>=70"));
		assertThat(builder, instanceOf(WhereClauseBuilder.class));
		assertThat(builder.getChild(), instanceOf(WhereClauseExtendedBuilder.class));
		assertThat(builder.getChild().getChild(), instanceOf(HavingBuilder.class));
		assertThat(builder.getChild().getChild().getChild(), nullValue());
	}

	@Test
	public void shouldBuildQueryWithHavingClauseWhenCustomExpression(){
		// Arrange
		builder.where("age").lessThan(25)
				.having("name").equal("tony");

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("WHERE age<25 HAVING name='tony'"));
		assertThat(builder, instanceOf(WhereClauseBuilder.class));
		assertThat(builder.getChild(), instanceOf(WhereClauseExtendedBuilder.class));
		assertThat(builder.getChild().getChild(), instanceOf(HavingBuilder.class));
		assertThat(builder.getChild().getChild().getChild(), nullValue());
	}
	
	@Test
	public void shouldBuildLogicalOperator(){
		// Arrange
		SqlExpressionFactory expression = SqlExpressionFactory.newExpression("standardCode", JadysSqlOperation.EQUAL, "ISO_900")
																.and("accreditationBodyCode", JadysSqlOperation.EQUAL, "DAKKS")
																.and("standardVersion", JadysSqlOperation.EQUAL, "2015");
		builder.where("companyId").equal("1254")
				.and("calculationId").equal("96547")
				.or(expression)
				.having().min("productStartYear").greaterThan(2010);

		// Act
		String sql = builder.build();
		
		// Assert
		assertThat(sql, is("WHERE companyId='1254' AND calculationId='96547' OR (standardCode='ISO_900' " +
				"AND accreditationBodyCode='DAKKS' AND standardVersion='2015') " +
				"HAVING MIN(productStartYear)>2010"));
		assertThat(sql, not(nullValue()));
	}
	
}
