package com.gogolo.jadys.sql.functions.impl;

import com.gogolo.jadys.sql.functions.AbstractSqlFunction;
import com.gogolo.jadys.sql.functions.enums.MathFunction;
import com.gogolo.jadys.sql.statement.select.SelectArgument;

import static com.gogolo.jadys.sql.functions.enums.MathFunction.*;

public class SqlMathFunctions extends AbstractSqlFunction<MathFunction> {

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

}
