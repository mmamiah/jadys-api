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
package com.chadekin.jadys.commons.expression.impl;

import java.util.Collection;
import com.chadekin.jadys.commons.enums.ConditionType;
import com.chadekin.jadys.commons.formatters.CollectionValueFormatter;
import com.chadekin.jadys.commons.JadysBuilder;
import com.chadekin.jadys.commons.Operator;
import com.chadekin.jadys.commons.expression.SqlExpressionBuilder;
import com.chadekin.jadys.commons.resourcekeys.JadysKeys;

/**
 * Builder for SQL 'IN' operation
 */
public class InExpressionBuilder implements SqlExpressionBuilder {
	
	private Object value;
	private Operator operator;

	public InExpressionBuilder(){
		this.value = null;
	}
	
	public InExpressionBuilder(Object value){
		this.value = value;
	}

	@Override
	public ConditionType getOperatorType() {
		return ConditionType.LIST_FILTERING;
	}

	@Override
	public JadysBuilder<String> init(Operator operator, Object value) {
		this.operator = operator;
		this.value = value;
		return this;
	}

	@Override
	public String build() {
		StringBuilder result = new StringBuilder(JadysKeys.SPACE)
				.append(operator.getOperator())
				.append(JadysKeys.SPACE);
		if(value instanceof Collection){
			String str = new CollectionValueFormatter().format(value);
			result.append(str);
		}
		else{
			result.append(JadysKeys.OPEN_PARENTHESIS)
					.append(value)
					.append(JadysKeys.CLOSE_PARENTHESIS);
		}
		return result.toString();
	}
	
}
