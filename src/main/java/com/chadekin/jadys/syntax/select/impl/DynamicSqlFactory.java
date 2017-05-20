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
package com.chadekin.jadys.syntax.select.impl;

import com.chadekin.jadys.sqldialect.DefaultPostGroupByTerm;
import com.chadekin.jadys.sqldialect.DefaultPostHavingTerm;
import com.chadekin.jadys.sqldialect.DefaultPostOrderByTerm;
import com.chadekin.jadys.sqldialect.FromClauseTerm;
import com.chadekin.jadys.sqldialect.mysql.MySqlPostHaving;
import com.chadekin.jadys.sqldialect.mysql.MySqlPostOrderBy;
import com.chadekin.jadys.syntax.from.FromClauseExtendedBuilder;
import com.chadekin.jadys.syntax.select.SelectClauseBuilder;
import com.chadekin.jadys.syntax.where.WhereClauseExtendedBuilder;

/**
 * Execute native SQL Query
 */
public class DynamicSqlFactory {

	/**
	 * The SelectDefaultBuilder
	 * @param <W> WhereClauseExtendedBuilder
	 * @param <F> The FromClause Builder
	 * @param <FX> FromClauseExtendedBuilder
	 * @param <O> OrderBy Clause per sql dialect
	 * @param <D> The default Select Statement builder (API Entry Point)
	 * @return
	 */
	public static <W extends WhereClauseExtendedBuilder<W, G, H, O>, F extends FromClauseTerm<W, O, FX>,
			FX extends FromClauseExtendedBuilder<FX, W, O>, 
			G extends DefaultPostGroupByTerm<H>,
			H extends DefaultPostHavingTerm<H, O>,
			O extends DefaultPostOrderByTerm<O>, D extends SelectDefaultBuilder<D, FX, F, W, G, H, O>>
	SelectClauseBuilder<F, FX> newQuery(){
		
		return (SelectClauseBuilder<F, FX>) SelectDefaultBuilder.newBuilder();
	}

	/**
	 * The SelectMySqlBuilder
	 * @param <F> The FromClause Builder
	 * @param <FX> FromClauseExtendedBuilder
	 * @param <W> WhereClauseExtendedBuilder
	 * @param <O> OrderBy Clause per sql dialect
	 * @param <M> The MySql Select Statement Builder (API Entry Point)
	 * @return
	 */
	public static <F extends FromClauseTerm<W, O, FX>, FX extends FromClauseExtendedBuilder<FX, W, O>, 
			W extends WhereClauseExtendedBuilder<W, G, H, O>, H extends MySqlPostHaving<H, O>,
			G extends DefaultPostGroupByTerm<H>, O extends MySqlPostOrderBy<O>, M extends SelectMySqlBuilder<M, FX, F, W, G, H, O>>
	SelectClauseBuilder<F, FX> newMySqlQuery(){
		return (SelectClauseBuilder<F, FX>) SelectMySqlBuilder.newBuilder();
	}

//	public static <T extends JadysSqlQueryBuilder> SelectStatement<DynamicSqlBuilder<SelectOracle<T>>, SelectOracle<T>> newOracleQuery(){
//		return DynamicSqlBuilder.<SelectOracle<T>> newQuery();
//	}
//
//	public static <T extends JadysSqlQueryBuilder> SelectStatement<DynamicSqlBuilder<SelectMsAccess<T>>, SelectMsAccess<T>> newMsAccessQuery(){
//		return DynamicSqlBuilder.<SelectMsAccess<T>> newQuery();
//	}
//
//	public static <T extends JadysSqlQueryBuilder> SelectStatement<DynamicSqlBuilder<SelectSqlServer<T>>, SelectSqlServer<T>> newSqlServerQuery(){
//		return DynamicSqlBuilder.<SelectSqlServer<T>> newQuery();
//	}
	
}
