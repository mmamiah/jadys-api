package com.chadekin.jadys.sqldialect.sqlserver;

import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.sqlfunction.SqlFunctionProxy;
import com.chadekin.jadys.syntax.AliasStatement;
import com.chadekin.jadys.syntax.from.FromStatement;
import com.chadekin.jadys.syntax.select.SelectColumn;

/**
 * SelectStatementForSqlServer
 */
public interface SqlServerSelect<T extends JadysSqlQueryBuilder> extends JadysSqlQueryBuilder<T>, SelectColumn<T>, SqlFunctionProxy<T>, FromStatement<T>, AliasStatement<SqlServerSelect<T>> {
}
