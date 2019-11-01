package com.gogolo.jadys.commons;

import com.gogolo.jadys.enums.DatabaseVersion;
import com.gogolo.jadys.sql.functions.SqlFunction;

import java.util.*;

import static com.gogolo.jadys.enums.DatabaseVersion.*;
import static com.gogolo.jadys.sql.functions.enums.AdvancedFunction.*;
import static com.gogolo.jadys.sql.functions.enums.AggregateFunction.*;
import static com.gogolo.jadys.sql.functions.enums.CharacterFunction.*;
import static com.gogolo.jadys.sql.functions.enums.DateFunction.*;
import static com.gogolo.jadys.sql.functions.enums.MathFunction.*;

public class DBCompatibility {

    private Map<DatabaseVersion, List<SqlFunction>> compatibility;

    private DBCompatibility(){
        this.compatibility = new HashMap<>();
        initOracle8i();
        initOracle9i();
        initOracle10g();
        initOracle11g();
        initOracle12c();
    }

    public DBCompatibility newInstance(){
        return new DBCompatibility();
    }

    private void initOracle8i(){
        this.compatibility.put(ORACLE_8_I, new ArrayList<>());
        this.compatibility.get(ORACLE_8_I).addAll(Arrays.asList(ASCII, CHR, CONCAT, CONCAT_X, INITCAP, INSTR, INSTR_X,
                LENGTH, LOWER, REPLACE, SUBSTR, SUBSTR_X, TRANSLATE, TRIM, UPPER, UID, USER, NVL, AVG, COUNT, MAX, MIN,
                SUM, ADD_MONTHS, LAST_DAY, NEXT_DAY, EXTRACT, ABS, CEIL, FLOOR, MOD, GREATEST, LEAST));
    }

    private void initOracle9i(){
        this.compatibility.put(ORACLE_9_I, new ArrayList<>());
        this.compatibility.get(ORACLE_9_I).addAll(Arrays.asList(ASCII, CHR, CONCAT, CONCAT_X, INITCAP, INSTR, INSTR_X,
                LENGTH, LOWER, REPLACE, SUBSTR, SUBSTR_X, TRANSLATE, TRIM, UPPER, UID, USER, NVL, AVG, COUNT, MAX, MIN,
                SUM, ADD_MONTHS, LAST_DAY, NEXT_DAY, EXTRACT, ABS, CEIL, FLOOR, MOD, GREATEST, LEAST));
    }

    private void initOracle10g(){
        this.compatibility.put(ORACLE_10_G, new ArrayList<>());
        this.compatibility.get(ORACLE_10_G).addAll(Arrays.asList(ASCII, CHR, CONCAT, CONCAT_X, INITCAP, INSTR, INSTR_X,
                LENGTH, LOWER, REPLACE, SUBSTR, SUBSTR_X, TRANSLATE, TRIM, UPPER, UID, USER, NVL, AVG, COUNT, MAX, MIN,
                SUM, ADD_MONTHS, LAST_DAY, NEXT_DAY, EXTRACT, ABS, CEIL, FLOOR, MOD, GREATEST, LEAST));
    }

    private void initOracle11g(){
        this.compatibility.put(ORACLE_11_G, new ArrayList<>());
        this.compatibility.get(ORACLE_11_G).addAll(Arrays.asList(ASCII, CHR, CONCAT, CONCAT_X, INITCAP, INSTR, INSTR_X,
                LENGTH, LOWER, REPLACE, SUBSTR, SUBSTR_X, TRANSLATE, TRIM, UPPER, UID, USER, NVL, AVG, COUNT, MAX, MIN,
                SUM, ADD_MONTHS, LAST_DAY, NEXT_DAY, EXTRACT, ABS, CEIL, FLOOR, MOD, GREATEST, LEAST));
    }

    private void initOracle12c(){
        this.compatibility.put(ORACLE_12_C, new ArrayList<>());
        this.compatibility.get(ORACLE_12_C).addAll(Arrays.asList(ASCII, CHR, CONCAT, CONCAT_X, INITCAP, INSTR, INSTR_X,
                LENGTH, LOWER, REPLACE, SUBSTR, SUBSTR_X, TRANSLATE, TRIM, UPPER, UID, USER, NVL, AVG, COUNT, MAX, MIN,
                SUM, ADD_MONTHS, LAST_DAY, NEXT_DAY, EXTRACT, ABS, CEIL, FLOOR, MOD, GREATEST, LEAST));
    }
}
