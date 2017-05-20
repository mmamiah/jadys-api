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
package com.chadekin.jadys.operators.logical;

import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.commons.enums.JadysSqlOperation;
import com.chadekin.jadys.commons.expression.SqlExpressionHandler;
import com.chadekin.jadys.commons.expression.impl.SqlExpressionFactory;
import com.chadekin.jadys.commons.resourcekeys.JadysKeys;
import com.chadekin.jadys.operators.SqlOperator;
import com.chadekin.jadys.validation.JadysArgumentValidator;
import org.apache.commons.lang.StringUtils;

/**
 * SqlLogicalOperator
 */
public interface SqlLogicalOperator extends SqlOperator {

	public default void executeLogicalOperator(JadysSqlOperation sqlOperation){
		if(this instanceof SqlExpressionHandler){
			SqlExpressionHandler handler = (SqlExpressionHandler)this;
			handler.addArgument(sqlOperation);
		}
	}

	public default void executeLogicalOperator(JadysSqlOperation sqlOperation, String column_name){
		JadysArgumentValidator.newValidator()
				.sqlInjection()
				.validate(column_name);
		if(this instanceof SqlExpressionHandler){
			SqlExpressionHandler handler = (SqlExpressionHandler)this;
			handler.addArgument(sqlOperation);
			handler.addArgument(column_name);
		}
	}

	public default void executeLogicalOperator(JadysSqlOperation sqlOperation, SqlExpressionFactory expression){
		if(this instanceof JadysSqlQueryBuilder){
			JadysSqlQueryBuilder handler = (JadysSqlQueryBuilder)this;
			String subExpression = handler.cleanExpression(expression.build());
			StringBuilder exp = new StringBuilder(JadysKeys.OPEN_PARENTHESIS).append(subExpression).append(JadysKeys.CLOSE_PARENTHESIS);
			handler.append(sqlOperation.getName(), exp.toString());
		}
	}

	public default void executeLogicalOperator(JadysSqlOperation sqlOperation, Object value){
		JadysArgumentValidator.newValidator()
				.sqlInjection()
				.validate(value);
		if(this instanceof SqlExpressionHandler){
			SqlExpressionHandler handler = (SqlExpressionHandler)this;
			handler.addArgument(sqlOperation);
			handler.addArgument(value);
		}
	}
}
