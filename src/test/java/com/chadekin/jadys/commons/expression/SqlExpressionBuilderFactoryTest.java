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
package com.chadekin.jadys.commons.expression;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.chadekin.jadys.commons.enums.JadysSqlOperation;
import com.chadekin.jadys.commons.expression.impl.SqlExpressionFactory;
import org.apache.commons.lang.StringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test Class for ExpressionBuilder
 */
public class SqlExpressionBuilderFactoryTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void shouldThrowIAEClauseWhenColumnNameIsNull(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		
		// Act
		SqlExpressionFactory.newExpression(null, JadysSqlOperation.NOT_EQUAL, "tony");
	}

	@Test
	public void shouldThrowIAEWhenClauseWhenColumnNameIsEmpty(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		
		// Arrange
		SqlExpressionFactory.newExpression(StringUtils.EMPTY, JadysSqlOperation.NOT_EQUAL, "tony");
	}

	@Test
	public void shouldThrowIEAClauseWhenOperatorIsNull(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		
		// Act
		SqlExpressionFactory.newExpression("name", null, "tony");
	}

	@Test
	public void shouldNotBuildExpressionClauseWhenValueIsNull(){
		// Arrange
		SqlExpressionFactory localBuilder = SqlExpressionFactory.newExpression("name", JadysSqlOperation.NOT_EQUAL, null);

		// Act
		String sql = localBuilder.build();

		// Assert
		assertThat(sql, is(StringUtils.EMPTY));
	}

	@Test
	public void shouldNotBuildExpressionClauseWhenValueIsEmpty(){
		// Arrange
		SqlExpressionFactory localBuilder = SqlExpressionFactory.newExpression("name", JadysSqlOperation.NOT_EQUAL, StringUtils.EMPTY);

		// Act
		String sql = localBuilder.build();

		// Assert
		assertThat(sql, is(StringUtils.EMPTY));
	}

	@Test
	public void shouldBuildExpressionClauseWhenNullValue(){
		// Arrange
		SqlExpressionFactory localBuilder = SqlExpressionFactory.newExpression("name", JadysSqlOperation.IS_NULL);

		// Act
		String sql = localBuilder.build();

		// Assert
		assertThat(sql, is("name IS NULL"));
	}

	@Test
	public void shouldBuildExpressionClauseWhenValueNullAndNOT(){
		// Arrange
		SqlExpressionFactory localBuilder = SqlExpressionFactory.newExpression("name", JadysSqlOperation.IS_NOT_NULL);

		// Act
		String sql = localBuilder.build();

		// Assert
		assertThat(sql, is("name IS NOT NULL"));
	}

	@Test
	public void shouldBuildExpressionClauseWhenNotInOperator(){
		// Arrange
		SqlExpressionFactory localBuilder = SqlExpressionFactory.newExpression("name", JadysSqlOperation.NOT_IN, "select id_ from cars");

		// Act
		String sql = localBuilder.build();

		// Assert
		assertThat(sql, is("name NOT IN (select id_ from cars)"));
	}
	
}
