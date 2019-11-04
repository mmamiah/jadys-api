package com.gogolo.jadys.sql.functions.enums;

import com.gogolo.jadys.sql.functions.SqlFunction;

public enum ConversionFunction implements SqlFunction {

    TO_CHAR("TO_CHAR", "to_char(%s)"),
    TO_DATE("TO_DATE", "to_date(%s)");

    private String code;
    private  String format;

    ConversionFunction(String code, String format) {
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
