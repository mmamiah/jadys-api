package com.chadekin.jadys.sqldialect.mysql;

import com.chadekin.jadys.commons.JadysBuilder;
import com.chadekin.jadys.sqldialect.DefaultPostOrderByTerm;
import com.chadekin.jadys.sqlfunction.aggregate.mysqldialect.LimitClause;

/**
 * MySqlDialect
 */
public interface MySqlPostOrderBy<O> extends JadysBuilder<String>, LimitClause<MySqlPostOrderBy<O>> {
	
}
