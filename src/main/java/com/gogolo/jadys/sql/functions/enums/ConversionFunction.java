package com.gogolo.jadys.sql.functions.enums;

import com.gogolo.jadys.sql.functions.SqlFunction;

public enum ConversionFunction implements SqlFunction {

    TO_CHAR("TO_CHAR", "TO_CHAR(%s, %s)"); // Select to_char(sysdate,’DAY’)”Today” FROM DUAL;

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
