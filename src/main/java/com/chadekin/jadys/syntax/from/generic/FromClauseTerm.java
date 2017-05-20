package com.chadekin.jadys.syntax.from.generic;

import com.chadekin.jadys.syntax.AliasStatement;
import com.chadekin.jadys.syntax.from.generic.FromClauseBuilder;
import com.chadekin.jadys.syntax.from.generic.FromClauseExtendedBuilder;

/**
 * DefaultFrom clause
 * @param <X> Extended From Clause (FromClauseExtendedBuilder)
 * @param <W> Where Clause per SqlDialect
 * @param <O> OrderBy Clause per SqlDialect
 */
public interface FromClauseTerm<W, O, X extends FromClauseExtendedBuilder<X, W, O>> extends FromClauseBuilder<X, W, O>, AliasStatement<X> {
	
}
