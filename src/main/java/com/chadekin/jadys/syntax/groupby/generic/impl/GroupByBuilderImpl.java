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
package com.chadekin.jadys.syntax.groupby.generic.impl;

import java.util.Arrays;
import java.util.stream.Stream;
import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.commons.AbstractSqlQueryBuilder;
import com.chadekin.jadys.commons.enums.JadysSqlOperation;
import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.commons.resourcekeys.JadysKeys;
import com.chadekin.jadys.syntax.groupby.generic.GroupByBuilder;
import org.apache.commons.lang.StringUtils;

/**
 * GroupByBuilder
 */
public class GroupByBuilderImpl<G> extends AbstractSqlQueryBuilder<GroupByBuilderImpl> implements GroupByBuilder<G> {
	
	protected GroupByBuilderImpl(JadysSqlQueryBuilder parent){
		super(parent);
	}
	
	public static GroupByBuilderImpl newGroupByStatement(JadysSqlQueryBuilder parent){
		return new GroupByBuilderImpl(parent);
	}
	
	private static boolean isValidExpression(String param, JadysSqlOperation equality, Object value){
		return StringUtils.isNotBlank(param) && equality != null && value!=null && StringUtils.isNotBlank(value.toString());
	}

	@Override
	public SqlLexical getType() {
		return SqlLexical.GROUP_BY;
	}

	@Override
	public GroupByBuilderImpl apply(String... expression) {
		if(expression==null || Arrays.asList(expression).isEmpty()){
			return this;
		}

		Stream.of(expression)
				.forEach(expr->{
					extractAlias(expr).stream()
							.forEach(this::withAlias);
					append(expr);
				});
		
		return this;
	}

	private void append(String dbColumn){
		if(StringUtils.isBlank(dbColumn)) throw new IllegalArgumentException("Undefined parameter for 'GROUP BY' clause");
		if(getResultSql() == null){
			setResultSql(new StringBuilder());
		}
		appendStatement();
		getResultSql().append(dbColumn);
	}

	private void appendStatement(){
		if(getResultSql().length()>0){
			getResultSql().append(JadysKeys.COMMA).append(JadysKeys.SPACE);
		}
	}

}
