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
package com.chadekin.jadys.syntax.orderby.impl;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;
import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.commons.AbstractSqlQueryBuilder;
import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.commons.resourcekeys.JadysKeys;
import com.chadekin.jadys.sqldialect.mysql.MySqlPostOrderBy;
import com.chadekin.jadys.syntax.orderby.OrderByClauseBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;

/**
 * OrderByClauseBuilder
 */
public class OrderByClauseBuilderImpl<P> extends AbstractSqlQueryBuilder<OrderByClauseBuilder<P>> implements OrderByClauseBuilder<OrderByClauseBuilder<P>>, MySqlPostOrderBy<P> {
	
	private static final Set<String> SORTING_ORDER = Sets.newHashSet(SqlLexical.ASC.getCode(), SqlLexical.DESC.getCode());
	
	private List<String> columnsToOrder = Lists.newArrayList();
	
	protected OrderByClauseBuilderImpl(JadysSqlQueryBuilder parent){
		super(parent);
	}
	
	public static <R> OrderByClauseBuilderImpl<R> newStatement(JadysSqlQueryBuilder parent){
		return new OrderByClauseBuilderImpl<R>(parent);
	}

	@Override
	public SqlLexical getType() {
		return SqlLexical.ORDER_BY;
	}

	@Override
	public OrderByClauseBuilder append(String... expression) {
		if(expression!=null && expression.length > 0){

			Stream.of(expression)
					.filter(value -> StringUtils.isNotBlank(value))
					.forEach(expressionConsumer());
		}
		return this;
	}
	
	private Consumer<String> expressionConsumer(){
		return value -> {
			extractAlias(value).stream()
					.forEach(this::withAlias);
			
			String tempValue = null;
			if (SORTING_ORDER.contains(value) && !columnsToOrder.isEmpty()) {
				tempValue = columnsToOrder.stream()
						.map(item -> new StringBuilder(item).append(JadysKeys.SPACE).append(value).toString())
						.reduce((a, b) -> new StringBuilder(a).append(JadysKeys.COMMA).append(JadysKeys.SPACE).append(b).toString())
						.orElse(StringUtils.EMPTY);
				columnsToOrder.clear();
			}
			else if (!SORTING_ORDER.contains(value)) {
				columnsToOrder.add(value);
			}
			if (StringUtils.isNotBlank(tempValue)) {
				if (getResultSql().length() > 0) {
					getResultSql().append(JadysKeys.COMMA);
				}
				getResultSql().append(JadysKeys.SPACE).append(tempValue);
			}
		};
	}
	
	@Override
	public String buildNext() {
		columnsToOrder.stream()
				.forEach(column_name -> {
					if (getResultSql().length() > 0) {
						getResultSql().append(JadysKeys.COMMA);
					}
					getResultSql().append(JadysKeys.SPACE).append(column_name);
				});
		return super.buildNext();
	}
}
