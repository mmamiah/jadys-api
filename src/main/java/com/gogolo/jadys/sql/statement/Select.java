package com.gogolo.jadys.sql.statement;

import com.gogolo.jadys.sql.SqlStatement;
import com.gogolo.jadys.sql.clause.From;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Select {

    private static final String SELECT_STATEMENT = "SELECT %s";
    private static final String START = "*";
    private static final String DELIMITER = ", ";

    private Select(){

    }

    public static From select(String... columns) {
        return new From(String.format(SELECT_STATEMENT, arrayToArguments(columns)));
    }

    public static From select(SqlStatement field) {
        String arg = (field==null) ? START : arrayToArguments(field);
        return new From(String.format(SELECT_STATEMENT, arg));
    }

    public static From select(SqlStatement field1, SqlStatement field2) {
        return new From(String.format(SELECT_STATEMENT, arrayToArguments(field1, field2)));
    }

    public static From select(SqlStatement field1, SqlStatement field2, SqlStatement field3) {
        return new From(String.format(SELECT_STATEMENT, arrayToArguments(field1, field2, field3)));
    }

    public static From select(SqlStatement field1, SqlStatement field2, SqlStatement field3, SqlStatement field4) {
        return new From(String.format(SELECT_STATEMENT, arrayToArguments(field1, field2, field3, field4)));
    }

    public static From select(SqlStatement field1, SqlStatement field2, SqlStatement field3, SqlStatement field4, SqlStatement field5) {
        return new From(String.format(SELECT_STATEMENT, arrayToArguments(field1, field2, field3, field4, field5)));
    }

    private static String arrayToArguments(Object... args){
        if(args==null){
            return START;
        }

        String[] convertedArgs = Arrays.stream(args)
                .map(item -> StringUtils.defaultString((String) item))
                .filter(StringUtils::isNotBlank)
                .toArray(String[]::new);

        String answer;
        if(convertedArgs.length == 0) {
            answer = START;
        }
        else{
            answer = String.join(DELIMITER, convertedArgs);
        }
        return answer;
    }

    private static String arrayToArguments(SqlStatement... args){
        if(args == null){
            return START;
        }
        return Arrays.stream(args).map(arg -> arg.toString().trim()).collect(Collectors.joining(DELIMITER));
    }

}
