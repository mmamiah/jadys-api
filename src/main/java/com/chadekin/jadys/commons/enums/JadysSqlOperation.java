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
package com.chadekin.jadys.commons.enums;

import com.chadekin.jadys.commons.Operator;
import org.apache.commons.lang.StringUtils;

/**
 * EqualityOperator
 */
public enum JadysSqlOperation implements Operator {

	NONE("NONE", StringUtils.EMPTY, ConditionType.COMPARISON, true),
	EQUAL("EQUAL", "=", ConditionType.COMPARISON, false),
	LESS_THAN("LESS_THAN", "<", ConditionType.COMPARISON, false),
	GREATER_THAN("GREATER_THAN", ">", ConditionType.COMPARISON, false),
	LESS_THAN_OR_EQUAL("LESS_THAN_OR_EQUAL", "<=", ConditionType.COMPARISON, false),
	GREATER_THAN_OR_EQUAL("GREATER_THAN_OR_EQUAL", ">=", ConditionType.COMPARISON, false),
	IS_NULL("IS_NULL", "IS NULL", ConditionType.NULL_TESTING, true),
	IS_NOT_NULL("IS_NOT_NULL", "IS NOT NULL", ConditionType.NULL_TESTING, true),
	IN("IN", "IN", ConditionType.LIST_FILTERING, false),
	AND("AND", "AND", ConditionType.LOGICAL, false),
	OR("OR", "OR", ConditionType.LOGICAL, false),
	NOT("NOT", "NOT", ConditionType.LOGICAL, false), //ConditionType.ALPHA
	XOR("XOR", "XOR", ConditionType.LOGICAL, false),
	LIKE("LIKE", "LIKE", ConditionType.PATTERN_MATCHING, false),
	NOT_EQUAL("NOT_EQUAL", "<>", ConditionType.COMPARISON, false),
	NOT_IN("NOT_IN", "NOT IN", ConditionType.LIST_FILTERING, false);
	
	private String code;
	private String operator;
	private JadysSqlOperation opposite;
	private ConditionType type;
	private boolean unary;

	JadysSqlOperation(String code, String operator, ConditionType type, boolean unary){
		this.code = code;
		this.operator = operator;
		this.type = type;
		this.unary = unary;
	}
	
	static {
		NONE.opposite = NONE;
		EQUAL.opposite = NOT_EQUAL;
		LESS_THAN.opposite = GREATER_THAN_OR_EQUAL;
		GREATER_THAN.opposite = LESS_THAN_OR_EQUAL;
		LESS_THAN_OR_EQUAL.opposite = GREATER_THAN;
		GREATER_THAN_OR_EQUAL.opposite = LESS_THAN;
		IS_NULL.opposite = IS_NOT_NULL;
		IS_NOT_NULL.opposite = IS_NULL;
		IN.opposite = NOT_IN;
		AND.opposite = OR;
		OR.opposite = AND;
		NOT.opposite = NONE;
		LIKE.opposite = NONE;
		NOT_EQUAL.opposite = EQUAL;
		NOT_IN.opposite = IN;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getName() {
		return operator;
	}

	@Override
	public String getOperator() {
		return operator;
	}

	@Override
	public ConditionType getType() {
		return type;
	}

	@Override
	public boolean isUnary() {
		return unary;
	}

	public JadysSqlOperation getOpposite() {
		return opposite;
	}
}
