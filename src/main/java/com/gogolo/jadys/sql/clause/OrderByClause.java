package com.gogolo.jadys.sql.clause;

import org.apache.commons.lang3.StringUtils;

public class OrderByClause extends Fetch {

    private static final String ASC = "ASC";
    private static final String DESC = "DESC";

    public OrderByClause(Having parent){
        super(parent.toString());
    }

    public Fetch asc(){
        updateValue(ASC);
        return (Fetch) this;
    }

    public Fetch desc(){
        updateValue(DESC);
        return (Fetch) this;
    }

    private void updateValue(String ascDesc){
        setSql(String.join(StringUtils.SPACE, this.toString(), ascDesc));
    }

}
