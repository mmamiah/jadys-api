package com.gogolo.jadys.sql.clause;

import org.apache.commons.lang3.StringUtils;

public class Join extends Where {

    private static final String JOIN = "JOIN";
    private static final String WHERE = "WHERE";

    public Join(As parent) {
        super(parent.toString());
    }

    public Join(String value) {
        super(value);
    }

    public On join(String table, String alias){
        setSql(String.join(StringUtils.SPACE, this.toString(), JOIN, table, alias));
        return new On(this);
    }

    public Where where(Object expression){
        setSql(String.join(StringUtils.SPACE, this.toString(), WHERE, String.valueOf(expression)));
        return (Where) this;
    }

}
