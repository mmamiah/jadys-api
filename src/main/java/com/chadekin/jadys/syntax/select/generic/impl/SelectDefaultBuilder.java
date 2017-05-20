package com.chadekin.jadys.syntax.select.generic.impl;

import com.chadekin.jadys.commons.enums.SqlDialect;
import com.chadekin.jadys.syntax.groupby.generic.DefaultPostGroupByTerm;
import com.chadekin.jadys.syntax.having.generic.DefaultPostHavingTerm;
import com.chadekin.jadys.syntax.orderby.generic.DefaultPostOrderByTerm;
import com.chadekin.jadys.syntax.from.generic.FromClauseTerm;
import com.chadekin.jadys.syntax.from.generic.FromClauseExtendedBuilder;
import com.chadekin.jadys.syntax.select.AbstractSelectBuilder;
import com.chadekin.jadys.syntax.select.generic.SelectClauseBuilder;
import com.chadekin.jadys.syntax.where.generic.WhereClauseExtendedBuilder;

/**
 * SelectDefaultBuilder
 */
public class SelectDefaultBuilder<
		T,
		FX extends FromClauseExtendedBuilder<FX, W, O>,
		F extends FromClauseTerm<W, O, FX>,
		W extends WhereClauseExtendedBuilder<W, G, H, O>,
		G extends DefaultPostGroupByTerm<H>,
		H extends DefaultPostHavingTerm<H, O>,
		O extends DefaultPostOrderByTerm<O>> 
		
		extends AbstractSelectBuilder<T, F, FX> {

	private SelectDefaultBuilder(){
		super();
		this.setDialect(SqlDialect.NONE);
	}

	public static <T, W extends WhereClauseExtendedBuilder<W, G, H, O>, O extends DefaultPostOrderByTerm<O>, 
					FX extends FromClauseExtendedBuilder<FX, W, O>, F extends FromClauseTerm<W, O, FX>,
					G extends DefaultPostGroupByTerm<H>, H extends DefaultPostHavingTerm<H, O>,
					DS extends SelectDefaultBuilder<DS, FX, F, W, G, H, O>, R extends SelectClauseBuilder<F, FX>>
	R newBuilder(){
		return (R) new SelectDefaultBuilder<DS, FX, F, W, G, H, O>();
	}
	
}
