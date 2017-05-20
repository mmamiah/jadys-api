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
package com.chadekin.jadys.syntax.where.impl;

import java.util.Set;
import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.commons.AbstractSqlQueryBuilder;
import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.commons.expression.impl.SqlExpressionFactory;
import com.chadekin.jadys.commons.resourcekeys.JadysExceptionMessageKeys;
import com.chadekin.jadys.commons.resourcekeys.JadysKeys;
import com.chadekin.jadys.model.SqlExpressionItem;
import com.chadekin.jadys.syntax.where.WhereClauseExtendedBuilder;
import org.apache.commons.collections4.CollectionUtils;

/**
 * WhereClauseExtendedBuilder
 */
public class WhereClauseExtendedBuilderImpl<G, H, O> extends AbstractSqlQueryBuilder<WhereClauseExtendedBuilderImpl> implements WhereClauseExtendedBuilder<WhereClauseExtendedBuilder, G, H, O> {
	
	protected WhereClauseExtendedBuilderImpl(JadysSqlQueryBuilder parent){
		super(parent);
	}

	public static WhereClauseExtendedBuilderImpl newStatement(JadysSqlQueryBuilder parent){
		return new WhereClauseExtendedBuilderImpl(parent);
	}

	@Override
	public SqlLexical getType() {
		return SqlLexical.WHERE;
	}

	@Override
	public void finalizeExpression() {
		String expression = SqlExpressionFactory.newExpression(getSqlExpressionItem()).build();
		Set<String> extractedAlias = extractAlias(expression);
		boolean isParentWithAlias = getParent()!=null && !getParent().getAlias().isEmpty();
		if((isParentWithAlias && !extractedAlias.isEmpty() && isValidAlias(extractedAlias)) || (!isParentWithAlias && extractedAlias.isEmpty())){
			extractedAlias.stream()
					.forEach(this::withAlias);
			apply(expression);
		}
		else if(!expression.isEmpty()){
			StringBuilder exceptionMessage = new StringBuilder(JadysExceptionMessageKeys.INVALID_ALIAS)
					.append(JadysKeys.COLUMN).append(JadysKeys.SPACE)
					.append(JadysKeys.APOSTROPHE).append(expression).append(JadysKeys.APOSTROPHE);
			throw new IllegalArgumentException(exceptionMessage.toString());
		}
	}
	
	private boolean isValidAlias(Set<String> aliases){
		JadysSqlQueryBuilder builder = getParent();
		while(builder != null){
			Set<String> parentAliases = builder.getAlias();
			if(CollectionUtils.isNotEmpty(parentAliases) && parentAliases.containsAll(aliases)){
				return true;
			}
			builder = builder.getParent();
		}
		return false;
	}

}
