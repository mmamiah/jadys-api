package com.gogolo.jadys.sql.select.impl;

import com.gogolo.jadys.sql.select.SelectExpression;
import org.apache.commons.lang3.StringUtils;

public class As implements SelectExpression {

    private String alias;

    As(String alias){
        this.alias = alias;
    }

    public As column(String alias) {
        return new As(alias);
    }

    public SelectExpression as(String alias){
        String temp = StringUtils.isBlank(alias) ? StringUtils.EMPTY : " as " + alias.trim();
        this.alias = this.alias.trim() + temp;
        return this;
    }

    @Override
    public String getValue() {
        return alias;
    }
}
