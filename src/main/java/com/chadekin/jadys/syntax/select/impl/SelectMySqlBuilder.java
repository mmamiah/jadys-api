package com.chadekin.jadys.syntax.select.impl;

import com.chadekin.jadys.sqldialect.DefaultPostGroupByTerm;
import com.chadekin.jadys.sqldialect.FromClauseTerm;
import com.chadekin.jadys.sqldialect.mysql.MySqlPostHaving;
import com.chadekin.jadys.sqldialect.mysql.MySqlPostOrderBy;
import com.chadekin.jadys.syntax.from.FromClauseExtendedBuilder;
import com.chadekin.jadys.syntax.select.AbstractSelectBuilder;
import com.chadekin.jadys.syntax.select.SelectClauseBuilder;
import com.chadekin.jadys.syntax.where.WhereClauseExtendedBuilder;

/**
 * SelectMySqlBuilder
 */
public class SelectMySqlBuilder<
		T,
		FX extends FromClauseExtendedBuilder<FX, W, O>,
		F extends FromClauseTerm<W, O, FX>,
		W extends WhereClauseExtendedBuilder<W, G, H, O>,
		G extends DefaultPostGroupByTerm<H>,
		H extends MySqlPostHaving<H, O>,
		O extends MySqlPostOrderBy<O>>

		extends AbstractSelectBuilder<T, F, FX> {

	private SelectMySqlBuilder(){
		super();
	}

	public static <T, W extends WhereClauseExtendedBuilder<W, G, H, O>, O extends MySqlPostOrderBy<O>,
			FX extends FromClauseExtendedBuilder<FX, W, O>, F extends FromClauseTerm<W, O, FX>,
			G extends DefaultPostGroupByTerm<H>, H extends MySqlPostHaving<H, O>,
			M extends SelectMySqlBuilder<M, FX, F, W, G, H, O>, R extends SelectClauseBuilder<F, FX>>
	R newBuilder(){
		return (R) new SelectMySqlBuilder<M, FX, F, W, G, H, O>();
	}

}
