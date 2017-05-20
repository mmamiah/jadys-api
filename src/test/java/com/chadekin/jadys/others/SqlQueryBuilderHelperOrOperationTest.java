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
package com.chadekin.jadys.others;

import static com.chadekin.jadys.commons.enums.JadysSqlOperation.AND;
import static com.chadekin.jadys.commons.enums.JadysSqlOperation.EQUAL;
import static com.chadekin.jadys.commons.enums.JadysSqlOperation.OR;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.text.IsEmptyString.emptyString;
import static org.junit.Assert.assertThat;

import com.chadekin.jadys.commons.enums.ConditionType;
import com.chadekin.jadys.commons.enums.JadysSqlOperation;
import com.chadekin.jadys.commons.expression.impl.SqlExpressionFactory;
import org.hamcrest.text.IsEmptyString;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit test for SqlQueryBuilderHelper.orOperation
 */
public class SqlQueryBuilderHelperOrOperationTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void shouldNotBuildExpressionWhenFirstParamIsNull(){
		// Act
		String expression = SqlExpressionFactory.newExpression().or(null, OR, 2).build();
		
		// Assert
		assertThat(expression, IsEmptyString.emptyString());
	}

	@Test
	public void shouldNotBuildExpressionWhenOperatorIsNull(){
		// Act
		String expression = SqlExpressionFactory.newExpression().or("param", null, 1).build();
		
		// Assert
		assertThat(expression, emptyString());
	}

	@Test
	public void shouldNotBuildExpressionWhenValueIsNull(){
		// Act
		String expression = SqlExpressionFactory.newExpression().or("param", OR, null).build();

		// Assert
		assertThat(expression, emptyString());
	}

	@Test
	public void shouldBuildComposedOperationForLogicalOperator(){
		// Arrange
		String param = "param";
		JadysSqlOperation operator = OR;
		Object value = "alpha=true";

		// Act
		String expression = SqlExpressionFactory.newExpression().or(param, operator, value).build();

		// Assert
		assertThat(OR.getType(), is(ConditionType.LOGICAL));
		assertThat(expression, is("OR param OR (alpha=true)"));
	}

	@Test
	public void shouldBuildComposedOperationForNonLogicalOperator(){
		// Arrange
		String param = "param";
		JadysSqlOperation operator = EQUAL;
		Object value = 2;

		// Act
		String expression = SqlExpressionFactory.newExpression().or(param, operator, value).build();

		// Assert
		assertThat(OR.getType(), is(ConditionType.LOGICAL));
		assertThat(expression, is("OR param=2"));
	}

//	@Test
//	public void shouldBuildEmptyExpressionWhenParamIsNullPlus(){
//		// Act
//		String expression = SqlQueryBuilderHelper.orOperation(null);
//		
//		// Assert
//		assertThat(expression, emptyString());
//	}

//	@Test
//	public void shouldBuildEmptyExpressionWhenParameterIsEmpty(){
//		// Act
//		String expression = SqlExpressionFactory.newExpression().or("param", AND, null).build();
//		String expression = SqlQueryBuilderHelper.orOperation(StringUtils.EMPTY);
//
//		// Assert
//		assertThat(expression, emptyString());
//	}

	@Test
	public void shouldNotBuildExpressionWhenValueIsNullPlus(){
		// Act
		String expression = SqlExpressionFactory.newExpression().or("param", AND, null).build();

		// Assert
		assertThat(expression, emptyString());
	}

//	@Test
//	public void shouldBuildComposedOperationForLogicalOperatorPlus(){
//		// Act
//		String expression = SqlQueryBuilderHelper.orOperation("isOk AND goodWeather");
//
//		// Assert
//		assertThat(OR.getType(), is(ConditionType.LOGICAL));
//		assertThat(expression, is("OR isOk AND goodWeather"));
//	}
//
//	@Test
//	public void shouldBuildComposedOperationForNonLogicalOperatorPlus(){
//		// Act
//		String expression = SqlQueryBuilderHelper.orOperation("age>26");
//
//		// Assert
//		assertThat(OR.getType(), is(ConditionType.LOGICAL));
//		assertThat(expression, is("OR age>26"));
//	}
	
}
