package com.gogolo.jadys.sql.functions;

import com.gogolo.jadys.sql.statement.select.SelectArgument;
import org.apache.commons.lang3.ArrayUtils;

public abstract class AbstractSqlFunction<T extends SqlFunction> extends SelectArgument {

    private String expression;

    protected SelectArgument apply(T sqlFunction){
        this.expression = sqlFunction.getCode();
        return this;
    }

    protected SelectArgument apply(T sqlFunction, Object arg){
        this.expression = sqlFunction.format(String.valueOf(arg));
        return this;
    }

    protected SelectArgument apply(T sqlFunction, Object arg1, Object arg2){
        this.expression = sqlFunction.format(String.valueOf(arg1), String.valueOf(arg2));
        return this;
    }

    protected SelectArgument apply(T sqlFunction, Object... args){
        this.expression = sqlFunction.format(ArrayUtils.toStringArray(args));
        return this;
    }

    @Override
    public String getValue() {
        return expression;
    }

}
