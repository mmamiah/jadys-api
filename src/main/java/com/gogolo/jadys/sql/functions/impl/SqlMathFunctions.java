package com.gogolo.jadys.sql.functions.impl;

import com.gogolo.jadys.sql.functions.AbstractSqlFunction;
import com.gogolo.jadys.sql.statement.select.SelectArgument;

import static com.gogolo.jadys.sql.functions.enums.MathFunction.*;

public class SqlMathFunctions extends AbstractSqlFunction<SqlMathFunctions> {

    public static SelectArgument abs(String arg) {
        return new SqlMathFunctions().apply(ABS, arg);
    }

    public static SelectArgument ceil(String arg) {
        return new SqlMathFunctions().apply(CEIL, arg);
    }

    public static SelectArgument floor(String arg) {
        return new SqlMathFunctions().apply(FLOOR, arg);
    }

    public static SelectArgument mod(String arg1, String arg2) {
        return new SqlMathFunctions().apply(MOD, arg1, arg2);
    }

    public static SelectArgument greatest(String... args) {
        return new SqlMathFunctions().apply(GREATEST, String.join(", ", args));
    }

    public static SelectArgument least(String... args) {
        return new SqlMathFunctions().apply(LEAST, String.join(", ", args));
    }

    public static SelectArgument sum(String arg) {
        return new SqlMathFunctions().apply(SUM, arg);
    }

    public static SelectArgument avg(String arg) {
        return new SqlMathFunctions().apply(AVG, arg);
    }

    public static SelectArgument max(String arg) {
        return new SqlMathFunctions().apply(MAX, arg);
    }

    public static SelectArgument min(String arg) {
        return new SqlMathFunctions().apply(MIN, arg);
    }

    public static SelectArgument count(String arg) {
        return new SqlMathFunctions().apply(COUNT, arg);
    }

    public static SelectArgument median(String arg) {
        return new SqlMathFunctions().apply(MEDIAN, arg);
    }

}
