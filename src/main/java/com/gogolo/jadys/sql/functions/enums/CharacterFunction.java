package com.gogolo.jadys.sql.functions.enums;

import com.gogolo.jadys.sql.functions.SqlFunction;

public enum CharacterFunction implements SqlFunction {
    ASCII("ASCII", "ASCII(%s)"),
    CHR("CHR", "CHR(%s)"),
    CONCAT("CONCAT", "CONCAT(%s, %s)"),
    CONCAT_X("CONCAT_X", "%s"),
    INITCAP("INITCAP", "INITCAP(%s)"),
    INSTR("INSTR", "INSTR(%s, %s)"),
    INSTR_X("INSTR_X", "INSTR(%s, %s, %s, %s)"),
    LENGTH("LENGTH", "LENGTH(%s)"),
    LOWER("LOWER", "LOWER(%s)"),
    REPLACE("REPLACE", "REPLACE(%s, %s)"),
    SUBSTR("SUBSTR", "SUBSTR(%s, %s)"),
    SUBSTR_X("SUBSTR_X", "SUBSTR(%s, %s, %s)"),
    TRANSLATE("TRANSLATE", "TRANSLATE(%s, %s, %s)"),
    TRIM("TRIM", "TRIM(%s)"),
    UPPER("UPPER", "UPPER(%s)");

    private String code;
    private String format;

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
