package com.gogolo.jadys.sql.functions.enums;

import com.gogolo.jadys.sql.functions.SqlFunction;

public enum MathFunction implements SqlFunction {

    ABS("ABS", "ABS(%s)"),
    CEIL("CEIL", "CEIL(%s)"),
    FLOOR("FLOOR", "FLOOR(%s)"),
    MOD("MOD", "MOD(%s, %s)"),
    GREATEST("GREATEST", "GREATEST(%s)"),
    LEAST("LEAST", "LEAST(%s)");

    private String code;
    private  String format;

    MathFunction(String code, String format) {
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
