package com.gogolo.jadys.sql.functions.enums;

import com.gogolo.jadys.sql.functions.SqlFunction;

public enum AggregateFunction implements SqlFunction {

    SUM("SUM", "SUM(%s)"),
    AVG("AVG", "AVG(%s)"),
    MAX("MAX", "MAX(%s)"),
    MIN("MIN", "MIN(%s)"),
    COUNT("COUNT", "COUNT(%s)"),
    MEDIAN("MEDIAN", "MEDIAN(%s)");

    private String code;
    private  String format;

    AggregateFunction(String code, String format) {
        this.code = code;
        this.format = format;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getFormat() {
        return format;
    }

}
