package com.gogolo.jadys.sql.functions;

import com.gogolo.jadys.sql.statement.select.SelectArgument;

public abstract class AbstractSqlFunction<T extends SelectArgument> implements SelectArgument {

    private String expression;

    protected AbstractSqlFunction<T> apply(SqlFunction sqlFunction){
        this.expression = sqlFunction.getCode();
        return this;
    }

    protected AbstractSqlFunction<T> apply(SqlFunction sqlFunction, String arg){
        this.expression = sqlFunction.format(arg);
        return this;
    }

    protected AbstractSqlFunction<T> apply(SqlFunction sqlFunction, String arg1, String arg2){
        this.expression = sqlFunction.format(arg1, arg2);
        return this;
    }

    protected AbstractSqlFunction<T> apply(SqlFunction sqlFunction, String... args){
        this.expression = sqlFunction.format(args);
        return this;
    }

    @Override
    public String getValue() {
        return expression;
    }

}
