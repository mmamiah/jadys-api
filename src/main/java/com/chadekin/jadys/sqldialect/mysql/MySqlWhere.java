package com.chadekin.jadys.sqldialect.mysql;

import com.chadekin.jadys.syntax.where.WhereClauseExtendedBuilder;

/**
 * WhereMySql
 * @param <X> Extended Where Clause (WhereClauseExtendedBuilder)
 * @param <O> OrderBy clause per sql dialect        
 */
public interface MySqlWhere<X extends WhereClauseExtendedBuilder<X, G, H, O>, G, H, O> extends WhereClauseExtendedBuilder<X, G, H, O> {
	
}
