package com.gogolo.jadys.sql.clause;

import org.apache.commons.lang3.StringUtils;

public class Where extends GroupBy {

    private static final String GROUP_BY = "GROUP BY";

    protected Where(){
        super();
    }

    public Where(String expression) {
        super(expression);
    }

    public GroupBy groupBy(String expression){
        setSql(String.join(StringUtils.SPACE, this.toString(), GROUP_BY, String.valueOf(expression)));
        return (GroupBy) this;
    }

}
