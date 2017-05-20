package com.chadekin.jadys.validation;

import java.util.function.Predicate;
import java.util.stream.Stream;
import com.chadekin.jadys.commons.resourcekeys.JadysExceptionMessageKeys;
import org.apache.commons.lang.StringUtils;

/**
 * JadysArgumentValidator
 */
public class JadysArgumentValidator {
	
	private boolean nullValueValidation = false;
	private boolean sqlInjectionValidation = false;
	private String nullValueErrorMessage;
	
	private JadysArgumentValidator(){
	}
	
	public static JadysArgumentValidator newValidator(){
		return new JadysArgumentValidator();
	}

	/**
	 * Null Value validation
	 * @return
	 */
	public JadysArgumentValidator nullValue(){
		nullValueValidation = true;
		return this;
	}

	/**
	 * Null Value validation
	 * @return
	 */
	public JadysArgumentValidator nullValue(String errorMessage){
		nullValueValidation = true;
		nullValueErrorMessage = errorMessage;
		return this;
	}

	/**
	 * SQl Injection validation
	 * @return
	 */
	public JadysArgumentValidator sqlInjection(){
		sqlInjectionValidation = true;
		return this;
	}
	
	public void validate(Object argument){
		if(nullValueValidation && argument==null){
			throwNewIaeForNullValue(nullValueErrorMessage);
		}
		else if(argument == null){
			return;
		}
		
		Predicate<String> sqlInjectionPredicate = SqlInjectionPredicate.isSqlInjection();
		boolean isSqlInjection = Stream.of(argument)
				.filter( arg -> arg instanceof String)
				.anyMatch((Predicate) SqlInjectionPredicate.isSqlInjection());
				
		if(sqlInjectionValidation && isSqlInjection){
			throw new IllegalArgumentException(JadysExceptionMessageKeys.SQL_INJECTION_DETECTED);
		}
	}
	
	private static void throwNewIaeForNullValue(String nullValueErrorMessage){
		String errorMessage;
		if(StringUtils.isNotBlank(nullValueErrorMessage)){
			errorMessage = nullValueErrorMessage;
		}
		else{
			errorMessage = JadysExceptionMessageKeys.INVALID_ARGUMENT;
		}
		throw new IllegalArgumentException(errorMessage);
	}
	
}
