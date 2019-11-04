package com.gogolo.jadys.sql.clause;

import com.gogolo.jadys.sql.SqlStatement;
import org.apache.commons.lang3.StringUtils;

public class As extends Where {

    private static final String AS = "AS";
    private static final String WHERE = "WHERE";

    public As(From parent){
        super(parent.toString());
    }

    public Join as(String alias){
        setSql(String.join(StringUtils.SPACE, this.toString(), AS, alias));
        return new Join(this);
    }

    public Where where(Object expression){
        setSql(String.join(StringUtils.SPACE, this.toString(), WHERE, String.valueOf(expression)));
        return (Where) this;
    }

}
