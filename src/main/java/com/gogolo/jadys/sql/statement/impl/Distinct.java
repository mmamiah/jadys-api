package com.gogolo.jadys.sql.statement.impl;

import com.gogolo.jadys.sql.SqlStatement;
import com.gogolo.jadys.sql.As;

public class Distinct extends SqlStatement {

    private static final String DISTINCT_STATEMENT = "DISTINCT %s";

    private Distinct(String column){
        super(column);
    }

    public static As distinct(String column) {
        return new As<Column>(new Distinct(String.format(DISTINCT_STATEMENT, column)), new Column());
    }

}
