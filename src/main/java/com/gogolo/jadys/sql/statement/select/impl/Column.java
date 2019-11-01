package com.gogolo.jadys.sql.statement.select.impl;

import com.gogolo.jadys.sql.statement.select.SelectArgument;

public class Column extends SelectArgument {

    private String column;

    private Column(String column){
        this.column = column;
    }

    public static Column newField(String column){
        return new Column(column);
    }

    public static As column(String column) {
        return new As(column);
    }

    @Override
    public String getValue() {
        return column;
    }
}
