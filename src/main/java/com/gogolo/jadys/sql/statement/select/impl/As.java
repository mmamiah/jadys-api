package com.gogolo.jadys.sql.statement.select.impl;

import com.gogolo.jadys.sql.statement.select.SelectArgument;
import org.apache.commons.lang3.StringUtils;

public class As extends SelectArgument {

    private String alias;

    As(String alias){
        this.alias = alias;
    }

    public As column(String alias) {
        return new As(alias);
    }

    public SelectArgument as(String alias){
        String temp = StringUtils.isBlank(alias) ? StringUtils.EMPTY : " as " + alias.trim();
        this.alias = this.alias.trim() + temp;
        return this;
    }

    @Override
    public String getValue() {
        return alias;
    }
}
