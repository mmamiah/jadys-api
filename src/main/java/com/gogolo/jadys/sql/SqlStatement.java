package com.gogolo.jadys.sql;

import org.apache.commons.lang3.StringUtils;

import java.util.stream.IntStream;

public abstract class SqlStatement implements CharSequence {

    private String sql = StringUtils.EMPTY;

    protected SqlStatement(){
        this.sql = StringUtils.EMPTY;
    }

    public SqlStatement(String column) {
        this.sql = column;
    }

    public void setSql(String sql) {
        this.sql = StringUtils.defaultIfBlank(sql, StringUtils.EMPTY).trim();
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return this.sql.subSequence(start, end);
    }

    @Override
    public IntStream chars() {
        return this.sql.chars();
    }

    @Override
    public char charAt(int index) {
        return this.sql.charAt(index);
    }

    @Override
    public IntStream codePoints() {
        return this.sql.codePoints();
    }

    @Override
    public int length() {
        return StringUtils.defaultIfBlank(sql, StringUtils.EMPTY).length();
    }

    public String toString(){
        return sql;
    }

}
