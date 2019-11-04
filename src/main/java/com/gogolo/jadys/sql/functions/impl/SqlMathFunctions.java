package com.gogolo.jadys.sql.functions.impl;

import com.gogolo.jadys.sql.SqlStatement;
import com.gogolo.jadys.sql.functions.AbstractSqlFunction;
import com.gogolo.jadys.sql.functions.enums.MathFunction;

import static com.gogolo.jadys.sql.functions.enums.MathFunction.*;

public class SqlMathFunctions extends AbstractSqlFunction<MathFunction> {

    public static <T extends CharSequence> SqlStatement abs(T arg) {
        return new SqlMathFunctions().apply(ABS, arg);
    }

    public static <T extends CharSequence> SqlStatement ceil(T arg) {
        return new SqlMathFunctions().apply(CEIL, arg);
    }

    public static <T extends CharSequence> SqlStatement floor(T arg) {
        return new SqlMathFunctions().apply(FLOOR, arg);
    }

    public static <T extends CharSequence> SqlStatement mod(T arg1, T arg2) {
        return new SqlMathFunctions().apply(MOD, arg1, arg2);
    }

    public static <T extends CharSequence> SqlStatement greatest(T... args) {
        return new SqlMathFunctions().apply(GREATEST, String.join(COMMA_SPACE, args));
    }

    public static <T extends CharSequence> SqlStatement least(T... args) {
        return new SqlMathFunctions().apply(LEAST, String.join(COMMA_SPACE, args));
    }

}
