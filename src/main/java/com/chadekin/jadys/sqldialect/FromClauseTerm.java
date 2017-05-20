package com.chadekin.jadys.sqldialect;

import com.chadekin.jadys.syntax.AliasStatement;
import com.chadekin.jadys.syntax.from.FromClauseBuilder;
import com.chadekin.jadys.syntax.from.FromClauseExtendedBuilder;

/**
 * DefaultFrom clause
 * @param <X> Extended From Clause (FromClauseExtendedBuilder)
 * @param <W> Where Clause per SqlDialect
 * @param <O> OrderBy Clause per SqlDialect
 */
public interface FromClauseTerm<W, O, X extends FromClauseExtendedBuilder<X, W, O>> extends FromClauseBuilder<X, W, O>, AliasStatement<X> {
	
}
