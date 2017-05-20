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
package com.chadekin.jadys.syntax.join;

import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.commons.expression.SqlExpressionHandler;
import com.chadekin.jadys.commons.resourcekeys.JadysExceptionMessageKeys;
import com.chadekin.jadys.commons.resourcekeys.JadysKeys;
import com.chadekin.jadys.validation.JadysArgumentValidator;

/**
 * JoinStatement
 */
public interface JoinStatement<T>  {
	
	public void applyJoin(SqlLexical sqlLexical, String alias, String fullStatement);

	/**
	 * Full JOIN
	 * @param table_name The DB table
	 * @param alias The DB table alias
	 * @return The nStatement<T>
	 */
	public default OnStatement<T> fullJoin(String table_name, String alias){
		appendData(SqlLexical.FULL_JOIN, table_name, alias);
		return (OnStatement<T> ) this;
	}

	/**
	 * Inner JOIN
	 * @param table_name The DB table
	 * @param alias The DB table alias
	 * @return The nStatement<T>
	 */
	public default OnStatement<T> join(String table_name, String alias){
		appendData(SqlLexical.JOIN, table_name, alias);
		return (OnStatement<T>) this;
	}

	/**
	 * Left JOIN
	 * @param table_name The DB table
	 * @param alias The DB table alias
	 * @return The nStatement<T>
	 */
	public default OnStatement<T> leftJoin(String table_name, String alias){
		appendData(SqlLexical.LEFT_JOIN, table_name, alias);
		return (OnStatement<T> ) this;
	}

	/**
	 * Right JOIN
	 * @param table_name The DB table
	 * @param alias The DB table alias
	 * @return The nStatement<T>
	 */
	public default OnStatement<T> rightJoin(String table_name, String alias){
		appendData(SqlLexical.RIGHT_JOIN, table_name, alias);
		return (OnStatement<T>) this;
	}
	
	public default void appendData(SqlLexical sqlLexical, String table_name, String alias){
		JadysArgumentValidator validator = JadysArgumentValidator.newValidator().sqlInjection();
		
		validator.nullValue(JadysExceptionMessageKeys.INVALID_TABLE_NAME)
				.validate(table_name);
		
		validator.nullValue(JadysExceptionMessageKeys.INVALID_ALIAS)
				.validate(alias);
		
		SqlExpressionHandler builder = (SqlExpressionHandler) this;
		StringBuilder arg = new StringBuilder(sqlLexical.getName())
				.append(JadysKeys.SPACE)
				.append(table_name)
				.append(JadysKeys.SPACE)
				.append(alias);
		this.applyJoin(sqlLexical, alias, arg.toString());
	}
}
