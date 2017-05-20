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
package com.chadekin.jadys.syntax.orderby;

import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.commons.JadysBuilder;
import com.chadekin.jadys.syntax.orderby.impl.OrderByClauseBuilderImpl;
import com.chadekin.jadys.validation.JadysArgumentValidator;

/**
 * OrderByStatement
 */
public interface OrderByStatement<O> extends JadysBuilder<String>  {

	public default O orderBy(String... column_name) {
		JadysArgumentValidator.newValidator()
				.sqlInjection()
				.validate(column_name);
		JadysSqlQueryBuilder builder = null;
		if(this instanceof OrderByClauseBuilder){
			// We reuse the same builder
			builder = ((JadysSqlQueryBuilder)this);
		}
		else if(this instanceof JadysSqlQueryBuilder){
			JadysSqlQueryBuilder actualBuilder = ((JadysSqlQueryBuilder)this);
			builder = OrderByClauseBuilderImpl.newStatement(actualBuilder);
			actualBuilder.setChild(builder);
		}
		if(builder!=null){
			builder.append(column_name);
		}
		return (O) builder;
	}
	
}