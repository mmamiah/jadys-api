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
import static org.hamcrest.text.IsEmptyString.*;

import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.commons.resourcekeys.JadysExceptionMessageKeys;
import com.chadekin.jadys.syntax.groupby.generic.DefaultPostGroupByTerm;
import com.chadekin.jadys.syntax.having.generic.DefaultPostHavingTerm;
import com.chadekin.jadys.syntax.orderby.generic.DefaultPostOrderByTerm;
import com.chadekin.jadys.syntax.from.generic.FromClauseTerm;
import com.chadekin.jadys.syntax.from.generic.FromClauseExtendedBuilder;
import com.chadekin.jadys.syntax.from.generic.impl.FromClauseBuilderImpl;
import com.chadekin.jadys.syntax.join.JoinStatement;
import com.chadekin.jadys.syntax.factory.DynamicSqlFactory;
import com.chadekin.jadys.syntax.select.generic.impl.SelectDefaultBuilder;
import com.chadekin.jadys.syntax.select.operation.SelectStatementOperation;
import com.chadekin.jadys.syntax.select.type.SelectBaseStatement;
import com.chadekin.jadys.syntax.where.generic.WhereClauseExtendedBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit Test for AggregateSqlStatementBuilder
 */
public class SelectStatementTest<T,
		FX extends FromClauseExtendedBuilder<FX, W, O>, F extends FromClauseTerm<W, O, FX>,
		W extends WhereClauseExtendedBuilder<W, G, H, O>,
		G extends DefaultPostGroupByTerm<H>,
		H extends DefaultPostHavingTerm<H, O>, O extends DefaultPostOrderByTerm<O>,
		D extends SelectDefaultBuilder<D, FX, F, W, G, H, O>> {

	private D builder;

	@Rule
	public ExpectedException exception =  ExpectedException.none();

	@Before
	public void init(){
		builder = (D) DynamicSqlFactory.newQuery().select();
	}

	@Test
	public void shouldConfirmBuilderName(){
		// Act
		SqlLexical type = ((JadysSqlQueryBuilder)builder).getType();

		// Assert
		assertThat(type, is(SqlLexical.SELECT));
	}
	
	@Test
	public void shouldBuildSelectCountAll(){
		// Arrange
		builder.count(null);
		
		// Act
		String sql = builder.build();
		
		// Assert
		assertThat(sql, is("SELECT COUNT(*)"));
	}
	
	@Test
	public void shouldBuildCountStatement(){
		// Arrange
		JadysSqlQueryBuilder returnedBuilder = (JadysSqlQueryBuilder) builder.count("users");
		
		// Act
		String sql = ((JadysSqlQueryBuilder)builder).build();
		
		// Assert
		assertThat(sql, is("SELECT COUNT(users)"));
		assertThat(builder, instanceOf(SelectBaseStatement.class));
		assertThat(builder, instanceOf(SelectStatementOperation.class));
		assertThat(builder, instanceOf(AliasStatement.class));
		assertThat(returnedBuilder, instanceOf(SelectDefaultBuilder.class));
	}
	
	@Test
	public void shouldThrowExceptionWhenAliasIsBlank(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.INVALID_ALIAS);
		
		// Arrange
		builder.count("id").as(null);
	}
	
	@Test
	public void shouldBuildCountStatementWithAlias(){
		// Arrange
		builder.count("users").as("usr");

		// Act
		String sql = ((JadysSqlQueryBuilder)builder).build();

		// Assert
		assertThat(sql, is("SELECT COUNT(users) AS usr"));
		assertThat(builder, instanceOf(SelectBaseStatement.class));
		assertThat(builder, instanceOf(SelectStatementOperation.class));
		assertThat(builder, instanceOf(AliasStatement.class));

		JadysSqlQueryBuilder child = ((JadysSqlQueryBuilder)builder).getChild();
		assertThat(child, nullValue());
	}
	
	@Test
	public void shouldNotBuildQueryWhenMissingFromStatement(){
		// Act
		String sql = ((JadysSqlQueryBuilder)builder).build();

		// Assert
		assertThat(sql, emptyString());
		assertThat(builder, instanceOf(SelectBaseStatement.class));
		assertThat(builder, instanceOf(SelectStatementOperation.class));
		assertThat(builder, instanceOf(AliasStatement.class));
		
		JadysSqlQueryBuilder child = ((JadysSqlQueryBuilder)builder).getChild();
		assertThat(child, nullValue());
	}

	@Test
	public void shouldThrowIaEWhenDbTableIsNull(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		
		// Act
		builder.from(null);
	}

	@Test
	public void shouldBuildSimpleQuery(){
		// Act
		String sql = builder.from("Customers").build();

		// Assert
		assertThat(sql, is("SELECT * FROM Customers"));
		assertThat(builder, instanceOf(SelectBaseStatement.class));
		assertThat(builder, instanceOf(SelectStatementOperation.class));
		assertThat(builder, instanceOf(AliasStatement.class));
		
		JadysSqlQueryBuilder child = ((JadysSqlQueryBuilder)builder).getChild();
		assertThat(child, instanceOf(FromClauseBuilderImpl.class));
		assertThat(child, instanceOf(JoinStatement.class));
		assertThat(child, instanceOf(AliasStatement.class));
	}
	
	@Test
	public void shouldBuildComplexclause(){
		// Arrange
		builder.count("cars").as("cr")
				.from("city");
		// Act
		String sql = ((JadysSqlQueryBuilder)builder).build();
		
		// Assert
		assertThat(sql, is("SELECT COUNT(cars) AS cr FROM city"));
		assertThat(builder, instanceOf(SelectBaseStatement.class));
		assertThat(builder, instanceOf(SelectStatementOperation.class));
		assertThat(builder, instanceOf(AliasStatement.class));

		JadysSqlQueryBuilder child = ((JadysSqlQueryBuilder)builder).getChild();
		assertThat(child, instanceOf(FromClauseBuilderImpl.class));
		assertThat(child, instanceOf(JoinStatement.class));
		assertThat(child, instanceOf(AliasStatement.class));
	}
	
	@Test
	public void shouldThrowIllegalArgumentExceptionIfSqlInjectionByDeleteStatement(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.SQL_INJECTION_DETECTED);
		
		// Act
		builder.from("delete from actors where id=12").build();
	}

	@Test
	public void shouldThrowIllegalArgumentExceptionIfSqlInjectionMultipleSelect(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.SQL_INJECTION_DETECTED);

		// Act
		builder.from("select * from friends f where f.id=(select c.id cars c where c.size=(select count(s.age) from students s where s.size=(select count(c.size) from children c)))").build();
	}
	
	@Test
	public void shouldBuildQueryUsingRegularSelectBuilder(){
		// Act
		String sql = DynamicSqlFactory.newQuery()
										.select().count("ctr").as("country")
										.from("users")
										.build();
		
		// Assert
		assertThat(sql, is("SELECT COUNT(ctr) AS country FROM users"));
	}
	
}
