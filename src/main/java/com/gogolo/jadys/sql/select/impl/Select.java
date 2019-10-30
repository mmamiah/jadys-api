package com.gogolo.jadys.sql.select.impl;

import com.gogolo.jadys.sql.from.impl.From;
import com.gogolo.jadys.sql.select.SelectExpression;
import com.gogolo.jadys.sql.SqlStatement;
import com.sun.deploy.util.ArrayUtil;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Select extends SqlStatement {

    private static final String SELECT_STATEMENT = "SELECT %s";
    private static final String START = "*";
    private static final String DELIMITER = ", ";

    private String select;

    private Select(String select){
        this.select = select;
    }

    @Override
    public String toString() {
        return select;
    }

    public static From select(String... column) {
        return From.newStatement(new Select(String.format(SELECT_STATEMENT, arrayToArguments(column))));
    }

    public static From select(SelectExpression field) {
        String arg = (field==null) ? START : arrayToArguments(field);
        return From.newStatement(new Select(String.format(SELECT_STATEMENT, arg)));
    }

    public static From select(SelectExpression field1, SelectExpression field2) {
        return From.newStatement(new Select(String.format(SELECT_STATEMENT, arrayToArguments(field1, field2))));
    }

    public static From select(SelectExpression field1, SelectExpression field2, SelectExpression field3) {
        return From.newStatement(new Select(String.format(SELECT_STATEMENT, arrayToArguments(field1, field2, field3))));
    }

    public static From select(SelectExpression field1, SelectExpression field2, SelectExpression field3, SelectExpression field4) {
        return From.newStatement(new Select(String.format(SELECT_STATEMENT, arrayToArguments(field1, field2, field3, field4))));
    }

    private static String arrayToArguments(String... args){
        String tempArg = args == null ? null : ArrayUtil.arrayToString(args).trim();
        return StringUtils.isEmpty(tempArg) ? START : String.join(DELIMITER, args);
    }

    private static String arrayToArguments(SelectExpression... args){
        if(args == null){
            return START;
        }
        return Arrays.stream(args).map(arg -> arg.getValue().trim()).collect(Collectors.joining(DELIMITER));
    }

}
