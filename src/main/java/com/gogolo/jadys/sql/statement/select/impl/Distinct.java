package com.gogolo.jadys.sql.statement.select.impl;

import com.gogolo.jadys.sql.statement.select.SelectArgument;

public class Distinct extends SelectArgument {

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
