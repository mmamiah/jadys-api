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

import java.util.List;
import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.commons.resourcekeys.JadysExceptionMessageKeys;
import com.chadekin.jadys.commons.resourcekeys.JadysKeys;
import com.chadekin.jadys.validation.JadysArgumentValidator;
import com.google.common.collect.Lists;

/**
 * SelectBaseStatement
 * @param <S> Operation available at Select Statement Level
 */
public interface SelectBaseStatement<S> {

	public default S select(String... column_name){
		JadysArgumentValidator validator = JadysArgumentValidator.newValidator()
				.sqlInjection()
				.nullValue(JadysExceptionMessageKeys.INVALID_COLUMN_NAME);
		if(this instanceof JadysSqlQueryBuilder){
			StringBuilder result = new StringBuilder();
			if(column_name.length>0){
				List<String> columns = Lists.newArrayList(column_name);
				for(String column: columns){
					validator.validate(column);
					if(result.length()!=0){
						result.append(JadysKeys.COMMA)
								.append(JadysKeys.SPACE);
					}
					result.append(column);
				}
			}
			((JadysSqlQueryBuilder) this).append(result.toString().trim());
		}
		return (S) this;
	}
	
}
