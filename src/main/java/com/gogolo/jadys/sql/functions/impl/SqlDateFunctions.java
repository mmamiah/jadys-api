package com.gogolo.jadys.sql.functions.impl;

import com.gogolo.jadys.sql.SqlStatement;
import com.gogolo.jadys.sql.functions.AbstractSqlFunction;
import com.gogolo.jadys.sql.functions.enums.DateFunction;
import com.gogolo.jadys.sql.functions.enums.SqlDateField;
import org.springframework.util.StringUtils;

import java.util.Arrays;

import static com.gogolo.jadys.sql.functions.enums.DateFunction.*;

public class SqlDateFunctions extends AbstractSqlFunction<DateFunction> {

    private static final String QUOTE = "'";

    public static SqlStatement addMonths(String dateField, int numberOfMonths) {
        return new SqlDateFunctions().apply(ADD_MONTHS, dateField, String.valueOf(numberOfMonths));
    }

    public static SqlStatement lastDay(String dateField) {
        return new SqlDateFunctions().apply(LAST_DAY, dateField);
    }

    public static SqlStatement nextDay(String dateField) {
        return new SqlDateFunctions().apply(NEXT_DAY, dateField);
    }

    /**
     * @param field The SqlDateField
     * @param dateField  { date_value | interval_value }
     * @return The SqlArgument object.
     */
    public static SqlStatement extract(SqlDateField field, String dateField) {
        return new SqlDateFunctions().apply(EXTRACT, field, dateField);
    }

    public static <T extends CharSequence> String addQuote(T arg){
        if(!(arg instanceof String)){
            return String.valueOf(arg);
        }

        String str = String.valueOf(arg).trim();
        if(!StringUtils.startsWithIgnoreCase(str, QUOTE)){
            str = QUOTE + str;
        }
        if(!StringUtils.endsWithIgnoreCase(str, QUOTE)){
            str = str + QUOTE;
        }
        return str;
    }

    public static <T extends CharSequence> String[] addQuote(T... args){
        return Arrays.stream(args).map(SqlDateFunctions::addQuote).toArray(String[]::new);
    }

}
