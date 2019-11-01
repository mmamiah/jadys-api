package com.gogolo.jadys.sql.functions.impl;

import com.gogolo.jadys.sql.functions.AbstractSqlFunction;
import com.gogolo.jadys.sql.functions.enums.AggregateFunction;
import com.gogolo.jadys.sql.statement.select.SelectArgument;

import static com.gogolo.jadys.sql.functions.enums.AggregateFunction.*;

public class SqlAggregateFunctions extends AbstractSqlFunction<AggregateFunction> {

    public static SelectArgument sum(String arg) {
        return new SqlAggregateFunctions().apply(SUM, arg);
    }

    public static SelectArgument sumDistinct(String arg) {
        return new SqlAggregateFunctions().apply(SUM.distinct(), arg);
    }

    public static SelectArgument avg(String arg) {
        return new SqlAggregateFunctions().apply(AVG, arg);
    }

    public static SelectArgument avgDistinct(String arg) {
        return new SqlAggregateFunctions().apply(AVG.distinct(), arg);
    }

    public static SelectArgument max(String arg) {
        return new SqlAggregateFunctions().apply(MAX, arg);
    }

    public static SelectArgument maxDistinct(String arg) {
        return new SqlAggregateFunctions().apply(MAX.distinct(), arg);
    }

    public static SelectArgument min(String arg) {
        return new SqlAggregateFunctions().apply(MIN, arg);
    }

    public static SelectArgument minDistinct(String arg) {
        return new SqlAggregateFunctions().apply(MIN.distinct(), arg);
    }

    public static SelectArgument count(String arg) {
        return new SqlAggregateFunctions().apply(COUNT, arg);
    }

    public static SelectArgument countDistinct(String arg) {
        return new SqlAggregateFunctions().apply(COUNT.distinct(), arg);
    }

}
