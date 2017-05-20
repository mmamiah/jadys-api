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
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;

import com.chadekin.jadys.sqldialect.DefaultPostOrderByTerm;
import com.chadekin.jadys.syntax.orderby.impl.OrderByClauseBuilderImpl;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit test for OrderByClauseBuilder
 */
public class OrderByClauseBuilderImplTest<O extends DefaultPostOrderByTerm<O>> {
	
	private OrderByClauseBuilderImpl<O> builder;
	
	@Before
	public void init(){
		builder = OrderByClauseBuilderImpl.newStatement(null);
	}

	@Test
	public void shouldThrowIaeWhenOrderStatementIsNull(){
		// Act
		String sql = builder.orderBy(null, null).build();
		
		// Assert
		assertThat(sql, is(emptyString()));
	}

	@Test
	public void shouldThrowIaeWhenOrderStatementIsEmptyForString(){
		// Act
		String sql = builder.orderBy(null, StringUtils.EMPTY).build();

		// Assert
		assertThat(sql, is(emptyString()));
	}

	@Test
	public void shouldThrowIaeWhenOrderStatementIsNullAndOrderAsc(){
		// Act
		String sql = builder.orderBy(null).asc().build();

		// Assert
		assertThat(sql, is(emptyString()));
	}

	@Test
	public void shouldThrowIaeWhenOrderStatementIsEmptyAndOrderAsc(){
		// Act
		String sql = builder.orderBy(StringUtils.EMPTY).asc().build();

		// Assert
		assertThat(sql, is(emptyString()));
	}

	@Test
	public void shouldThrowIaeWhenOrderStatementIsNullAndOrderDesc(){
		// Act
		String sql = builder.orderBy(null).desc().build();

		// Assert
		assertThat(sql, is(emptyString()));
	}

	@Test
	public void shouldThrowIaeWhenOrderStatementIsEmptyAndOrderDesc(){
		// Act
		String sql = builder.orderBy(StringUtils.EMPTY).desc().build();

		// Assert
		assertThat(sql, is(emptyString()));
	}

	@Test
	public void shouldBuildOrderByStatement(){
		// Arrange
		builder.orderBy("countryName").asc();

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("ORDER BY countryName ASC"));
		assertThat(builder.getChild(), nullValue());
	}

	@Test
	public void shouldBuildOrderByStatementWhenOrderTypeDesc(){
		// Arrange
		builder.orderBy("countryName").desc();

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("ORDER BY countryName DESC"));
		assertThat(builder.getChild(), nullValue());
	}

	@Test
	public void shouldBuildOrderByMultiStatement(){
		// Arrange
		builder.orderBy(null, "countryName", "customerName");

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("ORDER BY countryName, customerName"));
		assertThat(builder.getChild(), nullValue());
	}

	@Test
	public void shouldBuildOrderByAscMultiStatement(){
		// Arrange
		builder.orderBy("countryName", "firstName", "customerName").asc();

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("ORDER BY countryName ASC, firstName ASC, customerName ASC"));
		assertThat(builder.getChild(), nullValue());
	}

	@Test
	public void shouldBuildOrderByDescMultiStatement(){
		// Arrange
		builder.orderBy("lastName", "customerName", "countryName").desc();

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("ORDER BY lastName DESC, customerName DESC, countryName DESC"));
	}

	@Test
	public void shouldMixAllOrderByModeAscDesc(){
		// Arrange
		builder.orderBy("countryName", "firstName", "customerName").asc()
				.orderBy("lastName", "customerName", "countryName").desc();

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("ORDER BY countryName ASC, firstName ASC, customerName ASC, lastName DESC, customerName DESC, countryName DESC"));
	}
	
}
