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
package com.chadekin.jadys.commons.expression;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import com.chadekin.jadys.commons.formatters.SqlValueFormatterFactory;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

/**
 * Unit test for SqlValueFormatter
 */
public class SqlValueFormatterFactoryTest {
	
	@Test
	public void shouldNotFormatNullValue(){
		// Arrange
		Object value = null;
		
		// Act
		String result = SqlValueFormatterFactory.format(value)
				.build();
		
		// Assert
		assertThat(result, is(StringUtils.EMPTY));
	}
	
	@Test
	public void shouldFormatDateValue(){
		// Arrange
		Calendar calendar = new GregorianCalendar(2016, Calendar.DECEMBER, 24);
		Object value = calendar.getTime();
		
		// Act
		String result = SqlValueFormatterFactory.format(value)
				.build();
		
		// Assert
		assertThat(result, is("'2016-12-24'"));
	}
	
	@Test
	public void shouldFormatStringValue(){
		// Arrange
		Object value = "Hello World";
		
		// Act
		String result = SqlValueFormatterFactory.format(value)
				.build();
		
		// Assert
		assertThat(result, is("'Hello World'"));
	}
	
	@Test
	public void shouldFormatCharacterValue(){
		// Arrange
		Object value = 'E';
		
		// Act
		String result = SqlValueFormatterFactory.format(value)
				.build();
		
		// Assert
		assertThat(result, is("'E'"));
	}
	
	@Test
	public void shouldFormatCollection(){
		// Arrange
		Object value = Lists.newArrayList("Alpha", "Beta");
		
		// Act
		String result = SqlValueFormatterFactory.format(value)
				.build();
		
		// Assert
		assertThat(result, is("('Alpha','Beta')"));
	}

	@Test
	public void shouldFormatCollectionOfVariousType(){
		// Arrange
		Object value = Lists.newArrayList("Alpha", 2);

		// Act
		String result = SqlValueFormatterFactory.format(value)
				.build();

		// Assert
		assertThat(result, is("('Alpha',2)"));
	}

	@Test
	public void shouldFormatInteger(){
		// Arrange
		Object value = 2;

		// Act
		String result = SqlValueFormatterFactory.format(value)
				.build();

		// Assert
		assertThat(result, is("2"));
	}

	@Test
	public void shouldFormatDottedNumber(){
		// Arrange
		Object value = 9.125;

		// Act
		String result = SqlValueFormatterFactory.format(value)
				.build();

		// Assert
		assertThat(result, is("9.125"));
	}

	@Test
	public void shouldFormatArrayOfObject(){
		// Arrange
		Object value = new String[]{"52", "68"};

		// Act
		String result = SqlValueFormatterFactory.format(value)
				.build();

		// Assert
		assertThat(result, is("('52','68')"));
	}

	@Test
	public void shouldFormatArrayOfPrimitive(){
		// Arrange
		Object value = new long[]{23, 985};

		// Act
		String result = SqlValueFormatterFactory.format(value)
				.build();

		// Assert
		assertThat(result, is("(23,985)"));
	}
	
}
