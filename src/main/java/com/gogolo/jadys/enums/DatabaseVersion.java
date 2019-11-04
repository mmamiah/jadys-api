package com.gogolo.jadys.enums;

public enum DatabaseVersion {

    ORACLE_8_I("ORACLE_8_I", "Oracle 8i", Rdms.ORACLE),
    ORACLE_9_I("ORACLE_9_I", "Oracle 9i", Rdms.ORACLE),
    ORACLE_10_G("ORACLE_10_G", "Oracle 10g", Rdms.ORACLE),
    ORACLE_11_G("ORACLE_11_G", "Oracle 11g", Rdms.ORACLE),
    ORACLE_12_C("ORACLE_12_C", "Oracle 12c", Rdms.ORACLE);

    private String code;
    private String description;
    private Rdms producer;

    DatabaseVersion(String code, String description, Rdms producer) {
        this.code = code;
        this.description = description;
        this.producer = producer;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Rdms getProducer() {
        return producer;
    }

}
