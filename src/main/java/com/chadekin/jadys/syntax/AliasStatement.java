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

import java.util.StringJoiner;
import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.commons.ApplySqlExpression;
import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.commons.resourcekeys.JadysExceptionMessageKeys;
import com.chadekin.jadys.commons.resourcekeys.JadysKeys;
import com.chadekin.jadys.syntax.from.generic.FromClauseBuilder;
import com.chadekin.jadys.validation.JadysArgumentValidator;

/**
 * AliasStatement
 */
public interface AliasStatement<T> {

	public default T as(String alias){
		JadysArgumentValidator.newValidator()
				.nullValue(JadysExceptionMessageKeys.INVALID_ALIAS)
				.sqlInjection()
				.validate(alias);

		StringBuilder value = new StringBuilder(SqlLexical.AS.getName()).append(JadysKeys.SPACE).append(alias);
		if(this instanceof JadysSqlQueryBuilder){
			((JadysSqlQueryBuilder)this).withAlias(alias);
		}
		if(this instanceof ApplySqlExpression){
			ApplySqlExpression builder = (ApplySqlExpression) this;
			builder.apply(value.toString());
		}
		if(this instanceof FromClauseBuilder && ((JadysSqlQueryBuilder)this).getParent() != null){
			// adding alias to single stars ("*")
			String dotStar = JadysKeys.DOT + JadysKeys.STAR;
			StringJoiner result = new StringJoiner(dotStar);
			JadysSqlQueryBuilder parent = ((JadysSqlQueryBuilder)this).getParent();
			String parentValue = parent.getValue();
			String args[] = parentValue.split("\\.\\*");
			if(args.length>0){
				for(String arg:args){
					result.add(arg.replace(JadysKeys.STAR, alias + dotStar));
				}
			}
			else{
				result.add(parentValue.replace(JadysKeys.STAR, alias + dotStar));
			}
			if(parentValue.endsWith(dotStar)){
				result.add(""); // forcing adding joiner delimiter
			}
			parent.getResultSql().setLength(0);
			// The result should look like "alias.*"
			parent.setResultSql(new StringBuilder(result.toString()));
			
		}
		return (T) this;
	}
	
}
