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

import com.chadekin.jadys.commons.resourcekeys.JadysExceptionMessageKeys;
import com.chadekin.jadys.syntax.select.impl.DynamicSqlFactory;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *	SqlFunctionTest
 */
public class SqlFunctionProxyTest {

	@Rule
	public ExpectedException exception =  ExpectedException.none();

	@Test
	public void shouldBuildStatementContainingDistinct(){
		// Act
		String sql = DynamicSqlFactory.newQuery()
				.selectDistinct("surname")
				.as("name")
				.from("customers")
				.build();

		// Assert
		assertThat(sql, is("SELECT DISTINCT(surname) AS name FROM customers"));
	}

	@Test
	public void shouldThrowIAEWhenDistinctSqlInjection(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.SQL_INJECTION_DETECTED);

		// Arrange
		String injection = "john'; " +
				"INSERT INTO group_membership (user_id, group) " +
				"VALUES (SELECT user_id FROM users WHERE username='john', 'Administrator');";

		// Act
		String sql = DynamicSqlFactory.newQuery()
				.selectDistinct(injection)
				.from("customer")
				.build();

	}

	@Test
	public void shouldThrowIAEWhenDistinctBlankColumnName(){

		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.INVALID_COLUMN_NAME);

		// Act
		String sql = DynamicSqlFactory.newQuery()
				.selectDistinct(null)
				.from("customer")
				.build();
	}

	@Test
	public void shouldThrowIAEWhenSelectSqlInjection() {
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.SQL_INJECTION_DETECTED);

		// Arrange
		String injection = "john'; " +
				"INSERT INTO group_membership (user_id, group) " +
				"VALUES (SELECT user_id FROM users WHERE username='john', 'Administrator');";

		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select(injection)
				.from("customer")
				.build();
	}

	@Test
	public void shouldThrowIAEWhenColumnSqlInjection(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.SQL_INJECTION_DETECTED);

		// Arrange
		String injection = "john'; " +
				"INSERT INTO group_membership (user_id, group) " +
				"VALUES (SELECT user_id FROM users WHERE username='john', 'Administrator');";

		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select().column(injection)
				.from("customer")
				.build();

	}

	@Test
	public void shouldSelectColumnWithAliasWhenSelectAll(){
		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select().column("DoB").as("dateOfBirth")
				.from("students")
				.build();

		// Assert
		assertThat(sql, is("SELECT DoB AS dateOfBirth FROM students"));
	}

	@Test
	public void shouldSelectColumnWithAliasWhenSelect(){
		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select("name").column("DoB").as("dateOfBirth")
				.from("students")
				.build();
		
		// Assert
		assertThat(sql, is("SELECT name, DoB AS dateOfBirth FROM students"));
	}
	
	@Test
	public void shouldSelectManycolumnWithAliasWhenSelectWithInitialMultipleArguments(){
		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select("name", "surname").column("DoB").as("dateOfBirth")
				.from("students")
				.build();

		// Assert
		assertThat(sql, is("SELECT name, surname, DoB AS dateOfBirth FROM students"));
	}

	@Test
	public void shouldSelectMultipleColumnWithAliasWhenSelect(){
		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select("name").column("DoB").as("dateOfBirth").column("homeAddress").as("home")
				.from("students")
				.build();

		// Assert
		assertThat(sql, is("SELECT name, DoB AS dateOfBirth, homeAddress AS home FROM students"));
	}

	@Test
	public void shouldBuildStatementContainingUcase(){
		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select("surname").ucase("familyName").as("name")
				.from("customers")
				.build();
		
		// Assert
		assertThat(sql, is("SELECT surname, UCASE(familyName) AS name FROM customers"));
	}
	
	@Test
	public void shouldThrowIAEWhenUcaseSqlInjection(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.SQL_INJECTION_DETECTED);
		
		// Arrange
		String injection = "john'; " +
				"INSERT INTO group_membership (user_id, group) " +
				"VALUES (SELECT user_id FROM users WHERE username='john', 'Administrator');"; 
		
		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select().ucase(injection)
				.from("customer")
				.build();
		
	}
	
	@Test
	public void shouldThrowIAEWhenUcaseBlankColumnName(){

		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.INVALID_COLUMN_NAME);

		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select().ucase(null)
				.from("customer")
				.build();
	}

	@Test
	public void shouldBuildStatementContainingAvg(){
		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select("surname").avg("familySize").as("size")
				.from("customers")
				.build();

		// Assert
		assertThat(sql, is("SELECT surname, AVG(familySize) AS size FROM customers"));
	}

	@Test
	public void shouldThrowIAEWhenAvgSqlInjection(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.SQL_INJECTION_DETECTED);

		// Arrange
		String injection = "john'; " +
				"INSERT INTO group_membership (user_id, group) " +
				"VALUES (SELECT user_id FROM users WHERE username='john', 'Administrator');";

		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select().avg(injection)
				.from("customer")
				.build();

	}

	@Test
	public void shouldThrowIAEWhenAvgBlankColumnName(){

		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.INVALID_COLUMN_NAME);

		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select().avg(null)
				.from("customer")
				.build();
	}

	@Test
	public void shouldBuildStatementContainingSum(){
		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select("surname").sum("familySize").as("size")
				.from("customers")
				.build();

		// Assert
		assertThat(sql, is("SELECT surname, SUM(familySize) AS size FROM customers"));
	}

	@Test
	public void shouldThrowIAEWhenSumSqlInjection(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.SQL_INJECTION_DETECTED);

		// Arrange
		String injection = "john'; " +
				"INSERT INTO group_membership (user_id, group) " +
				"VALUES (SELECT user_id FROM users WHERE username='john', 'Administrator');";

		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select().sum(injection)
				.from("customer")
				.build();

	}

	@Test
	public void shouldThrowIAEWhenSumBlankColumnName(){

		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.INVALID_COLUMN_NAME);

		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select().sum(null)
				.from("customer")
				.build();
	}

	@Test
	public void shouldBuildStatementContainingMin(){
		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select("surname").min("familySize").as("smallestSize")
				.from("customers")
				.build();

		// Assert
		assertThat(sql, is("SELECT surname, MIN(familySize) AS smallestSize FROM customers"));
	}

	@Test
	public void shouldThrowIAEWhenMinSqlInjection(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.SQL_INJECTION_DETECTED);

		// Arrange
		String injection = "john'; " +
				"INSERT INTO group_membership (user_id, group) " +
				"VALUES (SELECT user_id FROM users WHERE username='john', 'Administrator');";

		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select().min(injection)
				.from("customer")
				.build();

	}

	@Test
	public void shouldThrowIAEWhenMinBlankColumnName(){

		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.INVALID_COLUMN_NAME);

		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select().min(null)
				.from("customer")
				.build();
	}

	@Test
	public void shouldBuildStatementContainingMax(){
		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select("surname").max("familySize").as("biggestSize")
				.from("customers")
				.build();

		// Assert
		assertThat(sql, is("SELECT surname, MAX(familySize) AS biggestSize FROM customers"));
	}

	@Test
	public void shouldThrowIAEWhenMaxSqlInjection(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.SQL_INJECTION_DETECTED);

		// Arrange
		String injection = "john'; " +
				"UPDATE table_name " +
				"SET column1 = value1, column2 = value2 " +
				"WHERE condition=1";
		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select().max(injection)
				.from("customer")
				.build();

	}

	@Test
	public void shouldThrowIAEWhenMaxBlankColumnName(){

		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.INVALID_COLUMN_NAME);

		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select().max(null)
				.from("customer")
				.build();
	}

	@Test
	public void shouldBuildStatementContainingRound(){
		// Act
		String sql = DynamicSqlFactory.newQuery().select("surname").round("price", 0).as("roundedPrice")
				.from("customers")
				.build();

		// Assert
		assertThat(sql, is("SELECT surname, ROUND(price,0) AS roundedPrice FROM customers"));
	}

	@Test
	public void shouldThrowIAEWhenRoundSqlInjection(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.SQL_INJECTION_DETECTED);

		// Arrange
		String injection = "john'; " +
				"DELETE FROM table_name " +
				"WHERE condition; ";

		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select().round(injection, 0)
				.from("customer")
				.build();

	}

	@Test
	public void shouldThrowIAEWhenRoundBlankColumnName(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.INVALID_COLUMN_NAME);

		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select().round(null, 0)
				.from("customer")
				.build();
	}

}
