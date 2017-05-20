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
import com.chadekin.jadys.commons.resourcekeys.JadysExceptionMessageKeys;
import com.chadekin.jadys.operators.ParameterOperator;
import com.chadekin.jadys.validation.JadysArgumentValidator;

/**
 * OnStatement
 */
public interface OnStatement<T> {
	
	public default ParameterOperator<T> on(String column_name){
		JadysArgumentValidator.newValidator()
				.sqlInjection()
				.nullValue(JadysExceptionMessageKeys.INVALID_COLUMN_NAME)
				.validate(column_name);
		if(this instanceof JoinStatement){
			JoinStatement handler = (JoinStatement) this;
			handler.applyJoin(SqlLexical.ON, null, column_name);
		}
		return (ParameterOperator<T>) this;
	}
	
}
