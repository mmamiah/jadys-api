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

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import com.chadekin.jadys.commons.JadysBuilder;
import com.chadekin.jadys.commons.Operator;
import com.chadekin.jadys.commons.enums.ConditionType;
import com.chadekin.jadys.commons.enums.JadysSqlOperation;
import com.chadekin.jadys.commons.expression.SqlExpressionBuilder;
import com.chadekin.jadys.commons.expression.SqlExpressionCleaning;
import com.chadekin.jadys.commons.resourcekeys.JadysKeys;
import com.chadekin.jadys.model.SqlExpressionItem;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Interface representing parameter and value comparison
 */
public class SqlExpressionFactory implements JadysBuilder<String>, SqlExpressionCleaning {
	
	private List<SqlExpressionItem> items;
	private Map<ConditionType, SqlExpressionBuilder> expressionHandler = Maps.newHashMap();

	private SqlExpressionFactory(){
		items = Lists.newLinkedList();
		initHandlers(this.expressionHandler);
	}

	private SqlExpressionFactory(String param, Operator operator, Object value){
		this();
		if(StringUtils.isBlank(param) || operator ==null) {
			throw new IllegalArgumentException("An Expression can not be empty.");
		}
		items.add(new SqlExpressionItem(param, operator, value));
	}

	private SqlExpressionFactory(SqlExpressionItem expression){
		this();
		if(expression==null) {
			throw new IllegalArgumentException("An Expression can not be empty.");
		}
		items.add(expression);
	}

	private static void initHandlers(Map<ConditionType, SqlExpressionBuilder> handler){
		SqlExpressionBuilder like = new LikeExpressionBuilder();
		handler.put(like.getOperatorType(), like);

		InExpressionBuilder in = new InExpressionBuilder();
		handler.put(in.getOperatorType(), in);

		AlphaExpressionBuilder alpha = new AlphaExpressionBuilder();
		handler.put(alpha.getOperatorType(), alpha);

		LogicalExpressionBuilder logical = new LogicalExpressionBuilder();
		handler.put(logical.getOperatorType(), logical);

		DefaultExpressionBuilder defaultOp = new DefaultExpressionBuilder();
		handler.put(defaultOp.getOperatorType(), defaultOp);

		NullTestingExpressionBuilder nullTesting = new NullTestingExpressionBuilder();
		handler.put(nullTesting.getOperatorType(), nullTesting);
		
	}

	public static SqlExpressionFactory newExpression(){
		return new SqlExpressionFactory();
	}
	
	public static SqlExpressionFactory newExpression(String param, JadysSqlOperation equality, Object value){
		return new SqlExpressionFactory(param, equality, value);
	}

	public static SqlExpressionFactory newExpression(SqlExpressionItem expression){
		return new SqlExpressionFactory(expression);
	}

	public static SqlExpressionFactory newExpression(String param, JadysSqlOperation ntOperator){
		return new SqlExpressionFactory(param, ntOperator, null);
	}

	public SqlExpressionFactory and(String param, JadysSqlOperation equalityOperator, Object value){
		items.add(new SqlExpressionItem(JadysSqlOperation.AND, param, equalityOperator, value));
		return this;
	}

	public SqlExpressionFactory and(String param, JadysSqlOperation equality){
		items.add(new SqlExpressionItem(JadysSqlOperation.AND, param, equality, null));
		return this;
	}

	public SqlExpressionFactory and(SqlExpressionFactory expressionFactory){
		items.add(new SqlExpressionItem(JadysSqlOperation.AND, expressionFactory.build()));
		return this;
	}

	public SqlExpressionFactory or(String param, JadysSqlOperation equalityOperator, Object value){
		items.add(new SqlExpressionItem(JadysSqlOperation.OR, param, equalityOperator, value));
		return this;
	}

	public SqlExpressionFactory or(String param, JadysSqlOperation ntOperator){
		items.add(new SqlExpressionItem(JadysSqlOperation.OR, param, ntOperator, null));
		return this;
	}

	public SqlExpressionFactory or(SqlExpressionFactory expressionFactory){
		items.add(new SqlExpressionItem(JadysSqlOperation.OR, expressionFactory.build()));
		return this;
	}

	public SqlExpressionFactory not(String param, JadysSqlOperation equalityOperator, Object value){
		items.add(new SqlExpressionItem(JadysSqlOperation.NOT, param, equalityOperator, value));
		return this;
	}

	public SqlExpressionFactory not(String param, JadysSqlOperation ntOperator){
		items.add(new SqlExpressionItem(JadysSqlOperation.NOT, param, ntOperator, null));
		return this;
	}

	public SqlExpressionFactory not(SqlExpressionFactory expressionFactory){
		items.add(new SqlExpressionItem(JadysSqlOperation.NOT, expressionFactory.build()));
		return this;
	}

	public SqlExpressionFactory xor(String param, JadysSqlOperation equalityOperator, Object value){
		items.add(new SqlExpressionItem(JadysSqlOperation.XOR, param, equalityOperator, value));
		return this;
	}

	public SqlExpressionFactory xor(String param, JadysSqlOperation ntOperator){
		items.add(new SqlExpressionItem(JadysSqlOperation.XOR, param, ntOperator, null));
		return this;
	}

	public SqlExpressionFactory xor(SqlExpressionFactory expressionFactory){
		items.add(new SqlExpressionItem(JadysSqlOperation.XOR, expressionFactory.build()));
		return this;
	}

	@Override
	public String build() {
		StringBuilder result =new StringBuilder();
		for(SqlExpressionItem item : items){
			String itemResult = StringUtils.isBlank(item.getExpression()) ? buildItem(item) : item.getExpression();
			if(StringUtils.isBlank(itemResult)){
				continue;
			}
			if(!item.getLogicalOperator().isEmpty()){
				itemResult = cleanExpression(itemResult);
				if(result.length()>0){
					result.append(JadysKeys.SPACE);
				}
				item.getLogicalOperator().stream()
						.forEach(op -> result.append(op).append(JadysKeys.SPACE));
			}
			result.append(itemResult);
		}
		return result.toString().trim();
	}
	
	private String buildItem(SqlExpressionItem item){
		boolean isBlank = !(item.getOperator() instanceof JadysSqlOperation) || isBlankValue(item.getValue());
		boolean isUnary = item.getOperator() != null && item.getOperator().isUnary();
		if(!isUnary && (isBlank || StringUtils.isBlank(item.getParam()))){
			return StringUtils.EMPTY;
		}

		StringBuilder result = new StringBuilder();
		if(!item.getParam().equals(JadysKeys.BLANK)){
			result.append(item.getParam());
		}

		SqlExpressionBuilder builder = expressionHandler.get(item.getOperator().getType());
		String builderValue = builder.init(item.getOperator(), item.getValue()).build();
		if(StringUtils.isNotBlank(builderValue)){
			result.append(builderValue);
		}
		else{
			result.setLength(0);
		}
		return result.toString();
	}
	
	private static boolean isBlankValue(Object value){
		boolean isBlank = value==null || StringUtils.isBlank(value.toString());
		if(!isBlank){
			boolean isEmptyCollection = value instanceof Collection && CollectionUtils.isEmpty((Collection)value);
			boolean isEmptyArray = value.getClass().isArray() && Array.getLength(value)==0;
			isBlank = isEmptyCollection || isEmptyArray;
		}
		return isBlank;
	}

}
