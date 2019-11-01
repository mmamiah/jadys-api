package com.gogolo.jadys.sql.functions.impl;

import com.gogolo.jadys.sql.functions.AbstractSqlFunction;
import com.gogolo.jadys.sql.functions.enums.DateFunction;
import com.gogolo.jadys.sql.statement.select.SelectArgument;
import org.springframework.util.StringUtils;

import static com.gogolo.jadys.sql.functions.enums.DateFunction.*;

public class SqlDateFunctions extends AbstractSqlFunction<DateFunction> {

    private static final String QUOTE = "'";

    public static SelectArgument addMonths(String date, int number_months) {
        return new SqlDateFunctions().apply(ADD_MONTHS, addQuote(date), String.valueOf(number_months));
    }

    public static SelectArgument lastDay(String date) {
        return new SqlDateFunctions().apply(LAST_DAY, addQuote(date));
    }

    public static SelectArgument nextDay(String date) {
        return new SqlDateFunctions().apply(NEXT_DAY, addQuote(date));
    }

    /**
     * @param calendarItem { YEAR | MONTH | DAY | HOUR | MINUTE | SECOND } <br>
     *                     | { TIMEZONE_HOUR | TIMEZONE_MINUTE } <br>
     *                     | { TIMEZONE_REGION | TIMEZONE_ABBR } <br>
     * @param date  { date_value | interval_value }
     * @return The SqlArgument object.
     */
    public static SelectArgument extract(String calendarItem, String date) {
        return new SqlDateFunctions().apply(EXTRACT, calendarItem, addQuote(date));
    }

    public static String addQuote(String date){
        String str = date.trim();
        if(!StringUtils.startsWithIgnoreCase(str, QUOTE)){
            str = QUOTE + str;
        }
        if(!StringUtils.endsWithIgnoreCase(str, QUOTE)){
            str = str + QUOTE;
        }
        return str;
    }

}
