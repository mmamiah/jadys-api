package com.gogolo.jadys.sql.functions.impl;

import com.gogolo.jadys.sql.SqlStatement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gogolo.jadys.sql.functions.impl.SqlConversionFunctions.toChar;
import static com.gogolo.jadys.sql.functions.impl.SqlConversionFunctions.toDate;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class SqlConversionFunctionsTest {

    @Test
    void shouldGenerateToDateSqlStatementWhenNoFormat(){
        // Arrange
        String dateField = "dateField";

        // Act
        SqlStatement sql = toDate(dateField);

        // Assert
        assertThat(String.valueOf(sql), equalTo("to_date(dateField)"));
    }

    @Test
    void shouldGenerateToDateSqlStatementWhenDefinedFormat(){
        // Arrange
        String date = "dateField";
        String formatMask = "YYYY/MM/DD HH:MI:SS";

        // Act
        SqlStatement sql = toDate(date, formatMask);

        // Assert
        assertThat(String.valueOf(sql), equalTo("to_date(dateField, 'YYYY/MM/DD HH:MI:SS')"));
    }

    @Test
    void shouldGenerateToCharSqlStatementWhenNoFormat(){
        // Arrange
        String amount = "amount";

        // Act
        SqlStatement sql = toChar(amount);

        // Assert
        assertThat(String.valueOf(sql), equalTo("to_char(amount)"));
    }

    @Test
    void shouldGenerateToCharSqlStatementWhenDefinedFormat(){
        // Arrange
        String field = "amount";
        String formatMask = "$9,999.00";

        // Act
        SqlStatement sql = toChar(field, formatMask);

        // Assert
        assertThat(String.valueOf(sql), equalTo("to_char(amount, '$9,999.00')"));
    }

    @Test
    void shouldGenerateComboConversionSqlStatementWhenCharToDate(){
        // Arrange
        String dateField = "dateField";
        String formatMask = "YYYY/MM/DD";

        // Act
        SqlStatement sql = toDate(toChar(dateField, formatMask));

        // Assert
        assertThat(String.valueOf(sql), equalTo("to_date(to_char(dateField, 'YYYY/MM/DD'))"));
    }

    @Test
    void shouldGenerateComboConversionSqlStatementWhenDateToChar(){
        // Arrange
        String dateField = "dateField";
        String formatMask = "dd-mon-yyyy";
        String charFormat = "Day";

        // Act
        SqlStatement sql = toChar(toDate(dateField, formatMask), charFormat);

        // Assert
        assertThat(String.valueOf(sql), equalTo("to_char(to_date(dateField, 'dd-mon-yyyy'), 'Day')"));
    }

}