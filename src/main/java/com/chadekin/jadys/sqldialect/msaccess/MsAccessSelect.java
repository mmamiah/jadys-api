package com.chadekin.jadys.sqldialect.msaccess;

import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.sqlfunction.SqlFunctionProxy;
import com.chadekin.jadys.syntax.AliasStatement;
import com.chadekin.jadys.syntax.from.FromStatement;
import com.chadekin.jadys.syntax.select.SelectColumn;

/**
 * SelectStatementForMsAccess
 */
public interface MsAccessSelect<T extends JadysSqlQueryBuilder> extends JadysSqlQueryBuilder<T>, SelectColumn<T>, SqlFunctionProxy<T>, FromStatement<T>, AliasStatement<MsAccessSelect<T>> {
}
