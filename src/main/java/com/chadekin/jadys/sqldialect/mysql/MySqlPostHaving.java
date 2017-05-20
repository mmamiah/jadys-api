package com.chadekin.jadys.sqldialect.mysql;

import com.chadekin.jadys.operators.JunctionOperator;
import com.chadekin.jadys.syntax.having.HavingStatementOperation;
import com.chadekin.jadys.syntax.orderby.OrderByStatement;

/**
 * MySqlPostHavingTerm
 */
public interface MySqlPostHaving<T, O> extends JunctionOperator<MySqlPostHaving<HavingStatementOperation<T, O>, O>>, MySqlPostOrderBy<O> {
	
}
