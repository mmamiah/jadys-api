package com.gogolo.jadys.sql.functions.impl;

import com.gogolo.jadys.sql.SqlStatement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gogolo.jadys.sql.functions.impl.SqlCharFunctions.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class SqlCharFunctionsTest {

    @Test
    void shouldGenerateAsciiSqlStatement(){
        // Arrange
        String arg = "D";

        // Act
        SqlStatement sql = ascii(arg);

        // Assert
        assertThat(String.valueOf(sql), equalTo("ASCII(D)"));
    }

    @Test
    void shouldGenerateChrSqlStatement(){
        // Arrange
        int arg = 84;

        // Act
        SqlStatement sql = chr(arg);

        // Assert
        assertThat(String.valueOf(sql), equalTo("CHR(84)"));
    }

    @Test
    void shouldGenerateConcatSqlStatementWhenSingleArgument(){
        // Arrange
        String arg = "first_name";

        // Act
        SqlStatement sql = concat(arg);

        // Assert
        assertThat(String.valueOf(sql), equalTo("first_name"));
    }

    @Test
    void shouldGenerateConcatSqlStatementWhenTwoArguments(){
        // Arrange
        String arg1 = "first_name";
        String arg2 = "last_name";

        // Act
        SqlStatement sql = concat(arg1, arg2);

        // Assert
        assertThat(String.valueOf(sql), equalTo("CONCAT(first_name, last_name)"));
    }

    @Test
    void shouldGenerateConcatSqlStatementWhenTwoPlusArguments(){
        // Arrange
        String arg1 = "first_name";
        String arg2 = "' '";
        String arg3 = "last_name";

        // Act
        SqlStatement sql = concat(arg1, arg2, arg3);

        // Assert
        assertThat(String.valueOf(sql), equalTo("first_name || ' ' || last_name"));
    }

    @Test
    void shouldGenerateInitcapSqlStatement(){
        // Arrange
        String field = "name";

        // Act
        SqlStatement sql = initCap(field);

        // Assert
        assertThat(String.valueOf(sql), equalTo("INITCAP(name)"));
    }

    @Test
    void shouldGenerateInstrSqlStatementWhenTwoArguments(){
        // Arrange
        String arg1 = "first_name";
        String arg2 = "is";

        // Act
        SqlStatement sql = inStr(arg1, arg2);

        // Assert
        assertThat(String.valueOf(sql), equalTo("INSTR(first_name, 'is')"));
    }

    @Test
    void shouldGenerateInstrSqlStatementWhenFourArguments(){
        // Arrange
        String arg1 = "first_name";
        String arg2 = "is";
        int start = 2;
        int nth = 1;

        // Act
        SqlStatement sql = inStr(arg1, arg2, 2, 1);

        // Assert
        assertThat(String.valueOf(sql), equalTo("INSTR(first_name, 'is', 2, 1)"));
    }

    @Test
    void shouldGenerateLengthSqlStatement(){
        // Arrange
        String arg = "first_name";

        // Act
        SqlStatement sql = length(arg);

        // Assert
        assertThat(String.valueOf(sql), equalTo("LENGTH(first_name)"));
    }

    @Test
    void shouldGenerateLowerSqlStatement(){
        // Arrange
        String arg = "user_age";

        // Act
        SqlStatement sql = lower(arg);

        // Assert
        assertThat(String.valueOf(sql), equalTo("LOWER(user_age)"));
    }

    @Test
    void shouldGenerateReplaceSqlStatementWhenTwoArguments(){
        // Arrange
        String originalValue = "alpha";
        String toBeReplaced = "beta";

        // Act
        SqlStatement sql = replace(originalValue, toBeReplaced);

        // Assert
        assertThat(sql.toString(), equalTo("REPLACE(alpha, 'beta')"));
    }

    @Test
    void shouldGenerateReplaceSqlStatementWhenThreeArguments(){
        // Arrange
        String originalValue = "alpha";
        String toBeReplaced = "beta";
        String newValue = "tera";

        // Act
        SqlStatement sql = replace(originalValue, toBeReplaced, newValue);

        // Assert
        assertThat(sql.toString(), equalTo("REPLACE('alpha', 'beta', 'tera')"));
    }

    @Test
    void shouldGenerateSubstrSqlStatementWhenTwoArguments(){
        // Arrange
        String originalValue = "alpha";
        int startPosition = 5;

        // Act
        SqlStatement sql = subStr(originalValue, startPosition);

        // Assert
        assertThat(sql.toString(), equalTo("SUBSTR('alpha', 5)"));
    }

    @Test
    void shouldGenerateSubstrSqlStatementWhenThreeArguments(){
        // Arrange
        String originalValue = "alpha";
        int startPosition = 5;
        int length = 3;

        // Act
        SqlStatement sql = subStr(originalValue, startPosition, length);

        // Assert
        assertThat(sql.toString(), equalTo("SUBSTR('alpha', 5, 3)"));
    }

    @Test
    void shouldGenerateTranslateSqlStatement(){
        // Arrange
        String originalValue = "alpha";
        String stringToReplace = "ph";
        String replacementString = "t";

        // Act
        SqlStatement sql = translate(originalValue, stringToReplace, replacementString);

        // Assert
        assertThat(sql.toString(), equalTo("TRANSLATE('alpha', 'ph', 't')"));
    }

    @Test
    void shouldGenerateTrimSqlStatement(){
        // Arrange
        String value = "alpha";

        // Act
        SqlStatement sql = trim(value);

        // Assert
        assertThat(sql.toString(), equalTo("TRIM('alpha')"));
    }

    @Test
    void shouldGenerateTrimSqlStatementWhenTrimCharacter(){
        // Arrange
        String value = "alpha";
        String trimCharacter = "d";

        // Act
        SqlStatement sql = trim(trimCharacter, value);

        // Assert
        assertThat(sql.toString(), equalTo("TRIM('d' FROM 'alpha')"));
    }

    @Test
    void shouldGenerateTrimLeadingSqlStatement(){
        // Arrange
        String value = "alpha";
        String trimCharacter = "d";

        // Act
        SqlStatement sql = trimLeading(trimCharacter, value);

        // Assert
        assertThat(sql.toString(), equalTo("TRIM(LEADING 'd' FROM 'alpha')"));
    }

    @Test
    void shouldGenerateTrimTrailingSqlStatementWhenTwoArguments(){
        // Arrange
        String value = "alpha";
        String trimCharacter = "h";

        // Act
        SqlStatement sql = trimTrailing(trimCharacter, value);

        // Assert
        assertThat(sql.toString(), equalTo("TRIM(TRAILING 'h' FROM 'alpha')"));
    }

    @Test
    void shouldGenerateUpperSqlStatement(){
        // Arrange
        String value = "alpha";

        // Act
        SqlStatement sql = upper(value);

        // Assert
        assertThat(sql.toString(), equalTo("UPPER('alpha')"));
    }

}