package com.gogolo.jadys.sql.functions.enums;

import com.gogolo.jadys.sql.functions.SqlFunction;

public enum DateFunction implements SqlFunction {

    TO_CHAR("TO_CHAR", "TO_CHAR(%s, %s)"), // Select to_char(sysdate,’DAY’)”Today” FROM DUAL;
    ADD_MONTHS("ADD_MONTHS", "ADD_MONTHS(%s, %s)"),
    LAST_DAY("LAST_DAY", "LAST_DAY(%s)"),
    NEXT_DAY("NEXT_DAY", "NEXT_DAY(%s)");

    private String code;
    private  String format;

    DateFunction(String code, String format) {
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
