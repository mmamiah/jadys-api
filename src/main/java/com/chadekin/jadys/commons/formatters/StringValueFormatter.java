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

import com.chadekin.jadys.commons.resourcekeys.JadysKeys;

/**
 * String value formatter
 * format String to value to 'value', and other without change.
 */
public class StringValueFormatter implements SqlValueFormatter<Object> {
	
	private String value;
	private DefaultValueFormatter defaultFormatter = new DefaultValueFormatter();

	@Override
	public Class getTargetClass() {
		return String.class;
	}

	@Override
	public void setValue(Object value) {
		this.value = defaultFormatter.format(value);
	}

	@Override
	public String build() {
		String result = JadysKeys.APOSTROPHE + value + JadysKeys.APOSTROPHE;
		return result;
	}
}
