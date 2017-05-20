package com.chadekin.jadys.syntax.having.mysql;

import com.chadekin.jadys.operators.JunctionOperator;
import com.chadekin.jadys.syntax.orderby.mysql.MySqlPostOrderBy;
import com.chadekin.jadys.syntax.having.HavingStatementOperation;

/**
 * MySqlPostHavingTerm
 */
public interface MySqlPostHaving<T, O> extends JunctionOperator<MySqlPostHaving<HavingStatementOperation<T, O>, O>>, MySqlPostOrderBy<O> {
	
}
