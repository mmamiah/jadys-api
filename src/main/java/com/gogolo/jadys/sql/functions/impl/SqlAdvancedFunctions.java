package com.gogolo.jadys.sql.functions.impl;

import com.gogolo.jadys.sql.functions.AbstractSqlFunction;
import com.gogolo.jadys.sql.functions.enums.AdvancedFunction;
import com.gogolo.jadys.sql.statement.select.SelectArgument;

import static com.gogolo.jadys.sql.functions.enums.AdvancedFunction.*;

public class SqlAdvancedFunctions extends AbstractSqlFunction<AdvancedFunction> {

    public static SelectArgument uid() {
        return new SqlAdvancedFunctions().apply(UID);
    }

    public static SelectArgument user() {
        return new SqlAdvancedFunctions().apply(USER);
    }

    public static SelectArgument nvl(String arg1, String replaceWith ) {
        return new SqlAdvancedFunctions().apply(NVL, arg1, replaceWith);
    }

}
