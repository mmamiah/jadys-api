package com.gogolo.jadys.sql.functions.impl;

import com.gogolo.jadys.sql.functions.AbstractSqlFunction;
import com.gogolo.jadys.sql.functions.enums.ConversionFunction;
import com.gogolo.jadys.sql.statement.select.SelectArgument;

import static com.gogolo.jadys.sql.functions.enums.ConversionFunction.TO_CHAR;
import static com.gogolo.jadys.sql.functions.enums.ConversionFunction.TO_DATE;
import static com.gogolo.jadys.sql.functions.impl.SqlDateFunctions.addQuote;

public class SqlConvertionFunctions extends AbstractSqlFunction<ConversionFunction> {

    public static SelectArgument toChar(Object arg, String formatMask) {
        return new SqlConvertionFunctions().apply(TO_CHAR, arg, addQuote(formatMask));
    }

    public static SelectArgument toDate(Object arg, String formatMask) {
        return new SqlConvertionFunctions().apply(TO_DATE, addQuote(String.valueOf(arg)), addQuote(formatMask));
    }

}
