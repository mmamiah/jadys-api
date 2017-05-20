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

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.commons.resourcekeys.JadysExceptionMessageKeys;
import com.chadekin.jadys.operators.JunctionOperator;
import com.chadekin.jadys.operators.ParameterOperator;
import com.chadekin.jadys.syntax.groupby.generic.DefaultPostGroupByTerm;
import com.chadekin.jadys.syntax.having.generic.DefaultPostHavingTerm;
import com.chadekin.jadys.syntax.orderby.generic.DefaultPostOrderByTerm;
import com.chadekin.jadys.syntax.from.generic.FromClauseBuilder;
import com.chadekin.jadys.syntax.from.generic.FromClauseExtendedBuilder;
import com.chadekin.jadys.syntax.from.generic.impl.FromClauseBuilderImpl;
import com.chadekin.jadys.syntax.groupby.GroupByStatement;
import com.chadekin.jadys.syntax.having.HavingStatement;
import com.chadekin.jadys.syntax.orderby.OrderByStatement;
import com.chadekin.jadys.syntax.select.generic.SelectClauseBuilder;
import com.chadekin.jadys.syntax.factory.DynamicSqlFactory;
import com.chadekin.jadys.syntax.where.generic.WhereClauseExtendedBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit test for FromClauseBuilder
 */
public class FromClauseExtendedBuilderTest<W extends WhereClauseExtendedBuilder<W, G, H, O>, 
		X extends FromClauseExtendedBuilder<X, W, O>,
		G extends DefaultPostGroupByTerm<H>,
		H extends DefaultPostHavingTerm<H, O>,
		O extends DefaultPostOrderByTerm<O>> {
	
	private FromClauseBuilderImpl<X, W, O> builder;

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void init(){
		builder = FromClauseBuilderImpl.newFromStatement(null, "DCT_Users");
	}
	
	@Test
	public void shouldConfirmBuilderName(){
		// Act
		SqlLexical builderName = ((JadysSqlQueryBuilder)builder).getType();
		SqlLexical childBuilderName = ((JadysSqlQueryBuilder)builder.as("builder")).getType();
		
		// Assert
		assertThat(builderName, CoreMatchers.is(SqlLexical.FROM));
		assertThat(childBuilderName, CoreMatchers.is(SqlLexical.FROM));
	}
	
	@Test
	public void shouldConfirmNoParent(){
		// Act
		JadysSqlQueryBuilder parent = builder.getParent();
		
		// Assert
		assertThat(parent, nullValue());
	}
	
	@Test
	public void shouldConfirmNoChild(){
		// Act
		JadysSqlQueryBuilder child = builder.getChild();
		
		// Assert
		assertThat(child, nullValue());
	}

	@Test
	public void shouldNoTBuildQueryWhenWhereStatementIsIncomplete(){
		// Act
		String sql = ((JadysSqlQueryBuilder)builder.where("Users")).build();

		// Assert
		assertThat(sql, is("FROM DCT_Users"));
		assertThat(builder, instanceOf(FromClauseBuilder.class));
		assertThat(builder.getChild(), instanceOf(WhereClauseExtendedBuilder.class));
		assertThat(builder.getChild(), instanceOf(JunctionOperator.class));
		assertThat(builder.getChild(), instanceOf(GroupByStatement.class));
		assertThat(builder.getChild(), instanceOf(HavingStatement.class));
		assertThat(builder.getChild(), instanceOf(OrderByStatement.class));
		assertThat(builder.getChild(), instanceOf(ParameterOperator.class));
		assertThat(builder.getChild().getChild(), nullValue());
	}

	@Test
	public void shouldBuildSimpleWhereStatement(){
		// Act
		String sql = builder.where("name").equal("Lewis").build();

		// Assert
		assertThat(sql, is("FROM DCT_Users WHERE name='Lewis'"));
		assertThat(builder, instanceOf(FromClauseBuilder.class));
		assertThat(builder.getChild(), instanceOf(WhereClauseExtendedBuilder.class));
		assertThat(builder.getChild(), instanceOf(JunctionOperator.class));
		assertThat(builder.getChild(), instanceOf(GroupByStatement.class));
		assertThat(builder.getChild(), instanceOf(HavingStatement.class));
		assertThat(builder.getChild(), instanceOf(OrderByStatement.class));
		assertThat(builder.getChild(), instanceOf(ParameterOperator.class));
		assertThat(builder.getChild().getChild(), nullValue());
	}

	@Test
	public void shouldThrowEXceptionWhenMissingAliasInSelectStatement(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.INVALID_ALIAS);
		
		// Arrange
		JadysSqlQueryBuilder builder = (JadysSqlQueryBuilder) DynamicSqlFactory.newQuery()
				.select()
				.from("Users").as("u")
				.join("cars", "c").on("c.id").equal("u.carId");

		// Act
		String sql = builder.build();
	}
	
	@Test
	public void shouldBuildInnerJoinStatement(){
		// Arrange
		JadysSqlQueryBuilder builder = (JadysSqlQueryBuilder) DynamicSqlFactory.newQuery()
				.lazy()
				.select("u.*")
				.from("Users").as("u")
				.join("cars", "c").on("c.id").equal("u.carId");
		
		// Act
		String sql = builder.build();
		
		// Assert
		assertThat(sql, is("SELECT u.* FROM Users AS u JOIN cars c ON c.id=u.carId"));
		
		JadysSqlQueryBuilder assertBuilder = builder;
		while(assertBuilder!=null && assertBuilder.getParent()!=null){
			assertBuilder = assertBuilder.getParent();
		}
		
		assertThat(assertBuilder, instanceOf(SelectClauseBuilder.class));
		assertBuilder = assertBuilder.getChild();
		assertThat(assertBuilder, instanceOf(FromClauseBuilder.class));
		assertBuilder = assertBuilder.getChild();
		assertThat(assertBuilder, nullValue());
	}


	@Test
	public void shouldBuildLeftJoinClause(){
		// Arrange
		FromClauseBuilderImpl<X, W, O> builder = FromClauseBuilderImpl.newFromStatement(null, "DCT_Users");
		builder.as("users")
				.leftJoin("DCT_Customer", "cst").on("cst.productId").equal("product.id")
				.where("cst.name").equal("Paul");

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("FROM DCT_Users AS users LEFT JOIN DCT_Customer cst ON cst.productId=product.id WHERE cst.name='Paul'"));
		JadysSqlQueryBuilder assertBuilder = builder;
		while(assertBuilder!=null && assertBuilder.getParent()!=null){
			assertBuilder = assertBuilder.getParent();
		}

		assertThat(assertBuilder, instanceOf(FromClauseBuilder.class));
		assertBuilder = assertBuilder.getChild();
		assertThat(assertBuilder, instanceOf(WhereClauseExtendedBuilder.class));
		assertBuilder = assertBuilder.getChild();
		assertThat(assertBuilder, nullValue());
	}

	@Test
	public void shouldBuildRightJoinClause(){
		// Arrange
		FromClauseBuilderImpl<X, W, O> builder = FromClauseBuilderImpl.newFromStatement(null, "DCT_Users");
		builder.as("users")
				.rightJoin("DCT_Customer", "cst").on("cst.productId").equal("product.id")
				.where("cst.key").equal("secondary");

		// Act
		String sql = builder.build();

		assertThat(sql, is("FROM DCT_Users AS users RIGHT JOIN DCT_Customer cst ON cst.productId=product.id WHERE cst.key='secondary'"));
		JadysSqlQueryBuilder assertBuilder = builder;
		while(assertBuilder!=null && assertBuilder.getParent()!=null){
			assertBuilder = assertBuilder.getParent();
		}

		assertThat(assertBuilder, instanceOf(FromClauseBuilder.class));
		assertBuilder = assertBuilder.getChild();
		assertThat(assertBuilder, instanceOf(WhereClauseExtendedBuilder.class));
		assertBuilder = assertBuilder.getChild();
		assertThat(assertBuilder, nullValue());
	}

	@Test
	public void shouldBuildFullJoinClause(){
		// Arrange
		FromClauseBuilderImpl<X, W, O> builder = FromClauseBuilderImpl.newFromStatement(null, "DCT_Users");
		builder.as("users")
				.fullJoin("DCT_Customer", "cst").on("cst.productId").equal("product.id")
				.where("cst.city").equal("Berlin");

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("FROM DCT_Users AS users FULL JOIN DCT_Customer cst ON cst.productId=product.id WHERE cst.city='Berlin'"));
		JadysSqlQueryBuilder assertBuilder = builder;
		while(assertBuilder!=null && assertBuilder.getParent()!=null){
			assertBuilder = assertBuilder.getParent();
		}

		assertThat(assertBuilder, instanceOf(FromClauseBuilder.class));
		assertBuilder = assertBuilder.getChild();
		assertThat(assertBuilder, instanceOf(WhereClauseExtendedBuilder.class));
		assertBuilder = assertBuilder.getChild();
		assertThat(assertBuilder, nullValue());
	}

	@Test
	public void shouldBuildComboClause(){
		// Arrange
		FromClauseBuilderImpl<X, W, O> builder = FromClauseBuilderImpl.newFromStatement(null, "DCT_Users");
		builder.as("users")
				.join("DCT_Customer", "cst").on("cst.productId").equal("product.id")
				.where("cst.age").equal(27);

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("FROM DCT_Users AS users JOIN DCT_Customer cst ON cst.productId=product.id WHERE cst.age=27"));
		JadysSqlQueryBuilder assertBuilder = builder;
		while(assertBuilder!=null && assertBuilder.getParent()!=null){
			assertBuilder = assertBuilder.getParent();
		}

		assertThat(assertBuilder, instanceOf(FromClauseBuilder.class));
		assertBuilder = assertBuilder.getChild();
		assertThat(assertBuilder, instanceOf(WhereClauseExtendedBuilder.class));
		assertBuilder = assertBuilder.getChild();
		assertThat(assertBuilder, nullValue());
	}

	@Test
	public void shouldThrowIaeWhenMissageArgumentInWhereClauseParameter(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(containsString(JadysExceptionMessageKeys.INVALID_ALIAS));
		
		// Arrange
		FromClauseBuilderImpl<X, W, O> builder = FromClauseBuilderImpl.newFromStatement(null, "DCT_Users");
		builder.as("us")
				.join("User_", "user").on("user.id_").equal("another.userId")
				.where("size").equal(5);

		// Act
		builder.build();
	}

	@Test
	public void shouldCleanJoinStatementWhenJoinedTableIsNotUsed(){
		// Arrange
		FromClauseBuilderImpl<X, W, O> builder = FromClauseBuilderImpl.newFromStatement(null, "DCT_Users");
		builder.as("us")
				.join("User_", "user").on("user.id_").equal("another.userId")
				.where("user.size").equal(5);

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("FROM DCT_Users AS us JOIN User_ user ON user.id_=another.userId WHERE user.size=5"));
	}

	@Test
	public void shouldThrowIaeWhenMissageArgumentInExtendedWhereClauseParameter(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(containsString(JadysExceptionMessageKeys.INVALID_ALIAS));
		
		// Arrange
		FromClauseBuilderImpl<X, W, O> builder = FromClauseBuilderImpl.newFromStatement(null, "DCT_Users");
		builder.as("us")
				.join("User_", "user").on("users.id_").equal("another.userId")
				.where("size").equal(5).and("user.name").equal("Lewis");

		// Act
		String sql = builder.build();
	}

	@Test
	public void shouldThrowIAEWhenTableNameNotValid(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.INVALID_TABLE_NAME);

		// Arrange
		builder.join(null, "user");
	}

	@Test
	public void shouldThrowIAEWhenAliasNotValid(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.INVALID_ALIAS);

		// Act
		builder.join("User_", null);
	}

}
