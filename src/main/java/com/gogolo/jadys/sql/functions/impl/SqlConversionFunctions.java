package com.gogolo.jadys.sql.functions.impl;

import com.gogolo.jadys.sql.SqlStatement;
import com.gogolo.jadys.sql.functions.AbstractSqlFunction;
import com.gogolo.jadys.sql.functions.enums.ConversionFunction;

import static com.gogolo.jadys.sql.functions.enums.ConversionFunction.TO_CHAR;
import static com.gogolo.jadys.sql.functions.enums.ConversionFunction.TO_DATE;
import static com.gogolo.jadys.sql.functions.impl.SqlDateFunctions.addQuote;

public class SqlConversionFunctions extends AbstractSqlFunction<ConversionFunction> {

    public static <T extends CharSequence> SqlStatement toChar(T arg) {
        return new SqlConversionFunctions().apply(TO_CHAR, String.valueOf(arg));
    }

    public static <T extends CharSequence> SqlStatement toChar(T arg, String formatMask) {
        return new SqlConversionFunctions()
                .apply(TO_CHAR, String.join(COMMA_SPACE, String.valueOf(arg), addQuote(formatMask)));
    }

    public static <T extends CharSequence> SqlStatement toDate(T arg) {
        return new SqlConversionFunctions().apply(TO_DATE, arg);
    }

    public static <T extends CharSequence> SqlStatement toDate(T arg, String formatMask) {
        return new SqlConversionFunctions()
                .apply(TO_DATE, String.join(COMMA_SPACE, arg, addQuote(formatMask)));
    }

}
