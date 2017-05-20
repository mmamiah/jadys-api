package com.chadekin.jadys.validation;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import com.chadekin.jadys.commons.enums.SqlCrudSyntax;
import com.chadekin.jadys.commons.enums.SqlLexical;
import org.apache.commons.lang.StringUtils;

/**
 * SqlInjectionPredicate
 */
public class SqlInjectionPredicate {

	private static final Set<SqlCrudSyntax> SQL_SYNTAX = EnumSet.allOf(SqlCrudSyntax.class)
			.stream()
			.filter(sqlSyntax -> sqlSyntax!= SqlCrudSyntax.SELECT)
			.collect(Collectors.toSet());
	
	private SqlInjectionPredicate(){
		
	}
	
	public static Predicate<String> isSqlInjection(){
		SqlInjectionPredicate predicate = new SqlInjectionPredicate();
		return predicate.isBannedSyntax().or(predicate.isArgumentTooLong()).or(predicate.isSelectDoS());
	}
	
	private Predicate<String> isBannedSyntax(){
		return argument -> {
			return SQL_SYNTAX.stream()
					.anyMatch(syntax -> {
						String regex = "[\\W]*(" + syntax.getName() + ")[\\W]+";
						Pattern pattern = Pattern.compile(regex);
						Matcher matcher = pattern.matcher(argument.toUpperCase());
						return matcher.find();
					});
		};
	}
	
	private Predicate<String> isArgumentTooLong(){
		return argument -> argument.length() >= 1000;
	}
	
	private Predicate<String> isSelectDoS(){
		return argument -> StringUtils.countMatches(argument.toUpperCase(), SqlLexical.SELECT.getName()) > 3;
	}
	
}
