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
package com.chadekin.jadys.syntax.where.impl;

import com.chadekin.jadys.commons.AbstractSqlQueryBuilder;
import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.commons.enums.JadysSqlOperation;
import com.chadekin.jadys.syntax.where.WhereClauseBuilder;
import org.apache.commons.lang.StringUtils;

/**
 * Native SQL 'Where' clause
 */
public class WhereClauseBuilderImpl<W, O> extends AbstractSqlQueryBuilder<WhereClauseBuilderImpl> implements WhereClauseBuilder<W, O> {
	
	protected WhereClauseBuilderImpl(AbstractSqlQueryBuilder parent){
		super(parent);
	}

	public static <W, O> WhereClauseBuilderImpl<W, O> newWhereStatement(AbstractSqlQueryBuilder parent){
		return new WhereClauseBuilderImpl<W, O>(parent);
	}

	@Override
	public SqlLexical getType() {
		return SqlLexical.WHERE;
	}

	//	@Override
//	public WhereClauseBuilder where(String param, NullTestingOperator operator) {
//		String expression = SqlExpressionFactory.newExpression(param, operator).build();
//		append(expression);
//		return this;
//	}
//
//	@Override
//	public WhereClauseBuilder where(String param, SqlOperator equality, Object value){
//		if(isValidExpression(param, equality, value) && isValidParentAlias(param, this)){
//			String expression = SqlExpressionFactory.newExpression(param, equality, value)
//					.build();
//			append(expression);
//		}
//		return this;
//	}

//	@Override
//	public GroupByBuilder groupBy(String dbColumn) {
//		GroupByBuilder builder = GroupByBuilder.newGroupByStatement(this);
//		setChild(builder.groupBy(dbColumn));
//		return builder;
//	}
//
//	@Override
//	public HavingBuilder having(String param, SqlOperator equality, Object value) {
//		HavingBuilder builder = HavingBuilder.newHavingStatement(this);
//		setChild(builder.having(param, equality, value));
//		return builder;
//	}
//
//	@Override
//	public HavingBuilder having(String param, NullTestingOperator ntEquality) {
//		HavingBuilder builder = HavingBuilder.newHavingStatement(this);
//		setChild(builder.having(param, ntEquality));
//		return builder;
//	}
//
//	@Override
//	public HavingBuilder having(String expression) {
//		HavingBuilder builder = HavingBuilder.newHavingStatement(this);
//		setChild(builder.having(expression));
//		return builder;
//	}
//
//	@Override
//	public OrderByClauseBuilder orderby(String orderByType, String... dbColumns) {
//		OrderByClauseBuilder builder = OrderByClauseBuilder.newStatement(this);
//		builder.orderby(orderByType, dbColumns);
//		setChild(builder);
//		return builder;
//	}
//
//	@Override
//	public OrderByClauseBuilder orderby(Set<String> dbColumns, String orderByType) {
//		OrderByClauseBuilder builder = OrderByClauseBuilder.newStatement(this);
//		builder.orderby(dbColumns, orderByType);
//		setChild(builder);
//		return builder;
//	}
//
//	@Override
//	public OrderByClauseBuilder orderByAsc(String... dbColumns){
//		OrderByClauseBuilder builder = OrderByClauseBuilder.newStatement(this);
//		builder.orderByAsc(dbColumns);
//		setChild(builder);
//		return builder;
//	}
//
//	@Override
//	public OrderByClauseBuilder orderByDesc(String... dbColumns){
//		OrderByClauseBuilder builder = OrderByClauseBuilder.newStatement(this);
//		builder.orderByDesc(dbColumns);
//		setChild(builder);
//		return builder;
//	}

//	@Override
//	public void initResultSql(String appender) {
//		if(!getValue().isEmpty()){
//			getResultSql().append(JadysKeys.SPACE);
//		}
//
//		if(!getValue().contains(SqlStatementKeys.WHERE)){
//			getResultSql().append(SqlStatementKeys.WHERE);
//		}
//		else if(StringUtils.isNotBlank(appender)){
//			getResultSql().append(appender);
//		}
//	}
	
	private static boolean isValidExpression(String param, JadysSqlOperation equality, Object value){
		return StringUtils.isNotBlank(param) && equality != null && value!=null && StringUtils.isNotBlank(value.toString());
	}
	
}
