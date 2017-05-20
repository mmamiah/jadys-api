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
package com.chadekin.jadys.sqlfunction.aggregate.commons;

import java.util.stream.Stream;
import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.commons.resourcekeys.JadysKeys;
import com.chadekin.jadys.sqlfunction.SqlFunction;

/**
 * Count
 */
public interface CountSqlFunction<T> extends SqlFunction {

	/**
	 * The COUNT() function returns the number of rows that matches a specified criteria.
	 * @param column_name The column name
	 * @return T
	 */
	public default T count(String... column_name){
		if(column_name!=null && column_name.length > 0){
			Stream.of(column_name)
					.forEach(col -> {
						executeFunction(col, SqlLexical.COUNT);
					});
		}
		else {
			executeFunction(JadysKeys.STAR, SqlLexical.COUNT);
		}
		return (T) this;
	}
}
