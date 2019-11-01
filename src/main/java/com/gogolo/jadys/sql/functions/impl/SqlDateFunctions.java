package com.gogolo.jadys.sql.functions.impl;

import com.gogolo.jadys.sql.functions.AbstractSqlFunction;
import com.gogolo.jadys.sql.statement.select.SelectArgument;

import static com.gogolo.jadys.sql.functions.enums.AdvancedFunction.NVL;
import static com.gogolo.jadys.sql.functions.enums.DateFunction.*;

public class SqlDateFunctions extends AbstractSqlFunction<SqlDateFunctions> {

    public static SelectArgument addMonths(String date, int number_months) {
        return new SqlDateFunctions().apply(ADD_MONTHS, addQuoteToDate(date), String.valueOf(number_months));
    }

    public static SelectArgument lastDay(String date) {
        return new SqlDateFunctions().apply(LAST_DAY, addQuoteToDate(date));
    }

    public static SelectArgument nextDay(String date) {
        return new SqlDateFunctions().apply(NEXT_DAY, addQuoteToDate(date));
    }

    /**
     * @param calendarItem { YEAR | MONTH | DAY | HOUR | MINUTE | SECOND } <br>
     *                     | { TIMEZONE_HOUR | TIMEZONE_MINUTE } <br>
     *                     | { TIMEZONE_REGION | TIMEZONE_ABBR } <br>
     * @param date  { date_value | interval_value }
     * @return The SqlArgument object.
     */
    public static SelectArgument extract(String calendarItem, String date) {
        return new SqlDateFunctions().apply(EXTRACT, calendarItem, addQuoteToDate(date));
    }

    private static String addQuoteToDate(String date){
        return "'" + date + "'";
    }

}
