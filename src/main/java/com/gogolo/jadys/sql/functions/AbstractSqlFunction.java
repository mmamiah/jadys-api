package com.gogolo.jadys.sql.functions;

import com.gogolo.jadys.sql.SqlStatement;
import org.apache.commons.lang3.ArrayUtils;

public abstract class AbstractSqlFunction<T extends SqlFunction> extends SqlStatement {

    protected static final String COMMA_SPACE = ", ";

    protected SqlStatement apply(T sqlFunction){
        setSql(sqlFunction.getCode());
        return this;
    }

    protected SqlStatement apply(T sqlFunction, Object arg){
        setSql(sqlFunction.format(String.valueOf(arg)));
        return this;
    }

    protected SqlStatement apply(T sqlFunction, Object arg1, Object arg2){
        setSql(sqlFunction.format(String.valueOf(arg1), String.valueOf(arg2)));
        return this;
    }

    protected SqlStatement apply(T sqlFunction, Object... args){
        setSql(sqlFunction.format(ArrayUtils.toStringArray(args)));
        return this;
    }

}
