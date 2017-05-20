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
package com.chadekin.jadys.syntax.having.impl;

import java.util.stream.Stream;
import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.commons.AbstractSqlQueryBuilder;
import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.operators.ParameterOperator;
import com.chadekin.jadys.sqlfunction.SqlFunctionProxy;
import com.chadekin.jadys.syntax.having.HavingBuilder;

/**
 * HavingBuilder
 * @param <O> The OrderBy builder type
 */
public class HavingBuilderImpl<O> extends AbstractSqlQueryBuilder<HavingBuilder> implements HavingBuilder<HavingBuilder, O> {

	protected HavingBuilderImpl(JadysSqlQueryBuilder parent){
		super(parent);
	}

	public static HavingBuilderImpl newHavingStatement(JadysSqlQueryBuilder parent){
		return new HavingBuilderImpl(parent);
	}

	@Override
	public SqlLexical getType() {
		return SqlLexical.HAVING;
	}

	@Override
	public HavingBuilder apply(String... expression) {
		Stream.of(expression)
				.forEach(expr->{
					extractAlias(expr).stream()
							.forEach(this::withAlias);
				});
		return super.apply(expression);
	}

	@Override
	public HavingBuilder append(String... value) {
		Stream.of(value)
				.forEach(expr->{
					extractAlias(expr).stream()
							.forEach(this::withAlias);
				});
		return super.append(value);
	}
}
