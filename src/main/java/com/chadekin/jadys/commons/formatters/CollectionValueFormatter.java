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

import java.util.Collection;
import java.util.Iterator;
import com.chadekin.jadys.commons.resourcekeys.JadysKeys;

/**
 * Formatter for Collection
 * Format a collection of number as (12,23,56) or
 * a collection of String as ('alpha','beta','gamma','theta')
 */
public class CollectionValueFormatter implements SqlValueFormatter<Collection> {
	
	private Collection value;
	
	private SqlValueFormatter stringFormatter = new StringValueFormatter();

	@Override
	public Class getTargetClass() {
		return Collection.class;
	}

	@Override
	public void setValue(Collection value) {
		this.value = value;
	}

	@Override
	public String build() {
		StringBuilder result = new StringBuilder();
		if(!value.isEmpty()){
			result.append(JadysKeys.OPEN_PARENTHESIS);
			Iterator iterator = value.iterator();
			while(iterator.hasNext()){
				Object item = iterator.next();
				result.append(formatItem(item));
				if(iterator.hasNext()){
					result.append(JadysKeys.COMMA);
				}
			}
			result.append(JadysKeys.CLOSE_PARENTHESIS);
		}
		return result.toString();
	}
	
	private static String formatItem(Object item){
		String result = null;
		Class type = FormatterTypeFactory.resolveClass(item);
		if(type == String.class){
			result = new StringValueFormatter().format(item);
		}
		else{
			result = new DefaultValueFormatter().format(item);
		}
		return result;
	}
}
