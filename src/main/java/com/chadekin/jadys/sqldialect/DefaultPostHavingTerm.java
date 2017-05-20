package com.chadekin.jadys.sqldialect;

import com.chadekin.jadys.operators.JunctionOperator;
import com.chadekin.jadys.syntax.having.HavingStatement;
import com.chadekin.jadys.syntax.having.HavingStatementOperation;
import com.chadekin.jadys.syntax.orderby.OrderByStatement;

/**
 * PostHavingDefault
 * This interface is used for encapsulating given operation returned by some method
 * @see HavingStatement
 * @see HavingStatementOperation
 * 
 * @param <T> Object type needed for Looping into Junction operation
 * @param <O> The OrderBy Clause per Sql Dialect
 */
public interface DefaultPostHavingTerm<T, O> extends JunctionOperator<DefaultPostHavingTerm<HavingStatementOperation<T, O>, O>>, OrderByStatement<O> {
	
}
