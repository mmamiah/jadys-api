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

import com.chadekin.jadys.commons.enums.ConditionType;
import com.chadekin.jadys.commons.formatters.SqlValueFormatterFactory;
import com.chadekin.jadys.commons.JadysBuilder;
import com.chadekin.jadys.commons.Operator;
import com.chadekin.jadys.commons.expression.SqlExpressionBuilder;
import org.apache.commons.lang.StringUtils;

/**
 * DefaultExpressionBuilder
 */
public class DefaultExpressionBuilder implements SqlExpressionBuilder {

	private Object value;
	private Operator operator;

	@Override
	public ConditionType getOperatorType() {
		return ConditionType.COMPARISON;
	}

	@Override
	public JadysBuilder<String> init(Operator operator, Object value) {
		this.operator = operator;
		this.value = value;
		return this;
	}

	@Override
	public String build() {
		if(SqlExpressionBuilder.isBlankValue(value)){
			return StringUtils.EMPTY;
		}
		StringBuilder result = new StringBuilder(operator.getOperator())
				.append(SqlValueFormatterFactory.format(value).build());

		return result.toString();
	}
}
