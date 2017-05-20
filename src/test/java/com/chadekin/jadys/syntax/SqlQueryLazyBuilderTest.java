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
import static org.hamcrest.text.IsEmptyString.emptyString;

import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.syntax.select.SelectClauseBuilder;
import com.chadekin.jadys.syntax.select.impl.DynamicSqlFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Native Query Builder Lazy unit test
 */
public class SqlQueryLazyBuilderTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void shouldNotBeLazyWheDefaultBuilder(){
		// Arrange
		JadysSqlQueryBuilder builder = (JadysSqlQueryBuilder) DynamicSqlFactory.newQuery().select();

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, emptyString());
		assertLazy(builder, false);
	}
	
	@Test
	public void shouldNotBeLazyWhenNotLazySimpleQuery(){
		// Arrange
		JadysSqlQueryBuilder builder = (JadysSqlQueryBuilder) DynamicSqlFactory.newQuery()
				.select()
				.from("users");
		
		// Act
		String sql = builder.build();
				
		// Assert
		assertThat(sql, is("SELECT * FROM users"));
		
		JadysSqlQueryBuilder parent = builder.getParent();
		assertThat(parent, not(nullValue()));
		assertThat(parent, instanceOf(SelectClauseBuilder.class));
		assertLazy(builder, false);
	}

	@Test
	public void shouldBeLazyWhenLazyAtSelectQuery(){
		// Arrange
		JadysSqlQueryBuilder builder = (JadysSqlQueryBuilder) DynamicSqlFactory.newQuery()
				.lazy()
				.select()
				.from("users");

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("SELECT * FROM users"));

		JadysSqlQueryBuilder parent = builder.getParent();
		assertThat(parent, not(nullValue()));
		assertThat(parent, instanceOf(SelectClauseBuilder.class));

		assertLazy(builder, true);
	}

	@Test
	public void shouldBeLazyWhenLazyBeforeJoinClauseIsLazy(){
		// Arrange
		JadysSqlQueryBuilder builder = (JadysSqlQueryBuilder) DynamicSqlFactory.newQuery()
				.lazy()
				.select("us.*")
				.from("users").as("us")
				.join("address", "ad").on("ad.id").equal("us.addressId");

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("SELECT us.* FROM users AS us JOIN address ad ON ad.id=us.addressId"));
		assertLazy(builder, true);
	}

	@Test
	public void shouldBeLazyWhenLazyAfterJoinClauseIsLazy(){
		// Arrange
		JadysSqlQueryBuilder builder = (JadysSqlQueryBuilder) DynamicSqlFactory.newQuery()
				.lazy()
				.select("us.*")
				.from("users").as("us")
				.join("address", "ad").on("ad.id").equal("us.addressId");

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("SELECT us.* FROM users AS us JOIN address ad ON ad.id=us.addressId"));
		assertLazy(builder, true);
	}

	@Test
	public void shouldBeLazyWhenLazyBeforeWhereClauseIsLazy(){
		// Arrange
		JadysSqlQueryBuilder builder = (JadysSqlQueryBuilder) DynamicSqlFactory.newQuery()
				.lazy()
				.select("us.*")
				.from("users").as("us")
				.join("address", "ad").on("ad.id").equal("us.addressId")
				.where("us.name").equal("john");

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("" +
				"SELECT us.* " +
				"FROM users AS us " +
				"JOIN address ad ON ad.id=us.addressId " +
				"WHERE us.name='john'"));
		assertLazy(builder, true);
	}

	@Test
	public void shouldIgnoreNotUsedJoinStatementWhenNotLazy(){
		// Arrange
		JadysSqlQueryBuilder builder = (JadysSqlQueryBuilder) DynamicSqlFactory.newQuery()
				.select("us.*")
				.from("users").as("us")
				.join("address", "ad").on("ad.id").equal("us.addressId")
				.where("us.name").equal("john");

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("" +
				"SELECT us.* " +
				"FROM users AS us " +
				"WHERE us.name='john'"));
		assertLazy(builder, false);
	}

	@Test
	public void shouldBeLazyWhenLazyBeforeGroupByClauseIsLazy(){
		// Arrange
		JadysSqlQueryBuilder builder = (JadysSqlQueryBuilder) DynamicSqlFactory.newQuery()
				.lazy()
				.select("us.*")
				.from("users").as("us")
				.join("address", "ad").on("ad.id").equal("us.addressId")
				.where("us.name").equal("john").and("ad.floor").equal("4")
				.groupBy("us.age");

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("" +
				"SELECT us.* " +
				"FROM users AS us " +
				"JOIN address ad ON ad.id=us.addressId " +
				"WHERE us.name='john' AND ad.floor='4' " +
				"GROUP BY us.age"));
		assertLazy(builder, true);
	}

	@Test
	public void shouldBuildQueryWhenLazyAndJoinedTableNotUsed(){
		// Arrange
		JadysSqlQueryBuilder builder = (JadysSqlQueryBuilder) DynamicSqlFactory.newQuery()
				.lazy()
				.select("us.*")
				.from("users").as("us")
				.join("address", "ad").on("ad.id").equal("us.addressId")
				.where("us.name").equal("john")
				.groupBy("us.age")
				.having("us.age").greaterThan(12);

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("" +
				"SELECT us.* " +
				"FROM users AS us " +
				"JOIN address ad ON ad.id=us.addressId " +
				"WHERE us.name='john' " +
				"GROUP BY us.age " +
				"HAVING us.age>12"));
		assertLazy(builder, true);
	}

	@Test
	public void shouldThrowIaeWhenLazyAndInvalidAlias(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Invalid or Missing alias detected: 'AND pt.floor='4''");
		
		// Arrange
		JadysSqlQueryBuilder builder = (JadysSqlQueryBuilder) DynamicSqlFactory.newQuery()
				.lazy()
				.select()
				.from("users").as("us")
				.join("address", "ad").on("ad.id").equal("us.addressId")
				.where("us.name").equal("john").and("pt.floor").equal("4")
				.groupBy("us.age")
				.having("us.age").greaterThan(12)
				.orderBy("us.id");

		// Act
		builder.build();

	}

	@Test
	public void shouldBeLazyWhenLazyBeforeOrderByClauseIsLazy(){
		// Arrange
		JadysSqlQueryBuilder builder = (JadysSqlQueryBuilder) DynamicSqlFactory.newQuery()
				.select("us.*")
				.from("users").as("us")
				.join("address", "ad").on("ad.id").equal("us.addressId")
				.where("us.name").equal("john")
				.groupBy("us.age")
				.having("us.age").greaterThan(12)
				.orderBy("us.id");

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("" +
				"SELECT us.* " +
				"FROM users AS us " +
				"WHERE us.name='john' " +
				"GROUP BY us.age " +
				"HAVING us.age>12 " +
				"ORDER BY us.id"));
		assertLazy(builder, false);
	}

	private static void assertLazy(JadysSqlQueryBuilder builder, boolean lazy){
		JadysSqlQueryBuilder target = builder;
		while(!(target instanceof SelectClauseBuilder)){
			target = target.getParent();
		}
		assertThat(((SelectClauseBuilder)target).isLazy(), is(lazy));
	}

}
