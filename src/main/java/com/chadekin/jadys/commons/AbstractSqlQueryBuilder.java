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
package com.chadekin.jadys.commons;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.commons.expression.SqlExpressionCleaning;
import com.chadekin.jadys.commons.expression.impl.SqlExpressionFactory;
import com.chadekin.jadys.commons.resourcekeys.JadysKeys;
import com.chadekin.jadys.model.SqlExpressionItem;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;

/**
 * NativeSqlQueryBuilder
 */
public abstract class AbstractSqlQueryBuilder<T> implements JadysSqlQueryBuilder<T>, ApplySqlExpression<T> {

	private static final String ALIAS_REGEX = "([a-zA-Z0-9]+\\.)";
	private JadysSqlQueryBuilder parent;
	private JadysSqlQueryBuilder child;
	private StringBuilder resultSql;
	private boolean activeStatement;
	private Set<String> alias;
	private SqlExpressionItem expressionItem;
	
	protected AbstractSqlQueryBuilder(JadysSqlQueryBuilder parent){
		this.parent = parent;
		this.activeStatement = true;
		this.alias = Sets.newHashSet();
		this.resultSql = new StringBuilder();
	}
	
	@Override
	public JadysSqlQueryBuilder getParent() {
		return parent;
	}

	@Override
	public JadysSqlQueryBuilder getChild() {
		return child;
	}

	@Override
	public void setChild(JadysSqlQueryBuilder child) {
		this.child = child;
	}

	@Override
	public StringBuilder getResultSql() {
		return resultSql;
	}

	@Override
	public void setResultSql(StringBuilder resultSql) {
		this.resultSql = resultSql;
	}

	@Override
	public void setSqlExpressionItem(SqlExpressionItem expressionItem) {
		this.expressionItem = expressionItem;
	}

	@Override
	public SqlExpressionItem getSqlExpressionItem() {
		return expressionItem;
	}

	@Override
	public void finalizeExpression() {
		apply(SqlExpressionFactory.newExpression(expressionItem).build());
	}

	@Override
	public T append(String... value) {
		return apply(value);
	}

	@Override
	public T apply(String... expression) {
		if(expression!=null && expression.length > 0){
			Stream.of(expression)
					.filter(value -> StringUtils.isNotBlank(value))
					.forEach(value -> {
						if(resultSql.length()!=0){
							resultSql.append(JadysKeys.SPACE);
						}
						resultSql.append(value);
					});
		}
		return (T) this;
	}

	@Override
	public T withAlias(String alias) {
		if(StringUtils.isNotBlank(alias)){
			StringBuilder result = new StringBuilder(alias);
			if(!alias.contains(JadysKeys.DOT)){
				result.append(JadysKeys.DOT);
			}
			getAlias().add(result.toString());
		}
		return (T) this;
	}

	@Override
	public String build() {
		JadysSqlQueryBuilder firstElement = getFirstElement(this);

		return firstElement.buildNext().toString().trim();
	}

	@Override
	public String getValue() {
		if(resultSql == null || !isActiveStatement()) return StringUtils.EMPTY;
		return resultSql.toString();
	}

	@Override
	public Set<String> getAlias() {
		return alias;
	}

	@Override
	public Set<String> extractAlias(String argument){
		Set<String> result = Sets.newHashSet();
		Pattern aliasPattern = Pattern.compile(ALIAS_REGEX);
		Matcher aliasMatcher = aliasPattern.matcher(argument);
		while (aliasMatcher.find()) {
			String aliasMatch = aliasMatcher.group();
			result.add(aliasMatch);
		}
		return result;
	}

	@Override
	public boolean isActiveStatement() {
		return activeStatement;
	}

	@Override
	public void setActiveStatement(boolean activeStatement) {
		this.activeStatement = activeStatement;
	}

	@Override
	public String buildNext() {
		String value = cleanExpression(getValue());
		StringBuilder result = new StringBuilder();
		if(StringUtils.isNotBlank(value)){
			result.append(getType().getName()).append(JadysKeys.SPACE).append(value).append(JadysKeys.SPACE);
		}
		if(getChild()!=null){
			result.append(getChild().buildNext());
		}
		return result.toString().trim();
	}

	private static JadysSqlQueryBuilder getFirstElement(JadysSqlQueryBuilder node){
		JadysSqlQueryBuilder firstElement = node;
		while(firstElement!=null && firstElement.getParent()!=null){
			firstElement = firstElement.getParent();
		}
		return firstElement;
	}

}
