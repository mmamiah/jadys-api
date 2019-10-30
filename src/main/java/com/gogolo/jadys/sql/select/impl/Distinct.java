package com.gogolo.jadys.sql.select.impl;

import com.gogolo.jadys.sql.select.SelectExpression;

public class Distinct implements SelectExpression {

    private static final String DISTINCT_STATEMENT = "DISTINCT %s";

    private String expression;

    private Distinct(String column){
        this.expression = column;
    }

    @Override
    public String getValue() {
        return expression;
    }

    public static As distinct(String column) {
        return new As(String.format(DISTINCT_STATEMENT, column));
    }

}
