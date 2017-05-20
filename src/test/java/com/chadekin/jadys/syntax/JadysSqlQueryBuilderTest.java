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
import static org.hamcrest.text.IsEmptyString.emptyString;

import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.syntax.groupby.generic.DefaultPostGroupByTerm;
import com.chadekin.jadys.syntax.having.generic.DefaultPostHavingTerm;
import com.chadekin.jadys.syntax.orderby.generic.DefaultPostOrderByTerm;
import com.chadekin.jadys.syntax.from.generic.FromClauseTerm;
import com.chadekin.jadys.syntax.from.generic.FromClauseBuilder;
import com.chadekin.jadys.syntax.from.generic.FromClauseExtendedBuilder;
import com.chadekin.jadys.syntax.select.generic.SelectClauseBuilder;
import com.chadekin.jadys.syntax.factory.DynamicSqlFactory;
import com.chadekin.jadys.syntax.select.generic.impl.SelectDefaultBuilder;
import com.chadekin.jadys.syntax.where.generic.WhereClauseExtendedBuilder;
import org.apache.commons.lang.StringUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit test for SelectClauseBuilder
 */
public class JadysSqlQueryBuilderTest<T,
		FX extends FromClauseExtendedBuilder<FX, W, O>, F extends FromClauseTerm<W, O, FX>,
		W extends WhereClauseExtendedBuilder<W, G, H, O>,
		G extends DefaultPostGroupByTerm<H>,
		H extends DefaultPostHavingTerm<H, O>, O extends DefaultPostOrderByTerm<O>,
		D extends SelectDefaultBuilder<D, FX, F, W, G, H, O>> {

	private SelectClauseBuilder<F, FX> builder;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void init(){
		builder = (SelectClauseBuilder<F, FX>) DynamicSqlFactory.newQuery();
	}
	
	@Test
	public void shouldConfirmBuilderName(){
		// Act 
		SqlLexical type = ((JadysSqlQueryBuilder)builder).getType();
		
		// Assert
		assertThat(type, CoreMatchers.is(SqlLexical.SELECT));
	}
	
	@Test
	public void shouldbBuildEmptyStatement(){
		// Act
		String sql = ((JadysSqlQueryBuilder)builder).build();
		
		// Assert
		assertThat(sql, is(StringUtils.EMPTY));
	}

	@Test
	public void shouldConfirmNoParent(){
		// Act
		JadysSqlQueryBuilder parent = ((JadysSqlQueryBuilder)builder).getParent();

		// Assert
		assertThat(parent, nullValue());
	}

	@Test
	public void shouldConfirmNoChild(){
		// Act
		JadysSqlQueryBuilder child = ((JadysSqlQueryBuilder)builder).getChild();

		// Assert
		assertThat(child, nullValue());
	}

	@Test
	public void shouldBuildSelectAll(){
		// Arrange
		builder.select("*")
				.from("students");

		// Act
		String sql = ((JadysSqlQueryBuilder)builder).build();

		// Assert
		assertThat(sql, is("SELECT * FROM students"));
	}
	
	@Test
	public void shouldBuildSimpleSelectClause(){
		// Arrange
		builder.select();
		
		// Act
		String sql = ((JadysSqlQueryBuilder)builder).build();
		
		// Assert
		assertThat(sql, emptyString());
	}
	
	@Test
	public void shouldBuildEmphasizedSelect(){
		// Arrange
		builder.select("id");
		
		// Act
		String sql = ((JadysSqlQueryBuilder)builder).build();
		
		// Assert
		assertThat(sql, is("SELECT id"));
	}
	
	@Test
	public void shouldBuildFromClause(){
		// Arrange
		builder.select("name")
				.from("products");
		
		// Act
		String sql = ((JadysSqlQueryBuilder)builder).build();
		
		// Assert
		assertThat(sql, is("SELECT name FROM products"));
		assertThat(builder, instanceOf(SelectDefaultBuilder.class));
		assertThat(builder, instanceOf(SelectClauseBuilder.class));
		assertThat(((JadysSqlQueryBuilder)builder).getChild(), instanceOf(FromClauseBuilder.class));
	}
	
	@Test
	public void shouldBuildMultipleSelect(){
		// Arrange
		builder.select("firstName,lastName");
		
		// Act
		String sql = ((JadysSqlQueryBuilder)builder).build();
		
		// Assert
		assertThat(sql, is("SELECT firstName,lastName"));
		assertThat(builder, instanceOf(SelectDefaultBuilder.class));
		assertThat(builder, instanceOf(SelectClauseBuilder.class));
		assertThat(((JadysSqlQueryBuilder)builder).getChild(), nullValue());
	}
	
}
