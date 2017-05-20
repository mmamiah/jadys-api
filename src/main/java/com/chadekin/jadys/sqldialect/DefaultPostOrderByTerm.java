package com.chadekin.jadys.sqldialect;

import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.commons.JadysBuilder;
import com.chadekin.jadys.syntax.orderby.OrderByStatement;
import com.chadekin.jadys.syntax.orderby.SqlOrderTypeAsc;
import com.chadekin.jadys.syntax.orderby.SqlOrderTypeDesc;

/**
 * AfterOrderByOperation
 */
public interface DefaultPostOrderByTerm<O> extends JadysBuilder<String>, SqlOrderTypeAsc<O>, SqlOrderTypeDesc<O> {
	
}
