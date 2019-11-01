package com.gogolo.jadys.sql.functions;

import com.gogolo.jadys.sql.SqlStatement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gogolo.jadys.sql.functions.impl.SqlAdvancedFunctions.uid;
import static com.gogolo.jadys.sql.functions.impl.SqlCharFunctions.*;
import static com.gogolo.jadys.sql.functions.impl.SqlDateFunctions.extract;
import static com.gogolo.jadys.sql.functions.impl.SqlMathFunctions.sum;
import static com.gogolo.jadys.sql.statement.select.impl.Select.select;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class SqlFunctionTest {

    @Test
    void shouldBuildSelectStatementWithAggregationFunction(){
        // Arrange
        String tableName = "User";

        // Act
        SqlStatement sql = select(sum("salary")).from(tableName);

        // Assert
        assertThat(sql.toString(), equalTo("SELECT SUM(salary) FROM USER"));
    }

    @Test
    void shouldBuildSelectStatementWithCharacterFunctionWhenSingleArgument(){
        // Arrange
        String tableName = "User";

        // Act
        SqlStatement sql = select(lower("salary")).from(tableName);

        // Assert
        assertThat(sql.toString(), equalTo("SELECT LOWER(salary) FROM USER"));
    }

    @Test
    void shouldBuildSelectStatementWithCharacterFunctionWhenDoubleArgument(){
        // Arrange
        String tableName = "User";

        // Act
        SqlStatement sql = select(extract("YEAR", "2003-08-22")).from(tableName);

        // Assert
        assertThat(sql.toString(), equalTo("SELECT EXTRACT(YEAR FROM DATE '2003-08-22') FROM USER"));
    }

    @Test
    void shouldBuildSelectStatementWithCharacterFunctionWhenTwoArgumentsOverThree(){
        // Arrange
        String tableName = "User";

        // Act
        SqlStatement sql = select(replace("alpha", "beta")).from(tableName);

        // Assert
        assertThat(sql.toString(), equalTo("SELECT REPLACE(alpha, beta) FROM USER"));
    }

    @Test
    void shouldBuildSelectStatementWithCharacterFunctionWhenThreeArgumentsOverThree(){
        // Arrange
        String tableName = "User";

        // Act
        SqlStatement sql = select(replace("alpha", "beta", "teta")).from(tableName);

        // Assert
        assertThat(sql.toString(), equalTo("SELECT REPLACE(alpha, beta, teta) FROM USER"));
    }

    @Test
    void shouldConcatOneArgument(){
        // Arrange

        // Act
        SqlStatement sql = select(concat("one")).fromDual();

        // Assert
        assertThat(sql.toString(), equalTo("SELECT one FROM DUAL"));
    }

    @Test
    void shouldConcatTwoArguments(){
        // Arrange

        // Act
        SqlStatement sql = select(concat("Tex", "Walter")).fromDual();

        extract(null, null);

        // Assert
        assertThat(sql.toString(), equalTo("SELECT CONCAT(Tex, Walter) FROM DUAL"));
    }

    @Test
    void shouldConcatMoreThanTwoArguments(){
        // Arrange

        // Act
        SqlStatement sql = select(concat("Tex", "colt", "Walter")).fromDual();

        // Assert
        assertThat(sql.toString(), equalTo("SELECT Tex || colt || Walter FROM DUAL"));
    }

    @Test
    void shouldBuildSelectStatementWithMiscellaneousFunction(){
        // Arrange

        // Act
        SqlStatement sql = select(uid()).fromDual();

        // Assert
        assertThat(sql.toString(), equalTo("SELECT UID FROM DUAL"));
    }

}