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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Calendar;
import org.hamcrest.text.IsEmptyString;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit test for date formatter
 */
public class DateValueFormatterTest {
	
	private DateValueFormatter formatter;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void init(){
		formatter = new DateValueFormatter();
	}
	
	@Test
	public void shouldFormatNull(){
		// Arrange
		Object value = null;
		
		// Act
		String result = formatter.format(value);
		
		// Assert
		assertThat(result, IsEmptyString.emptyString());
	}

	@Test
	public void shouldFormatDate(){
		// Arrange
		Calendar calendar = Calendar.getInstance();
		calendar.set(2017, Calendar.APRIL, 11);
		Object value = calendar.getTime();

		// Act
		String result = formatter.format(value);

		// Assert
		assertThat(result, is("'2017-04-11'"));
	}
	
	@Test
	public void shouldThrowExceptionWhenWrongType(){
		// Arrange
		Object value = "This";
		
		// Assert
		exception.expect(ClassCastException.class);
		
		// Act
		formatter.format(value);
	}
}
