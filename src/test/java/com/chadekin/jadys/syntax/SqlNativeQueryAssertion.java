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
package com.chadekin.jadys.syntax;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Assertion for SqlNativeQueryITest
 */
public class SqlNativeQueryAssertion {

	// AND(\s\S*(?!\sAND)){0,4}\S\s
	private static String REGEX_SELECT = "SELECT(\\s\\S*)";
	private static String REGEX_FROM = "FROM(\\s\\S*){1,2}";
	private static String REGEX_JOIN = "JOIN(\\s\\S*){4}";
	private static String REGEX_WHERE = "WHERE\\s\\S*(\\s\\S*){4} ";
	private static String REGEX_AND = "AND(\\s\\S*(?!\\sAND)){0,4}\\S\\s";
	private static String REGEX_GROUP_BY = "GROUP BY\\s\\S* ";
	private static String REGEX_HAVING = "HAVING(\\s\\S*)";
	private static String REGEX_ORDER_BY = "ORDER BY(\\s\\S*){2}";

	protected static void assertThatSelect(String sql){
		Pattern pattern = Pattern.compile(REGEX_SELECT);
		Matcher matcher = pattern.matcher(sql);

		matcher.find();
		assertThat(matcher.group(0), is("SELECT COUNT(subQuery.*)"));

		matcher.find();
		assertThat(matcher.group(0), is("SELECT cus.Id_,"));
	}

	protected static void assertThatFrom(String sql){
		Pattern pattern = Pattern.compile(REGEX_FROM);
		Matcher matcher = pattern.matcher(sql);

		matcher.find();
		assertThat(matcher.group(0), is("FROM (SELECT cus.Id_,"));

		matcher.find();
		assertThat(matcher.group(0), is("FROM dct_customer AS"));
	}

	protected static void assertThatJoin(String sql){
		Pattern pattern = Pattern.compile(REGEX_JOIN);
		Matcher matcher = pattern.matcher(sql);

		matcher.find();
		assertThat(matcher.group(0), is("JOIN dct_location loc ON loc.customerId=cus.id_"));
	}

	protected static void assertThatWhere(String sql){
		Pattern pattern = Pattern.compile(REGEX_WHERE);
		Matcher matcher = pattern.matcher(sql);

		matcher.find();
		assertThat(matcher.group(0), is("WHERE cus.companyId=142576 AND cus.customerId=1258 AND cus.customerName "));
	}
	
	protected static void assertThatAnd(String sql){
		Pattern pattern = Pattern.compile(REGEX_AND);
		Matcher matcher = pattern.matcher(sql);

		matcher.find();
		assertThat(matcher.group(0), is("AND cus.customerId=1258 "));

		matcher.find();
		assertThat(matcher.group(0), is("AND cus.customerName LIKE '%BSD GmbH%' "));

		matcher.find();
		assertThat(matcher.group(0), is("AND cus.city LIKE '%Paris%' "));

		matcher.find();
		assertThat(matcher.group(0), is("AND cus.externalId LIKE '%456753%' "));

		matcher.find();
		assertThat(matcher.group(0), is("AND cus.customerType LIKE '%KEY_ACCOUNT%' "));

		matcher.find();
		assertThat(matcher.group(0), is("AND cus.modifiedDate>='2016-10-14' "));

		matcher.find();
		assertThat(matcher.group(0), is("AND cus.modifiedDate<='2016-12-14' "));

		matcher.find();
		assertThat(matcher.group(0), is("AND cus.modifiedByUserId IN (125,36,587) GROUP "));
		
	}

	protected static void assertThatGroupBy(String sql){
		Pattern pattern = Pattern.compile(REGEX_GROUP_BY);
		Matcher matcher = pattern.matcher(sql);

		matcher.find();
		assertThat(matcher.group(0), is("GROUP BY loc.customerId "));
	}

	protected static void assertThatHaving(String sql){
		Pattern pattern = Pattern.compile(REGEX_HAVING);
		Matcher matcher = pattern.matcher(sql);

		matcher.find();
		assertThat(matcher.group(0), is("HAVING COUNT(loc.customerId)=2"));
	}

	protected static void assertThatOrderBy(String sql){
		Pattern pattern = Pattern.compile(REGEX_ORDER_BY);
		Matcher matcher = pattern.matcher(sql);

		matcher.find();
		assertThat(matcher.group(0), is("ORDER BY cus.externalId ASC)"));
	}
	
	protected static void assertThatAll(String sql){
		assertThat(sql, is("" +
				"SELECT COUNT(subQuery.*) " +
				"FROM " +
				"(" +
				"SELECT cus.Id_, COUNT(cus.*), COUNT(cus.numberOfLocation) " +
				"FROM dct_customer AS cus " +
				"JOIN dct_location loc ON loc.customerId=cus.id_ " +
				"WHERE cus.companyId=142576 AND cus.customerId=1258 AND cus.customerName LIKE '%BSD GmbH%' AND cus.city LIKE '%Paris%' " +
				"AND cus.externalId LIKE '%456753%' AND cus.customerType LIKE '%KEY_ACCOUNT%' AND cus.modifiedDate>='2016-10-14' " +
				"AND cus.modifiedDate<='2016-12-14' AND cus.modifiedByUserId IN (125,36,587) " +
				"GROUP BY loc.customerId " +
				"HAVING COUNT(loc.customerId)=2 " +
				"ORDER BY cus.externalId ASC" +
				") AS subQuery"));
	}
	
}
