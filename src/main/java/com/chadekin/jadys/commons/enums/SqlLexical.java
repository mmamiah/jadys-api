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

/**
 * SqlBeautifulerSyntax
 */
public enum SqlLexical implements BusinessEnum {

	SELECT("SELECT", "SELECT"),
	DISTINCT("DISTINCT", "DISTINCT"),
	FROM("FROM", "FROM"),
	WHERE("WHERE", "WHERE"),
	JOIN("JOIN", "JOIN"),
	LEFT_JOIN("LEFT_JOIN", "LEFT JOIN"),
	RIGHT_JOIN("RIGHT_JOIN", "RIGHT JOIN"),
	FULL_JOIN("FULL_JOIN", "FULL JOIN"),
	ON("ON", "ON"),
	GROUP_BY("GROUP_BY", "GROUP BY"),
	HAVING("HAVING", "HAVING"),
	ORDER_BY("ORDER_BY", "ORDER BY"),
	AS("AS", "AS"),
	ASC("ASC", "ASC"),
	DESC("DESC", "DESC"),

	// SQL Aggregate Functions
	AVG("AVG", "AVG"),
	COUNT("COUNT", "COUNT"),
	FIRST("FIRST", "FIRST"),
	LAST("LAST", "LAST"),
	MAX("MAX", "MAX"),
	MIN("MIN", "MIN"),
	SUM("SUM", "SUM"),
	LIMIT("LIMIT", "LIMIT"),

	// SQL Scalar Functions
	UCASE("UCASE", "UCASE"),
	LCASE("LCASE", "LCASE"),
	MID("MID", "MID"),
	LEN("LEN", "LEN"),
	ROUND("ROUND", "ROUND"),
	NOW("NOW", "NOW"),
	FORMAT("FORMAT", "FORMAT");
	
	private String code;
	private String name;

	SqlLexical(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
