package com.gogolo.jadys.sql.functions.impl;

import com.gogolo.jadys.sql.SqlStatement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gogolo.jadys.sql.functions.impl.SqlMathFunctions.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class SqlMathFunctionsTest {

    @Test
    void shouldGenerateAbsSqlStatement(){
        // Arrange
        String value = "-5";

        // Act
        SqlStatement sql = abs(value);

        // Assert
        assertThat(String.valueOf(sql), equalTo("ABS(-5)"));
    }

    @Test
    void shouldGenerateCeilSqlStatement(){
        // Arrange
        String value = "102.43";

        // Act
        SqlStatement sql = ceil(value);

        // Assert
        assertThat(String.valueOf(sql), equalTo("CEIL(102.43)"));
    }

    @Test
    void shouldGenerateFloorSqlStatement(){
        // Arrange
        String value = "102.43";

        // Act
        SqlStatement sql = floor(value);

        // Assert
        assertThat(String.valueOf(sql), equalTo("FLOOR(102.43)"));
    }

    @Test
    void shouldGenerateModuloSqlStatement(){
        // Arrange
        String arg1 = "11";
        String arg2 = "3";

        // Act
        SqlStatement sql = mod(arg1, arg2);

        // Assert
        assertThat(String.valueOf(sql), equalTo("MOD(11, 3)"));
    }

    @Test
    void shouldGenerateGreatestSqlStatementWhenSingleArg(){
        // Arrange
        String arg = "11";

        // Act
        SqlStatement sql = greatest(arg);

        // Assert
        assertThat(String.valueOf(sql), equalTo("GREATEST(11)"));
    }

    @Test
    void shouldGenerateGreatestSqlStatementWhenMultipleArgs(){
        // Arrange
        String arg1 = "11";
        String arg2 = "3";
        String arg3 = "5";

        // Act
        SqlStatement sql = greatest(arg1, arg2, arg3);

        // Assert
        assertThat(String.valueOf(sql), equalTo("GREATEST(11, 3, 5)"));
    }

    @Test
    void shouldGenerateLeastSqlStatementWhenSingleArg(){
        // Arrange
        String arg = "11";

        // Act
        SqlStatement sql = least(arg);

        // Assert
        assertThat(String.valueOf(sql), equalTo("LEAST(11)"));
    }

    @Test
    void shouldGenerateLeastSqlStatementWhenMultipleArgs(){
        // Arrange
        String arg1 = "11";
        String arg2 = "3";
        String arg3 = "5";

        // Act
        SqlStatement sql = least(arg1, arg2, arg3);

        // Assert
        assertThat(String.valueOf(sql), equalTo("LEAST(11, 3, 5)"));
    }

    @Test
    void shouldGenerateComboMathFunctionSql(){
        // Arrange
        String arg1 = "11";
        String arg2 = "3";
        String arg3 = "5";

        // Act
        SqlStatement sql = floor(least(greatest(arg1, arg2), mod(arg1, arg3), arg3));

        // Assert
        assertThat(String.valueOf(sql), equalTo("FLOOR(LEAST(GREATEST(11, 3), MOD(11, 5), 5))"));
    }

}