package com.gogolo.jadys.sql.functions.enums;

import com.gogolo.jadys.sql.functions.SqlFunction;

public enum AdvancedFunction implements SqlFunction {

    UID("UID", "UID"),
    USER("USER", "USER"),
    NVL("NVL", "NVL(%s, %s)");

    private String code;
    private  String format;

    AdvancedFunction(String code, String format) {
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
