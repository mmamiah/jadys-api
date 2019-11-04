package com.gogolo.jadys.sql.functions.impl;

import com.gogolo.jadys.sql.SqlStatement;
import com.gogolo.jadys.sql.functions.enums.SqlDateField;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gogolo.jadys.sql.functions.impl.SqlDateFunctions.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class SqlDateFunctionsTest {

    @Test
    void shouldGenerateExtractSqlStatement(){
        // Arrange
        SqlDateField year = SqlDateField.YEAR;
        String field = "birth";

        // Act
        SqlStatement sql = extract(year, field);

        // Assert
        assertThat(String.valueOf(sql), equalTo("EXTRACT(YEAR FROM DATE birth)"));
    }

    @Test
    void shouldGenerateNextDaySqlStatement(){
        // Arrange
        String field = "birth";

        // Act
        SqlStatement sql = nextDay(field);

        // Assert
        assertThat(String.valueOf(sql), equalTo("NEXT_DAY(birth)"));
    }

    @Test
    void shouldGenerateLastDaySqlStatement(){
        // Arrange
        String field = "birth";

        // Act
        SqlStatement sql = lastDay(field);

        // Assert
        assertThat(String.valueOf(sql), equalTo("LAST_DAY(birth)"));
    }

    @Test
    void shouldGenerateAddMonthSqlStatement(){
        // Arrange
        String field = "birth";
        int numberOfMonths = 2;

        // Act
        SqlStatement sql = addMonths(field, numberOfMonths);

        // Assert
        assertThat(String.valueOf(sql), equalTo("ADD_MONTHS(birth, 2)"));
    }

}