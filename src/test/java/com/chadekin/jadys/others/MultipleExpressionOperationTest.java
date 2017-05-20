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

import static org.hamcrest.core.Is.is;
import static org.hamcrest.text.IsEmptyString.emptyString;
import static org.junit.Assert.assertThat;

import com.chadekin.jadys.commons.expression.impl.SqlExpressionFactory;
import com.chadekin.jadys.commons.enums.JadysSqlOperation;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unitest for SqlQueryBuilderHelper.nuildMultipleExpressionOperation
 */
public class MultipleExpressionOperationTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void shouldBuildEmptyStringWhenNull(){
		// Act
		String result = SqlExpressionFactory.newExpression().build();
		
		// Assert
		assertThat(result, emptyString());
	}

	@Test
	public void shouldBuildSimpleExpression(){
		// Act
		String result = SqlExpressionFactory.newExpression("age", JadysSqlOperation.EQUAL, 5).build();

		// Assert
		assertThat(result, is("age=5"));
	}

	@Test
	public void shouldBuildEmptyStringWhenExpressionIsNull(){
		// Act
		String result = SqlExpressionFactory.newExpression().and(null, null, null).build();

		// Assert
		assertThat(result, emptyString());
	}

	@Test
	public void shouldBuildEmptyStringWhenExpressionIsEmpty(){
		// Act
		String result = SqlExpressionFactory.newExpression().xor(null, null, null).build();

		// Assert
		assertThat(result, emptyString());
	}

	@Test
	public void shouldBuildComboExpressionWhenGivenOneExpression(){
		// Act
		String result = SqlExpressionFactory.newExpression().xor("age", JadysSqlOperation.EQUAL, 5).build();

		// Assert
		assertThat(result, is("XOR age=5"));
	}

	@Test
	public void shouldBuildComboExpressionWhenMultipleExpression(){
		// Act
		String result = SqlExpressionFactory.newExpression("age", JadysSqlOperation.EQUAL, 5)
											.xor("color", JadysSqlOperation.NOT_EQUAL, "red")
											.build();

		// Assert
		assertThat(result, is("age=5 XOR color<>'red'"));
	}
}
