package com.gogolo.jadys.sql;

import com.gogolo.jadys.sql.SqlStatement;
import com.gogolo.jadys.sql.clause.From;
import org.apache.commons.lang3.StringUtils;

public class As<T extends SqlStatement> extends SqlStatement {

    private static final String AS = "as";

    private SqlStatement parent;
    private T nextStep;

    public As(SqlStatement parent, T nextStep){
        this.parent = parent;
        this.nextStep = nextStep;
    }

    public As(From from) {

    }

    public T as(String alias){
        this.nextStep.setSql(String.join(StringUtils.SPACE, parent.toString(), AS, alias));
        return this.nextStep;
    }

    @Override
    public String toString() {
        return parent.toString();
    }

    @Override
    public void setSql(String sql) {
        parent.setSql(sql);
    }

}
