package com.gogolo.jadys.sql.functions.impl;

import com.gogolo.jadys.sql.SqlStatement;
import com.gogolo.jadys.sql.functions.AbstractSqlFunction;
import com.gogolo.jadys.sql.functions.enums.AdvancedFunction;

import static com.gogolo.jadys.sql.functions.enums.AdvancedFunction.*;
import static com.gogolo.jadys.sql.functions.impl.SqlDateFunctions.addQuote;

public class SqlAdvancedFunctions extends AbstractSqlFunction<AdvancedFunction> {

    public static SqlStatement uid() {
        return new SqlAdvancedFunctions().apply(UID);
    }

    public static SqlStatement user() {
        return new SqlAdvancedFunctions().apply(USER);
    }

    public static <T extends CharSequence> SqlStatement nvl(T arg, String replaceWith ) {
        return new SqlAdvancedFunctions().apply(NVL, arg, addQuote(replaceWith));
    }

}
