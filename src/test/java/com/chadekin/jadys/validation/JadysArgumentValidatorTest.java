package com.chadekin.jadys.validation;

import com.chadekin.jadys.commons.resourcekeys.JadysExceptionMessageKeys;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * JadysArgumentValidator Unit Test
 */
public class JadysArgumentValidatorTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void shouldThrowExceptionIfValueIsNull(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.INVALID_ARGUMENT);
		
		// Act
		JadysArgumentValidator.newValidator()
				.nullValue()
				.validate(null);
	}

	@Test
	public void shouldThrowExceptionIfValueIsNullWithCustomErrorMessage(){
		// Arrange
		String customErrorMessage = "Your value is Null";
		
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(customErrorMessage);
		
		// Act
		JadysArgumentValidator.newValidator()
				.nullValue(customErrorMessage)
				.validate(null);
	}

	@Test
	public void shouldThrowExceptionWhenSqlInjection(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.SQL_INJECTION_DETECTED);

		// Act
		JadysArgumentValidator.newValidator()
				.sqlInjection()
				.validate("UPDATE users");
	}

}