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
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.syntax.groupby.generic.DefaultPostGroupByTerm;
import com.chadekin.jadys.syntax.having.generic.DefaultPostHavingTerm;
import com.chadekin.jadys.syntax.orderby.generic.DefaultPostOrderByTerm;
import com.chadekin.jadys.syntax.from.generic.FromClauseExtendedBuilder;
import com.chadekin.jadys.syntax.from.generic.impl.FromClauseBuilderImpl;
import com.chadekin.jadys.syntax.groupby.generic.GroupByBuilder;
import com.chadekin.jadys.syntax.where.generic.WhereClauseExtendedBuilder;
import org.apache.commons.lang.StringUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test class for GroupByBuilder
 */
public class GroupByBuilderImplTest<W extends WhereClauseExtendedBuilder<W, G, H, O>,
		X extends FromClauseExtendedBuilder<X, W, O>,
		G extends DefaultPostGroupByTerm<H>,
		H extends DefaultPostHavingTerm<H, O>,
		O extends DefaultPostOrderByTerm<O>> {

	FromClauseBuilderImpl<X, W, O> builder;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void init(){
		builder = FromClauseBuilderImpl.newFromStatement(null, "Users");
	}

	@Test
	public void shouldConfirmBuilderName(){
		// Act
		SqlLexical type = builder.getType();

		// Assert
		assertThat(type, CoreMatchers.is(SqlLexical.FROM));
		assertThat(builder.getChild(), nullValue());
	}

	@Test
	public void shouldBuildSimpleQuery(){
		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("FROM Users"));
		assertThat(builder.getChild(), nullValue());
	}

	@Test
	public void shouldThrowIaeWhenGroupByParameterIsNull(){
		// Act
		String sql = builder.groupBy(null).build();

		// Assert
		assertThat(sql, is("FROM Users"));
		assertThat(builder.getChild(), instanceOf(GroupByBuilder.class));
		assertThat(builder.getChild().getChild(), nullValue());
	}

	@Test
	public void shouldThrowIaeWhenGroupByParameterIsEmpty(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		// Act
		builder.groupBy(StringUtils.EMPTY);
	}

	@Test
	public void shouldBuildGroupByClause(){
		// Arrange
		builder.groupBy("firstName");

		// Act
		String sql = builder.build();

		//
		assertThat(sql, is("FROM Users GROUP BY firstName"));
		assertThat(builder.getChild(), instanceOf(GroupByBuilder.class));
		assertThat(builder.getChild().getChild(), nullValue());
	}
	
}
