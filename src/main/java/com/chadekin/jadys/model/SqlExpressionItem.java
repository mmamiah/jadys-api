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
package com.chadekin.jadys.model;

import java.util.TreeSet;
import com.chadekin.jadys.commons.Operator;
import com.chadekin.jadys.commons.enums.JadysSqlOperation;
import com.chadekin.jadys.commons.resourcekeys.JadysKeys;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;

/**
 * SqlExpressionItem
 */
public class SqlExpressionItem {

	private String param;
	private Operator operator;
	private Object value;
	private TreeSet<JadysSqlOperation> logicalOperator = Sets.newTreeSet();
	private String expression;

	public SqlExpressionItem(JadysSqlOperation logicalOperator, String param, Operator operator, Object value) {
		this.param = param;
		this.operator = operator;
		this.value = value;
		if(logicalOperator!=null){
			this.logicalOperator.add(logicalOperator);
		}
	}

	public SqlExpressionItem() {
		this(null, null, null, null);
	}

	public SqlExpressionItem(JadysSqlOperation logicalOperator, String expression) {
		this.logicalOperator.add(logicalOperator);
		this.expression = expression;
	}

	public SqlExpressionItem(String param, Operator operator, Object value) {
		this(null, param, operator, value);
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param=param;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public TreeSet<JadysSqlOperation> getLogicalOperator() {
		return logicalOperator;
	}

	public void appendLogicalOperator(JadysSqlOperation operator) {
		this.logicalOperator.add(operator);
	}

	public String getExpression() {
		return expression;
	}

	public boolean isReady(){
		boolean isReady = false;
		boolean isReadyPrefix = param!=null && operator!=null;
		if(isReadyPrefix && operator.isUnary()){
			isReady=true;
		}
		return isReady || (isReadyPrefix && value!=null);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		if(!logicalOperator.isEmpty()){
			String logicalOpName = logicalOperator.stream()
					.map(op -> op.getName())
					.reduce((a, b) -> JadysKeys.SPACE + a + JadysKeys.SPACE + b + JadysKeys.SPACE)
					.orElse(StringUtils.EMPTY)
					.trim();
			result.append(logicalOpName);
		}
		if(StringUtils.isNotBlank(param)){
			if(result.length()>0){
				result.append(JadysKeys.SPACE);
			}
			result.append(param);
		}
		if(operator!=null){
			result.append(operator.getOperator());
		}
		if(value!=null){
			result.append(value.toString());
		}
		return result.toString();
	}
	
}
