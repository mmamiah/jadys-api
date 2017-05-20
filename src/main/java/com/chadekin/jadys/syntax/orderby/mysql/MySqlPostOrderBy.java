package com.chadekin.jadys.syntax.orderby.mysql;

import com.chadekin.jadys.commons.JadysBuilder;
import com.chadekin.jadys.sqlfunction.aggregate.mysqldialect.LimitClause;
import com.chadekin.jadys.syntax.orderby.SqlOrderTypeAsc;
import com.chadekin.jadys.syntax.orderby.SqlOrderTypeDesc;

/**
 * MySqlDialect
 */
public interface MySqlPostOrderBy<O> extends JadysBuilder<String>, SqlOrderTypeAsc<O>, SqlOrderTypeDesc<O>, LimitClause<MySqlPostOrderBy<O>> {
	
}
