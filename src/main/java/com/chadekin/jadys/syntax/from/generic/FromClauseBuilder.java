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
package com.chadekin.jadys.syntax.from.generic;

import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.operators.ParameterOperator;
import com.chadekin.jadys.syntax.groupby.GroupByStatement;
import com.chadekin.jadys.syntax.having.HavingStatement;
import com.chadekin.jadys.syntax.join.JoinStatement;
import com.chadekin.jadys.syntax.join.OnStatement;
import com.chadekin.jadys.syntax.orderby.OrderByStatement;
import com.chadekin.jadys.syntax.where.WhereStatement;

/**
 * FromClauseBuilder
 * @param <X> Extended From Clause (FromClauseExtendedBuilder)
 * @param <W> WhereClause per SqlDialect
 * @param <O> OrderClause per SqlDialect
 */
public interface FromClauseBuilder<X, W, O> extends WhereStatement<W>, GroupByStatement<O>, HavingStatement<O>, OrderByStatement<O> {
	
}
