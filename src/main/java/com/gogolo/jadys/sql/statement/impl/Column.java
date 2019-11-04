package com.gogolo.jadys.sql.statement.impl;

import com.gogolo.jadys.sql.SqlStatement;
import com.gogolo.jadys.sql.As;

public class Column extends SqlStatement {

    public Column(){
        super();
    }

    public Column(String value){
        super(value);
    }

    public static As<Column> as(Column parent){
        return new As<Column>(parent, new Column());
    }

    public static As<Column> column(String column) {
        return as(new Column(column));
    }

}
