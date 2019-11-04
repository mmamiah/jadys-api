package com.gogolo.jadys.sql.clause;

import com.gogolo.jadys.sql.SqlStatement;
import org.apache.commons.lang3.StringUtils;

public class On extends SqlStatement {

    private static final String ON = "ON";

    private Join parent;

    public On() {
        super();
    }

    public On(Join parent) {
        super();
        this.parent = parent;
    }

    public Join on(String expression){
        String expr = String.join(StringUtils.SPACE, this.parent.toString(), ON, expression);
        this.parent.setSql(expr);
        return (Join) this.parent;
    }

}
