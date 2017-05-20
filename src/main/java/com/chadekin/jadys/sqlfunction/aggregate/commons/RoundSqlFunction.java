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

import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.commons.resourcekeys.JadysExceptionMessageKeys;
import com.chadekin.jadys.commons.resourcekeys.JadysKeys;
import com.chadekin.jadys.sqlfunction.SqlFunction;
import com.chadekin.jadys.validation.JadysArgumentValidator;

/**
 * RoundSqlFunction
 */
public interface RoundSqlFunction<T> extends SqlFunction {

	/**
	 * The ROUND() function is used to round a numeric field to the number of decimals specified.
	 * @param column_name  	Required. The field to round.
	 * @param decimals Required. Specifies the number of decimals to be returned.
	 * @return T
	 */
	public default T round(String column_name, int decimals){
		JadysArgumentValidator.newValidator()
				.nullValue(JadysExceptionMessageKeys.INVALID_COLUMN_NAME)
				.sqlInjection()
				.validate(column_name);

		if(this instanceof JadysSqlQueryBuilder){
			StringBuilder value = new StringBuilder(SqlLexical.ROUND.getName())
					.append(JadysKeys.OPEN_PARENTHESIS)
					.append(column_name).append(JadysKeys.COMMA).append(decimals)
					.append(JadysKeys.CLOSE_PARENTHESIS);
			JadysSqlQueryBuilder builder = (JadysSqlQueryBuilder) this;
			builder.append(value.toString());
		}
		return (T) this;
	}

}
