package com.gogolo.jadys.sql.functions.enums;

import com.gogolo.jadys.sql.functions.SqlFunction;

public enum CharacterFunction implements SqlFunction {

    LOWER("LOWER", "LOWER(%s)"),
    UPPER("UPPER", "UPPER(%s)"),
    INITCAP("INITCAP", "INITCAP(%s)"),
    SUBSTR("SUBSTR", "SUBSTR(%s)"),
    INSTR("INSTR", "INSTR(%s)"),
    LENGTH("LENGTH", "LENGTH(%s)"),
    TRANSLATE("TRANSLATE", "TRANSLATE(%s)"),
    TRIM("TRIM", "TRIM(%s)"),
    EXTRACT("EXTRACT", "EXTRACT(%s FROM %s)"),
    CONCAT("CONCAT", "CONCAT(%s, %s)"),
    NVL("NVL", "NVL(%s, %s)"),
    REPLACE("REPLACE", "REPLACE(%s, %s)");

    private String code;
    private  String format;

    CharacterFunction(String code, String format) {
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
