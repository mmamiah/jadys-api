package com.gogolo.jadys.sql.functions;

public interface SqlFunction {

    default String format(String... arg){
        return String.format(getFormat(), arg);
    }

    String getCode();

    String getFormat();
}
