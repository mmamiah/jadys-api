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
package com.chadekin.jadys.commons.formatters;

import java.util.Map;
import com.chadekin.jadys.commons.JadysBuilder;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

/**
 * SqlValueFormatter
 */
public class SqlValueFormatterFactory implements JadysBuilder<String> {

	protected Object value;
	
	private Map<Class, SqlValueFormatter> formatters = Maps.newHashMap();
	
	private SqlValueFormatterFactory(Object value){
		this.value = value;
		init(formatters);
	}
	
	public static SqlValueFormatterFactory format(Object value){
		return new SqlValueFormatterFactory(value);
	}

	@Override
	public String build() {
		if(value == null) return StringUtils.EMPTY;
		String result = null;
		
		Class type = FormatterTypeFactory.resolveClass(value);
		SqlValueFormatter formatter = formatters.get(type);
		result = formatter.format(value);
		
		return result;
	}

	private static void init(Map<Class, SqlValueFormatter> formatters){
		appendFormatter(formatters, new ArrayValueFormatter());
		appendFormatter(formatters, new CollectionValueFormatter());
		appendFormatter(formatters, new DateValueFormatter());
		appendFormatter(formatters, new DefaultValueFormatter());
		appendFormatter(formatters, new StringValueFormatter());
	}
	
	private static void appendFormatter(Map<Class, SqlValueFormatter> formatters, SqlValueFormatter formatter){
		formatters.put(formatter.getTargetClass(), formatter);
	}
	
}
