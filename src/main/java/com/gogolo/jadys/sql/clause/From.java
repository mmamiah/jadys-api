package com.gogolo.jadys.sql.clause;

import com.gogolo.jadys.sql.SqlStatement;
import org.apache.commons.lang3.StringUtils;

public class From extends SqlStatement {

    private static final String FROM = "FROM %S";
    private static final String DUAL = "DUAL";

    public From(String value){
        super(value);
    }

    public As from(String tableName) {
        String str = String.format(FROM, tableName);
        setSql(String.join(StringUtils.SPACE, this.toString(), str));
        return new As(this);
    }

    public SqlStatement fromDual() {
        String str = String.format(FROM, DUAL);
        setSql(String.join(StringUtils.SPACE, this.toString(), str));
        return this;
    }

}
