package com.gogolo.jadys.sql.functions.enums;

import com.gogolo.jadys.sql.functions.SqlFunction;

public enum DateFunction implements SqlFunction {

    ADD_MONTHS("ADD_MONTHS", "ADD_MONTHS(%s, %s)"),

    /**
     * The Oracle/PLSQL LAST_DAY function returns the last day of the month based on a date value.
     */
    LAST_DAY("LAST_DAY", "LAST_DAY(%s)"),

    /**
     * The Oracle/PLSQL NEXT_DAY function returns the first weekday that is greater than a date.
     */
    NEXT_DAY("NEXT_DAY", "NEXT_DAY(%s)"),

    /**
     * The Oracle/PLSQL EXTRACT function extracts a value from a date or interval value.
     */
    EXTRACT("EXTRACT", "EXTRACT(%s FROM DATE %s)");

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
