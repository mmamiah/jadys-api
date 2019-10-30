package com.gogolo.jadys.sql.from.impl;

import com.gogolo.jadys.sql.SqlStatement;
import com.gogolo.jadys.sql.from.FromExpression;
import com.gogolo.jadys.sql.where.WhereStatement;
import org.apache.commons.lang3.StringUtils;

public class As extends SqlStatement {

    private String expression;

    As(String expression){
        this.expression = expression;
    }

    public As column(String expression) {
        return new As(expression);
    }

    @Override
    public String toString() {
        return expression;
    }

    public WhereStatement as(String alias){
        String temp = StringUtils.isBlank(alias) ? StringUtils.EMPTY : " as " + alias.trim();
        this.expression = this.expression.trim() + temp;
        return WhereStatement.newStatement(this.expression);
    }
}
