package com.chadekin.jadys.syntax.from.generic;

import com.chadekin.jadys.operators.ParameterOperator;
import com.chadekin.jadys.syntax.join.JoinStatement;
import com.chadekin.jadys.syntax.join.OnStatement;

/**
 * FromClauseExtendedBuilder
 * @param <X> Extended From Clause (FromClauseExtendedBuilder)
 * @param <W> Where Clause per SqlDialect
 * @param <O> OrderBy Clause per SqlDialect
 */
public interface FromClauseExtendedBuilder<X extends FromClauseExtendedBuilder<X, W, O>, W, O> extends FromClauseTerm<W, O, X>, JoinStatement<X>, OnStatement<X>, ParameterOperator<JoinStatement<X>> {
	
}
