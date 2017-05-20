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
package com.chadekin.jadys.syntax.select.type;

import com.chadekin.jadys.commons.ApplySqlExpression;
import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.commons.resourcekeys.JadysExceptionMessageKeys;
import com.chadekin.jadys.commons.resourcekeys.JadysKeys;
import com.chadekin.jadys.validation.JadysArgumentValidator;

/**
 * DistinctStatement
 */
public interface SelectDistinctStatement<D> {

	/**
	 * The SELECT DISTINCT clause is used to return only distinct (different) values.
	 * @param column_name The column name
	 * @return D
	 */
	public default D selectDistinct(String column_name){
		JadysArgumentValidator.newValidator()
				.nullValue(JadysExceptionMessageKeys.INVALID_COLUMN_NAME)
				.sqlInjection()
				.validate(column_name);
		if(this instanceof ApplySqlExpression){
			ApplySqlExpression builder = (ApplySqlExpression) this;
			StringBuilder value = new StringBuilder(SqlLexical.DISTINCT.getName()).append(JadysKeys.OPEN_PARENTHESIS).append(column_name).append(JadysKeys.CLOSE_PARENTHESIS);
			builder.apply(value.toString());
		}
		return (D) this;
	}
}
