package com.chadekin.jadys.syntax.select.mysql.impl;

import com.chadekin.jadys.commons.enums.SqlDialect;
import com.chadekin.jadys.syntax.groupby.generic.DefaultPostGroupByTerm;
import com.chadekin.jadys.syntax.having.generic.DefaultPostHavingTerm;
import com.chadekin.jadys.syntax.from.generic.FromClauseTerm;
import com.chadekin.jadys.syntax.orderby.mysql.MySqlPostOrderBy;
import com.chadekin.jadys.syntax.from.generic.FromClauseExtendedBuilder;
import com.chadekin.jadys.syntax.select.AbstractSelectBuilder;
import com.chadekin.jadys.syntax.select.generic.SelectClauseBuilder;
import com.chadekin.jadys.syntax.where.generic.WhereClauseExtendedBuilder;

/**
 * SelectMySqlBuilder
 */
public class SelectMySqlBuilder<
		T,
		FX extends FromClauseExtendedBuilder<FX, W, O>,
		F extends FromClauseTerm<W, O, FX>,
		W extends WhereClauseExtendedBuilder<W, G, H, O>,
		G extends DefaultPostGroupByTerm<H>,
		H extends DefaultPostHavingTerm<H, O> & MySqlPostOrderBy<O>,
		O extends MySqlPostOrderBy<O>>

		extends AbstractSelectBuilder<T, F, FX> {

	private SelectMySqlBuilder(){
		super();
		this.setDialect(SqlDialect.MYSQL);
	}

	public static <T, W extends WhereClauseExtendedBuilder<W, G, H, O>, O extends MySqlPostOrderBy<O>,
			FX extends FromClauseExtendedBuilder<FX, W, O>, F extends FromClauseTerm<W, O, FX>,
			G extends DefaultPostGroupByTerm<H>, H extends DefaultPostHavingTerm<H, O> & MySqlPostOrderBy<O>,
			M extends SelectMySqlBuilder<M, FX, F, W, G, H, O>, R extends SelectClauseBuilder<F, FX>>
	R newBuilder(){
		return (R) new SelectMySqlBuilder<M, FX, F, W, G, H, O>();
	}

}
