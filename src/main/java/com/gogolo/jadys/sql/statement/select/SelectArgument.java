package com.gogolo.jadys.sql.statement.select;

public abstract class SelectArgument {

    public abstract String getValue();

    public String toString(){
        return getValue();
    }

}
