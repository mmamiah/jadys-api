package com.gogolo.jadys.sql.functions.impl;

import com.gogolo.jadys.sql.SqlStatement;
import com.gogolo.jadys.sql.functions.AbstractSqlFunction;
import com.gogolo.jadys.sql.functions.enums.CharacterFunction;

import static com.gogolo.jadys.sql.functions.enums.CharacterFunction.*;
import static com.gogolo.jadys.sql.functions.impl.SqlDateFunctions.addQuote;

public class SqlCharFunctions extends AbstractSqlFunction<CharacterFunction> {

    public static SqlStatement ascii(String arg) {
        return new SqlCharFunctions().apply(ASCII, arg);
    }

    public static SqlStatement chr(int arg) {
        return new SqlCharFunctions().apply(CHR, arg);
    }

    public static SqlStatement concat(String arg1, String arg2) {
        return new SqlCharFunctions().apply(CONCAT,arg1, arg2);
    }

    public static SqlStatement concat(String... args) {
        return new SqlCharFunctions().apply(CONCAT_X, String.join(" || ", args));
    }

    public static SqlStatement initCap(String arg) {
        return new SqlCharFunctions().apply(INITCAP, arg);
    }

    public static SqlStatement inStr(String original, String substring) {
        return new SqlCharFunctions().apply(INSTR, original, addQuote(substring));
    }

    public static SqlStatement inStr(String arg1, String arg2, int startPosition , int thAppearance ) {
        return new SqlCharFunctions().apply(
                                        INSTR_X,
                                        arg1, addQuote(arg2),
                                        String.valueOf(startPosition), String.valueOf(thAppearance)
        );
    }

    public static SqlStatement length(String arg) {
        return new SqlCharFunctions().apply(LENGTH, arg);
    }

    public static SqlStatement lower(String arg) {
        return new SqlCharFunctions().apply(LOWER, arg);
    }

    public static SqlStatement replace(String field, String toBeReplaced) {
        return new SqlCharFunctions().apply(REPLACE, field, addQuote(toBeReplaced));
    }

    public static SqlStatement replace(String field, String toBeReplaced, String newValue) {
        String lastArgument = (newValue == null) ? toBeReplaced : String.join(COMMA_SPACE, addQuote(toBeReplaced), addQuote(newValue));
        return new SqlCharFunctions().apply(REPLACE, addQuote(field), lastArgument);
    }

    public static SqlStatement subStr(String arg, int startPosition) {
        return new SqlCharFunctions().apply(SUBSTR, addQuote(arg), String.valueOf(startPosition));
    }

    public static SqlStatement subStr(String arg, int startPosition, int length) {
        return new SqlCharFunctions().apply(SUBSTR_X, addQuote(arg), String.valueOf(startPosition), String.valueOf(length));
    }

    public static SqlStatement translate(String arg, String stringToReplace, String replacementString) {
        return new SqlCharFunctions().apply(TRANSLATE, addQuote(arg), addQuote(stringToReplace), addQuote(replacementString));
    }

    public static SqlStatement trim(String arg) {
        return new SqlCharFunctions().apply(TRIM, addQuote(arg));
    }

    public static SqlStatement trim(String trimCharacter, String arg) {
        return new SqlCharFunctions().apply(TRIM, String.join(" FROM ", addQuote(trimCharacter), addQuote(arg)));
    }

    public static SqlStatement trimLeading(String trimCharacter, String arg) {
        return new SqlCharFunctions().apply(TRIM, "LEADING " + addQuote(trimCharacter) + " FROM " + addQuote(arg));
    }

    public static SqlStatement trimTrailing(String trimCharacter, String arg) {
        return new SqlCharFunctions().apply(TRIM, "TRAILING " + addQuote(trimCharacter) + " FROM " + addQuote(arg));
    }

    public static SqlStatement upper(String arg) {
        return new SqlCharFunctions().apply(UPPER, addQuote(arg));
    }

}
