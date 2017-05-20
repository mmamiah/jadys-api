package com.chadekin.jadys.syntax.orderby.mysql;

import com.chadekin.jadys.syntax.orderby.OrderByStatement;

/**
 * OrderByClauseMySqlBuilder
 */
public interface OrderByClauseMySqlBuilder<O> extends OrderByStatement<O>, MySqlPostOrderBy<OrderByClauseMySqlBuilder<O>> {

}
