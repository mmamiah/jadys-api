package com.gogolo.jadys.sql.functions.impl;

import com.gogolo.jadys.sql.SqlStatement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gogolo.jadys.sql.functions.impl.SqlAdvancedFunctions.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class SqlAdvancedFunctionsTest {

    @Test
    void shouldGenerateUidSqlStatement(){
        // Arrange

        // Act
        SqlStatement sql = uid();

        // Assert
        assertThat(String.valueOf(sql), equalTo("UID"));
    }

    @Test
    void shouldGenerateUserSqlStatement(){
        // Arrange

        // Act
        SqlStatement sql = user();

        // Assert
        assertThat(String.valueOf(sql), equalTo("USER"));
    }

    @Test
    void shouldGenerateAsciiSqlStatement(){
        // Arrange
        String arg = "user_city";
        String replaceWith = "n/a";

        // Act
        SqlStatement sql = nvl(arg, replaceWith);

        // Assert
        assertThat(String.valueOf(sql), equalTo("NVL(user_city, 'n/a')"));
    }

}