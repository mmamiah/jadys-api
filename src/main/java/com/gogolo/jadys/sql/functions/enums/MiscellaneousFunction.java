package com.gogolo.jadys.sql.functions.enums;

import com.gogolo.jadys.sql.functions.SqlFunction;

public enum MiscellaneousFunction implements SqlFunction {

    GREATEST("GREATEST", "GREATEST(%s)"),
    LEAST("LEAST", "LEAST(%s)"),
    UID("UID", "UID"),
    USER("USER", "USER");

    private String code;
    private  String format;

    MiscellaneousFunction(String code, String format) {
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
