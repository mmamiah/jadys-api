package com.chadekin.jadys.sqlfunction;

import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.commons.ApplySqlExpression;
import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.commons.expression.SqlExpressionHandler;
import com.chadekin.jadys.commons.resourcekeys.JadysExceptionMessageKeys;
import com.chadekin.jadys.commons.resourcekeys.JadysKeys;
import com.chadekin.jadys.syntax.select.SelectClauseBuilder;
import com.chadekin.jadys.validation.JadysArgumentValidator;

/**
 * SqlFunction
 */
public interface SqlFunction {

	public default void executeFunction(String column_name, SqlLexical lexical){
		JadysArgumentValidator.newValidator()
				.nullValue(JadysExceptionMessageKeys.INVALID_COLUMN_NAME)
				.sqlInjection()
				.validate(column_name);
		boolean isSelectBuilder = this instanceof SelectClauseBuilder;
		boolean isSelectAll = isSelectBuilder && this instanceof JadysSqlQueryBuilder;
		if(isSelectAll && ((JadysSqlQueryBuilder)this).getResultSql().toString().equals(JadysKeys.STAR)){
			((JadysSqlQueryBuilder)this).getResultSql().setLength(0);
		}
		if(this instanceof SqlExpressionHandler){
			StringBuilder value = new StringBuilder(lexical.getName()).append(JadysKeys.OPEN_PARENTHESIS).append(column_name).append(JadysKeys.CLOSE_PARENTHESIS);
			SqlExpressionHandler handler = (SqlExpressionHandler) this;
			handler.addArgument(value.toString());
		}
	}

	public default void executeFunction(int arg, SqlLexical lexical){
		boolean isSelectBuilder = this instanceof SelectClauseBuilder;
		boolean isSelectAll = isSelectBuilder && this instanceof JadysSqlQueryBuilder;
		if(isSelectAll && ((JadysSqlQueryBuilder)this).getResultSql().toString().equals(JadysKeys.STAR)){
			((JadysSqlQueryBuilder)this).getResultSql().setLength(0);
		}
		if(this instanceof ApplySqlExpression){
			ApplySqlExpression builder = (ApplySqlExpression) this;
			builder.apply(lexical.getName(), String.valueOf(arg));
		}
	}
	
}
