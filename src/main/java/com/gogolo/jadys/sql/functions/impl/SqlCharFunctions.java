package com.gogolo.jadys.sql.functions.impl;

import com.gogolo.jadys.sql.functions.AbstractSqlFunction;
import com.gogolo.jadys.sql.functions.enums.CharacterFunction;
import com.gogolo.jadys.sql.statement.select.SelectArgument;

import static com.gogolo.jadys.sql.functions.enums.CharacterFunction.*;

public class SqlCharFunctions extends AbstractSqlFunction<CharacterFunction> {

    public static SelectArgument ascii(String arg) {
        return new SqlCharFunctions().apply(ASCII, arg);
    }

    public static SelectArgument chr(String arg) {
        return new SqlCharFunctions().apply(CHR, arg);
    }

    public static SelectArgument concat(String arg1, String arg2) {
        return new SqlCharFunctions().apply(CONCAT, arg1, arg2);
    }

    public static SelectArgument concat(String... args) {
        return new SqlCharFunctions().apply(CONCAT_X, String.join(" || ", args));
    }

    public static SelectArgument initCap(String arg) {
        return new SqlCharFunctions().apply(INITCAP, arg);
    }

    public static SelectArgument inStr(String arg1, String arg2) {
        return new SqlCharFunctions().apply(INSTR, arg1, arg2);
    }

    public static SelectArgument inStr(String arg1, String arg2, int startPosition , int thAppearance ) {
        return new SqlCharFunctions().apply(INSTR_X, arg1, arg2, String.valueOf(startPosition), String.valueOf(thAppearance));
    }

    public static SelectArgument length(String arg) {
        return new SqlCharFunctions().apply(LENGTH, arg);
    }

    public static SelectArgument lower(String arg) {
        return new SqlCharFunctions().apply(LOWER, arg);
    }

    public static SelectArgument replace(String originalValue, String toBeReplaced) {
        return new SqlCharFunctions().apply(REPLACE, originalValue, toBeReplaced);
    }

    public static SelectArgument replace(String originalValue, String toBeReplaced, String newValue) {
        String lastArgument = (newValue == null) ? toBeReplaced : String.join(", ", toBeReplaced, newValue);
        return new SqlCharFunctions().apply(REPLACE, originalValue, lastArgument);
    }

    public static SelectArgument subStr(String arg, int startPosition) {
        return new SqlCharFunctions().apply(SUBSTR, arg, String.valueOf(startPosition));
    }

    public static SelectArgument subStr(String arg, int startPosition, int length) {
        return new SqlCharFunctions().apply(SUBSTR_X, arg, String.valueOf(startPosition), String.valueOf(startPosition));
    }

    public static SelectArgument translate(String arg, String stringToReplace, String replacementString) {
        return new SqlCharFunctions().apply(TRANSLATE, arg, stringToReplace, replacementString);
    }

    public static SelectArgument trim(String arg) {
        return new SqlCharFunctions().apply(TRIM, arg);
    }

    public static SelectArgument trim(String trimCharacter, String arg) {
        return new SqlCharFunctions().apply(TRIM, String.join(" FROM ", trimCharacter, arg));
    }

    public static SelectArgument trimLeading(String trimCharacter, String arg) {
        return new SqlCharFunctions().apply(TRIM, "LEADING " + trimCharacter + " FROM " + arg);
    }

    public static SelectArgument trimTrailing(String trimCharacter, String arg) {
        return new SqlCharFunctions().apply(TRIM, "TRAILING " + trimCharacter + " FROM " + arg);
    }

    public static SelectArgument upper(String arg) {
        return new SqlCharFunctions().apply(UPPER, arg);
    }

}
