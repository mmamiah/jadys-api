package com.chadekin.jadys.syntax.orderby.mysql.impl;

import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.syntax.orderby.AbstractOrderByClauseBuilder;
import com.chadekin.jadys.syntax.orderby.mysql.OrderByClauseMySqlBuilder;

/**
 * OrderByClauseMySqlBuilderImpl
 */
public class OrderByClauseMySqlBuilderImpl<P> extends AbstractOrderByClauseBuilder<OrderByClauseMySqlBuilder<P>> implements OrderByClauseMySqlBuilder<OrderByClauseMySqlBuilder<P>> {
	
	protected OrderByClauseMySqlBuilderImpl(JadysSqlQueryBuilder parent) {
		super(parent);
	}

	public static <R> OrderByClauseMySqlBuilderImpl<R> newStatement(JadysSqlQueryBuilder parent){
		return new OrderByClauseMySqlBuilderImpl<R>(parent);
	}
	
}
