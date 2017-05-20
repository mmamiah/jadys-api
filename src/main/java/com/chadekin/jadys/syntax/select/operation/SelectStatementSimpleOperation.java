package com.chadekin.jadys.syntax.select.operation;

import com.chadekin.jadys.sqlfunction.SqlFunctionProxy;
import com.chadekin.jadys.syntax.from.FromStatement;
import com.chadekin.jadys.syntax.select.SelectColumn;

/**
 * SelectStatementSimpleOperation
 */
public interface SelectStatementSimpleOperation<F, R> extends SelectColumn<SelectStatementOperation<F, R>>, SqlFunctionProxy<SelectStatementOperation<F, R>>, FromStatement<R> {
	
}
