package com.gogolo.jadys.sql.functions.impl;

import com.gogolo.jadys.sql.SqlStatement;
import com.gogolo.jadys.sql.functions.AbstractSqlFunction;
import com.gogolo.jadys.sql.functions.enums.AggregateFunction;

import static com.gogolo.jadys.sql.functions.enums.AggregateFunction.*;

public class SqlAggregateFunctions extends AbstractSqlFunction<AggregateFunction> {

    private static final String DISTINCT_PATTERN = "DISTINCT %s";

    private static String distinct(String arg){
        return String.format(DISTINCT_PATTERN, arg);
    }

    public static SqlStatement sum(String arg) {
        return new SqlAggregateFunctions().apply(SUM, arg);
    }

    public static SqlStatement sumDistinct(String arg) {
        return new SqlAggregateFunctions().apply(SUM, distinct(arg));
    }

    public static SqlStatement avg(String arg) {
        return new SqlAggregateFunctions().apply(AVG, arg);
    }

    public static SqlStatement avgDistinct(String arg) {
        return new SqlAggregateFunctions().apply(AVG, distinct(arg));
    }

    public static SqlStatement max(String arg) {
        return new SqlAggregateFunctions().apply(MAX, arg);
    }

    public static SqlStatement maxDistinct(String arg) {
        return new SqlAggregateFunctions().apply(MAX, distinct(arg));
    }

    public static SqlStatement min(String arg) {
        return new SqlAggregateFunctions().apply(MIN, arg);
    }

    public static SqlStatement minDistinct(String arg) {
        return new SqlAggregateFunctions().apply(MIN, distinct(arg));
    }

    public static SqlStatement count(String arg) {
        return new SqlAggregateFunctions().apply(COUNT, arg);
    }

    public static SqlStatement countDistinct(String arg) {
        return new SqlAggregateFunctions().apply(COUNT, distinct(arg));
    }

}
