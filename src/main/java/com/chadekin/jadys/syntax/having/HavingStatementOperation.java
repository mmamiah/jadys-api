package com.chadekin.jadys.syntax.having;

import com.chadekin.jadys.operators.ParameterOperator;
import com.chadekin.jadys.syntax.having.generic.DefaultPostHavingTerm;
import com.chadekin.jadys.sqlfunction.SqlFunctionProxy;

/**
 * HavingStatementOperation
 * @param <T> Object type needed for Looping into Junction operation
 * @param <O> The OrderBy Clause per Sql Dialect
 */
public interface HavingStatementOperation<T, O> extends SqlFunctionProxy<ParameterOperator<DefaultPostHavingTerm<T, O>>> {
	
}
