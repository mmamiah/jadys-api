/**
 *                     GNU General Public License (GPL)
 *                        version 3.0, 29 June 2007
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 *
 * Copyright (C) 2017 Marc Mamiah.
 * License GPLv3+: GNU GPL version 3
 */
package com.chadekin.jadys;

import java.util.List;
import java.util.Set;
import com.chadekin.jadys.commons.JadysBuilder;
import com.chadekin.jadys.commons.enums.SqlDialect;
import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.commons.expression.SqlExpressionCleaning;
import com.chadekin.jadys.commons.expression.SqlExpressionHandler;

/**
 * JadysSqlQueryBuilder
 */
public interface JadysSqlQueryBuilder<T> extends JadysBuilder<String>, SqlExpressionHandler<T>, SqlExpressionCleaning {

	/**
	 * The builder name: SELECT, FROM, WHERE ...
	 * @return
	 */
	public SqlLexical getType();

	/**
	 * Get the parent builder.
	 * @return the NativeSqlQueryBuilder
	 */
	public JadysSqlQueryBuilder getParent();

	/**
	 * Get the child builder
	 * @return The NativeSqlQueryBuilder
	 */
	public JadysSqlQueryBuilder getChild();

	/**
	 * Set the child builder
	 * @param child The builder to set
	 */
	public void setChild(JadysSqlQueryBuilder child);

	public StringBuilder getResultSql();

	/**
	 * String representation of the SQL Query
	 * @return
	 */
	public String getValue();
	
	public T append(String... value);

	public void setResultSql(StringBuilder resultSql);

	public T withAlias(String alias);

	public Set<String> getAlias();

	/**
	 * Extracting Alias from provided argument
	 * eg: user.name, the alias is 'user.' 
	 * @param argument The string from with the alias should be extracted
	 * @return The collection containing all input alias.
	 */
	public Set<String> extractAlias(String argument);

	public boolean isActiveStatement();

	public void setActiveStatement(boolean activeStatement);

	@Override
	default String build() {
		return null;
	}

	/**
	 * Build the Child next clauses only
	 * @return
	 */
	public String buildNext();

	/**
	 * The Sql dialect
	 * @return
	 */
	public SqlDialect getDialect();

	/**
	 * The Sql Dialect
	 * @param dialect
	 */
	public void setDialect(SqlDialect dialect);


}
