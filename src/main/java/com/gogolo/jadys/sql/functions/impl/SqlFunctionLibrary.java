package com.gogolo.jadys.sql.functions.impl;

import com.gogolo.jadys.sql.functions.SqlFunction;
import com.gogolo.jadys.sql.statement.select.SelectArgument;

import static com.gogolo.jadys.sql.functions.enums.AggregateFunction.*;
import static com.gogolo.jadys.sql.functions.enums.CharacterFunction.*;
import static com.gogolo.jadys.sql.functions.enums.DateFunction.*;
import static com.gogolo.jadys.sql.functions.enums.MathFunction.*;
import static com.gogolo.jadys.sql.functions.enums.MiscellaneousFunction.*;

public class SqlFunctionLibrary implements SelectArgument {

    private String expression;

    private SqlFunctionLibrary(SqlFunction sqlFunction){
        this.expression = sqlFunction.getCode();
    }

    private SqlFunctionLibrary(SqlFunction sqlFunction, String arg){
        this.expression = sqlFunction.format(arg);
    }

    private SqlFunctionLibrary(SqlFunction sqlFunction, String arg1, String arg2){
        this.expression = sqlFunction.format(arg1, arg2);
    }

    @Override
    public String getValue() {
        return expression;
    }

    public static SelectArgument sum(String arg) {
        return new SqlFunctionLibrary(SUM, arg);
    }

    public static SelectArgument avg(String arg) {
        return new SqlFunctionLibrary(AVG, arg);
    }

    public static SelectArgument max(String arg) {
        return new SqlFunctionLibrary(MAX, arg);
    }

    public static SelectArgument min(String arg) {
        return new SqlFunctionLibrary(MIN, arg);
    }

    public static SelectArgument count(String arg) {
        return new SqlFunctionLibrary(COUNT, arg);
    }

    public static SelectArgument median(String arg) {
        return new SqlFunctionLibrary(MEDIAN, arg);
    }

    public static SelectArgument lower(String arg) {
        return new SqlFunctionLibrary(LOWER, arg);
    }

    public static SelectArgument upper(String arg) {
        return new SqlFunctionLibrary(UPPER, arg);
    }

    public static SelectArgument initcap(String arg) {
        return new SqlFunctionLibrary(INITCAP, arg);
    }

    public static SelectArgument substr(String arg) {
        return new SqlFunctionLibrary(SUBSTR, arg);
    }

    public static SelectArgument instr(String arg) {
        return new SqlFunctionLibrary(INSTR, arg);
    }

    public static SelectArgument length(String arg) {
        return new SqlFunctionLibrary(LENGTH, arg);
    }

    public static SelectArgument translate(String arg) {
        return new SqlFunctionLibrary(TRANSLATE, arg);
    }

    public static SelectArgument trim(String arg) {
        return new SqlFunctionLibrary(TRIM, arg);
    }

    public static SelectArgument extract(String arg1, String arg2) {
        return new SqlFunctionLibrary(EXTRACT, arg1, arg2);
    }

    public static SelectArgument concat(String arg1, String arg2) {
        return new SqlFunctionLibrary(CONCAT, arg1, arg2);
    }

    public static SelectArgument nvl(String arg1, String arg2) {
        return new SqlFunctionLibrary(NVL, arg1, arg2);
    }

    public static SelectArgument replace(String originalValue, String toBeReplaced) {
        return new SqlFunctionLibrary(REPLACE, originalValue, toBeReplaced);
    }

    public static SelectArgument replace(String originalValue, String toBeReplaced, String newValue) {
        String lastArgument = (newValue == null) ? toBeReplaced : String.join(", ", toBeReplaced, newValue);
        return new SqlFunctionLibrary(REPLACE, originalValue, lastArgument);
    }

    public static SelectArgument toChar(String arg1, String arg2) {
        return new SqlFunctionLibrary(TO_CHAR, arg1, arg2);
    }

    public static SelectArgument addMonths(String arg1, String arg2) {
        return new SqlFunctionLibrary(ADD_MONTHS, arg1, arg2);
    }

    public static SelectArgument lastDay(String arg) {
        return new SqlFunctionLibrary(LAST_DAY, arg);
    }

    public static SelectArgument nextDay(String arg) {
        return new SqlFunctionLibrary(NEXT_DAY, arg);
    }

    public static SelectArgument abs(String arg) {
        return new SqlFunctionLibrary(ABS, arg);
    }

    public static SelectArgument ceil(String arg) {
        return new SqlFunctionLibrary(CEIL, arg);
    }

    public static SelectArgument floor(String arg) {
        return new SqlFunctionLibrary(FLOOR, arg);
    }

    public static SelectArgument mod(String arg1, String arg2) {
        return new SqlFunctionLibrary(MOD, arg1, arg2);
    }

    public static SelectArgument greatest(String arg) {
        return new SqlFunctionLibrary(GREATEST, arg);
    }

    public static SelectArgument least(String arg) {
        return new SqlFunctionLibrary(LEAST, arg);
    }

    public static SelectArgument uid() {
        return new SqlFunctionLibrary(UID);
    }

    public static SelectArgument user() {
        return new SqlFunctionLibrary(USER);
    }

}
