package com.chadekin.jadys.sqlfunction.aggregate.mysqldialect;

import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.sqlfunction.SqlFunction;

/**
 * LimitClause
 */
public interface LimitClause<O> extends SqlFunction {
	
	public default O limit(int number){
		executeFunction(number, SqlLexical.LIMIT);
		return (O) this;
	}
}
