package com.gogolo.jadys.sql.clause.where;

import com.gogolo.jadys.sql.SqlStatement;

public class WhereStatement extends SqlStatement {

    private String expression;

    private WhereStatement(String expression){
        this.expression = expression;
    }

    public static WhereStatement newStatement(String expression) {
        return new WhereStatement(expression);
    }

    public SqlStatement where(String expression){
        return this;
    }

    @Override
    public String toString() {
        return null;
    }
}
