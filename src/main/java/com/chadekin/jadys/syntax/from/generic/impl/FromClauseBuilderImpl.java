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
package com.chadekin.jadys.syntax.from.generic.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.commons.AbstractSqlQueryBuilder;
import com.chadekin.jadys.commons.enums.SqlCrudSyntax;
import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.commons.resourcekeys.JadysKeys;
import com.chadekin.jadys.model.SqlExpressionItem;
import com.chadekin.jadys.syntax.AliasStatement;
import com.chadekin.jadys.syntax.from.generic.FromClauseExtendedBuilder;
import com.chadekin.jadys.syntax.select.generic.SelectClauseBuilder;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;

/**
 * FromClauseBuilder
 */
public class FromClauseBuilderImpl<X extends FromClauseExtendedBuilder<X,W,O>, W, O> extends AbstractSqlQueryBuilder<X> implements FromClauseExtendedBuilder<X, W, O>, AliasStatement<X> {

	private static final Set<SqlLexical> JOIN_STATEMENTS = Sets.newHashSet(SqlLexical.JOIN, SqlLexical.FULL_JOIN, SqlLexical.LEFT_JOIN, SqlLexical.RIGHT_JOIN);
	private static final Set<SqlLexical> ON_STATEMENTS = Sets.newHashSet(SqlLexical.ON);
	private static final Set<SqlLexical> HEADER_STATEMENTS = Sets.union(ON_STATEMENTS, Sets.newHashSet(SqlLexical.SELECT));
	private static final Set<SqlLexical> FILTER_STATEMENTS = Sets.newHashSet(SqlLexical.WHERE, SqlLexical.GROUP_BY, SqlLexical.HAVING, SqlLexical.ORDER_BY);
	private static final Set<SqlLexical> FOOTER_STATEMENTS = Sets.union(ON_STATEMENTS, FILTER_STATEMENTS);
	
	// Map<Alias, JoinStatement>
	private Map<String, String> joinStatements = Maps.newLinkedHashMap();
	private Map.Entry<String, String> joinStatementTemp = null;
	
	protected FromClauseBuilderImpl(JadysSqlQueryBuilder parent, String table_name){
		super(parent);
		if(StringUtils.isBlank(table_name)){
			throw new IllegalArgumentException("Table name cannot be empty");
		}
		initBuilderValue(table_name);
	}

	public static FromClauseBuilderImpl newFromStatement(JadysSqlQueryBuilder parent, String table_name){
		return new FromClauseBuilderImpl(parent, table_name);
	}

	@Override
	public SqlLexical getType() {
		return SqlLexical.FROM;
	}

	@Override
	public void finalizeExpression() {
		String exp = getSqlExpressionItem().toString();
		if(StringUtils.isNotBlank(exp) && exp.contains(SqlLexical.JOIN.getName())){
			int beginIndex = exp.indexOf(SqlLexical.JOIN.getName() + JadysKeys.SPACE);
			int endIndex = exp.indexOf(JadysKeys.SPACE + SqlLexical.ON.getName());
			String alias = Stream.of(exp.substring(beginIndex, endIndex).split(JadysKeys.SPACE))
							.reduce((a, b)-> b)
							.orElse(StringUtils.EMPTY)
							.concat(JadysKeys.DOT);
			withAlias(alias);
			joinStatements.put(alias, exp);
		}
	}

	@Override
	public void applyJoin(SqlLexical sqlLexical, String alias, String fullStatement) {
		if(JOIN_STATEMENTS.contains(sqlLexical)){
			joinStatementTemp = Maps.immutableEntry(alias + JadysKeys.DOT, fullStatement);
		}
		else if(SqlLexical.ON == sqlLexical){
			StringBuilder statement = new StringBuilder(joinStatementTemp.getValue())
					.append(JadysKeys.SPACE)
					.append(SqlLexical.ON)
					.append(JadysKeys.SPACE)
					.append(fullStatement);
			SqlExpressionItem expressionItem = new SqlExpressionItem();
			expressionItem.setParam(statement.toString());
			setSqlExpressionItem(expressionItem);
		}
	}

	@Override
	public String buildNext() {
		// retrieve active aliases
		Set<String> aliases = retrieveBasicActiveAliases();
		
		// check linked aliases and record them
		attachRelatedActiveAliases(aliases);
		
		String[] statements = joinStatements.entrySet()
				.stream()
				.filter(entry -> aliases.contains(entry.getKey()))
				.map(entry -> entry.getValue())
				.toArray(String[]::new);

		this.apply(statements);
		return super.buildNext();
	}
	
	private Set<String> retrieveBasicActiveAliases(){
		boolean isLazy = getParent()!=null && getParent() instanceof SelectClauseBuilder && ((SelectClauseBuilder)getParent()).isLazy();
		Set<String> parentAliases = getParent()==null ? Sets.newHashSet() : getParent().getAlias();
		Set<String> childAliases = Sets.newHashSet();
		JadysSqlQueryBuilder childBuilder = getChild();
		while(childBuilder!=null){
			childAliases.addAll(childBuilder.getAlias());
			childBuilder = childBuilder.getChild();
		}
		return joinStatements.entrySet()
				.stream()
				.filter(entry -> isLazy || parentAliases.contains(entry.getKey()) || childAliases.contains(entry.getKey()))
				.map(entry -> extractAlias(entry.getValue()))
				.flatMap(entry -> entry.stream())
				.filter(val -> joinStatements.keySet().contains(val))
				.collect(Collectors.toSet());
	}

	private void attachRelatedActiveAliases(Set<String> aliases){
		Set<String> foundAliases = Sets.newHashSet(aliases);
		while(!foundAliases.isEmpty()){
			Set<String> result = Sets.difference(findRelatedAlias(foundAliases), aliases);
			foundAliases.clear();
			foundAliases.addAll(result);
			aliases.addAll(result);
		}
	}
	
	private Set<String> findRelatedAlias(Collection<String> aliasToSearch){
		return joinStatements.entrySet()
				.stream()
				.filter(entry -> aliasToSearch.contains(entry.getKey()))
				.map(entry -> extractAlias(entry.getValue()))
				.flatMap(entry -> entry.stream())
				.filter(alias -> joinStatements.keySet().contains(alias) && !aliasToSearch.contains(alias))
				.collect(Collectors.toSet());
	}

	private void initBuilderValue(String table_name){
		StringBuilder value = new StringBuilder();
		if(table_name.contains(SqlCrudSyntax.SELECT.getCode())){
			value.append(JadysKeys.OPEN_PARENTHESIS)
					.append(table_name)
					.append(JadysKeys.CLOSE_PARENTHESIS);
		}
		else{
			value.append(table_name);
		}
		apply(value.toString());
	}

}
