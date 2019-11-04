package com.gogolo.jadys.sql.clause;

import org.apache.commons.lang3.StringUtils;

public class GroupBy extends Having {

    private static final String HAVING = "HAVING";

    protected GroupBy(){
        super();
    }

    public GroupBy(String value){
        super(value);
    }

    public Having having(Object expression){
        setSql(String.join(StringUtils.SPACE, this.toString(), HAVING, String.valueOf(expression)));
        return (Having) this;
    }

}
