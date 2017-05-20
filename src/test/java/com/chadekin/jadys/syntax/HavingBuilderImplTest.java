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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.syntax.having.generic.DefaultPostHavingTerm;
import com.chadekin.jadys.syntax.orderby.generic.DefaultPostOrderByTerm;
import com.chadekin.jadys.syntax.groupby.generic.GroupByBuilder;
import com.chadekin.jadys.syntax.groupby.generic.impl.GroupByBuilderImpl;
import com.chadekin.jadys.syntax.having.generic.HavingBuilder;
import org.apache.commons.lang.StringUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit test for HavingBuilder
 */
public class HavingBuilderImplTest<H extends DefaultPostHavingTerm<H, O>, O extends DefaultPostOrderByTerm<O>> {
	
	private GroupByBuilderImpl<H> builder;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void init(){
		builder = GroupByBuilderImpl.newGroupByStatement(null);
	}
	
	@Test
	public void shouldConfirmBuilderName(){
		// Act
		SqlLexical type = builder.getType();
		
		// Assert
		assertThat(type, is(SqlLexical.GROUP_BY));
		assertThat(builder, CoreMatchers.instanceOf(GroupByBuilder.class));
		assertThat(builder.getParent(), nullValue());
		assertThat(builder.getChild(), nullValue());
	}
	
	@Test
	public void shouldIgnoreNullParameter(){
		// arrange
		builder.having(null).equal(23);

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, emptyString());
		assertThat(builder, instanceOf(GroupByBuilder.class));
	}

	@Test
	public void shouldIgnoreEmptyParameter(){
		// arrange
		builder.having(StringUtils.EMPTY).equal(23);
		
		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, emptyString());
		assertThat(builder, instanceOf(GroupByBuilder.class));
	}

	@Test
	public void shouldBuildHavingClauseIfValueIsNull(){
		// arrange
		builder.having("age").greaterThan(18)
				.and("model").lessThan(null);
		
		// Act
		String sql = builder.build();
		
		// Assert
		assertThat(sql, is("HAVING age>18"));
		assertThat(builder, instanceOf(GroupByBuilder.class));
		assertThat(builder.getChild(), instanceOf(HavingBuilder.class));
		assertThat(builder.getChild().getChild(), nullValue());
	}

	@Test
	public void shouldBuildHavingClauseIfValueIsEmpty(){
		// arrange
		builder.having("age").greaterThan(18)
				.and("model").greaterThanOrEqual(StringUtils.EMPTY);

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("HAVING age>18"));
		assertThat(builder, instanceOf(GroupByBuilder.class));
		assertThat(builder.getChild(), instanceOf(HavingBuilder.class));
		assertThat(builder.getChild().getChild(), nullValue());
	}

	@Test
	public void shouldBuildHavingClauseWithConjunction(){
		// arrange
		builder.having("age").greaterThan(18)
				.and("size").greaterThanOrEqual(45);

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("HAVING age>18 AND size>=45"));
		assertThat(builder, instanceOf(GroupByBuilder.class));
		assertThat(builder.getChild(), instanceOf(HavingBuilder.class));
		assertThat(builder.getChild().getChild(), nullValue());
	}

	@Test
	public void shouldBuildHavingClause(){
		// Arrange
		builder.having("age").greaterThan(18)
				.and("lastName").not().equal("dubois");

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("HAVING age>18 AND lastName<>'dubois'"));
		assertThat(builder, instanceOf(GroupByBuilder.class));
		assertThat(builder.getChild(), instanceOf(HavingBuilder.class));
		assertThat(builder.getChild().getChild(), nullValue());
	}

	@Test
	public void shouldBuildHavingClauseWithFunction(){
		// Arrange
		builder.having().ucase("name").equal("PAUL")
				.and("lastName").not().equal("dubois");

		// Act
		String sql = builder.build();

		// Assert
		assertThat(sql, is("HAVING UCASE(name)='PAUL' AND lastName<>'dubois'"));
		assertThat(builder, instanceOf(GroupByBuilder.class));
		assertThat(builder.getChild(), instanceOf(HavingBuilder.class));
		assertThat(builder.getChild().getChild(), nullValue());
	}
}
