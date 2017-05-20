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

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Date;

/**
 * Resolve The formatter type
 */
public final class FormatterTypeFactory {
	
	public static Class resolveClass(Object target){
		Class classType = Object.class;
		if(target instanceof Date){
			classType = Date.class;
		}
		else if(target instanceof Collection){
			classType = Collection.class;
		}
		else if(target.getClass().isArray()){
			classType = Array.class;
		}
		else if(target instanceof String || target instanceof Character){
			classType = String.class;
		}
		return classType;
	}
}
