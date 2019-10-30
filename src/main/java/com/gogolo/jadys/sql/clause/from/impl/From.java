package com.gogolo.jadys.sql.clause.from.impl;

import com.gogolo.jadys.sql.SqlStatement;
import com.gogolo.jadys.sql.statement.select.impl.Select;

public class From extends SqlStatement {

    private static final String FROM_STATEMENT = "%s FROM %S";
    private static final String DUAL = "DUAL";

    private Select select;
    private String tableName;

    private From(Select select) {
        this.select = select;
    }

    @Override
    public String toString() {
        return String.format(FROM_STATEMENT, select.toString().trim(), tableName);
    }

    public static From newStatement(Select select){
        return new From(select);
    }

    public As from(String tableName) {
        this.tableName = tableName;
        return new As(toString());
    }

    public SqlStatement fromDual() {
        this.tableName = DUAL;
        return this;
    }

}
