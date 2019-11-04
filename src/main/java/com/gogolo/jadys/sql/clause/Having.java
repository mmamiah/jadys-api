package com.gogolo.jadys.sql.clause;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Having extends Fetch {

    private static final String ORDER_BY = "ORDER BY";

    protected Having() {
        super();
    }

    public Having(String value) {
        super(value);
    }

    public OrderByClause orderBy(String... args){
        List<String> expression = new ArrayList<>();
        expression.add(this.toString());
        expression.add(ORDER_BY);
        expression.addAll(Arrays.asList(args));
        setSql(String.join(StringUtils.SPACE, expression));
        return new OrderByClause(this);
    }

}
