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
package com.chadekin.jadys.commons.expression;

import java.util.EnumSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import com.chadekin.jadys.commons.enums.ConditionType;
import com.chadekin.jadys.commons.enums.JadysSqlOperation;
import com.chadekin.jadys.model.SqlExpressionItem;
import org.apache.commons.lang.StringUtils;

/**
 * SqlExpressionHandler
 */
public interface SqlExpressionHandler<T> {

	// Collecting SQL Logical Expression like 'AND', 'OR'
	public static final Set<JadysSqlOperation> LOGICAL_OPERATORS = EnumSet.allOf(JadysSqlOperation.class)
			.stream()
			.filter(operator -> operator.getType()== ConditionType.LOGICAL)
			.collect(Collectors.toSet());

	/**
	 * Add an argument to the expression to be included to the actual builder, after verifying it is a valid expression
	 * @param arg
	 * @return
	 */
	public default T addArgument(Object arg){
		SqlExpressionItem expressionItem = getSqlExpressionItem();
		if(expressionItem==null){
			setSqlExpressionItem(new SqlExpressionItem());
		}

		if(LOGICAL_OPERATORS.contains(arg)){
			getSqlExpressionItem().appendLogicalOperator((JadysSqlOperation)arg);
		}
		else if(arg instanceof JadysSqlOperation){
			JadysSqlOperation operator = (JadysSqlOperation) arg;
			TreeSet logicalOperators = getSqlExpressionItem().getLogicalOperator();
			if(!logicalOperators.isEmpty() && logicalOperators.last() == JadysSqlOperation.NOT){
				operator = operator.getOpposite();
				getSqlExpressionItem().getLogicalOperator().remove(JadysSqlOperation.NOT);
			}
			getSqlExpressionItem().setOperator(operator);
		}
		else if(getSqlExpressionItem().getParam()==null){
			String parameter = arg==null ? StringUtils.EMPTY : arg.toString();
			getSqlExpressionItem().setParam(parameter);
		}
		else if(arg == null){
			setSqlExpressionItem(null);
		}
		else if(getSqlExpressionItem().getValue()==null){
			getSqlExpressionItem().setValue(arg);
		}

		if(getSqlExpressionItem()!=null && getSqlExpressionItem().isReady()){
			finalizeExpression();
			setSqlExpressionItem(null);
		}
		return (T) this;
	}
	
	public void setSqlExpressionItem(SqlExpressionItem expressionItem);
	
	public SqlExpressionItem getSqlExpressionItem();

	/**
	 * Add the resulting expression to the actual builder result
	 */
	public void finalizeExpression();
	
}
