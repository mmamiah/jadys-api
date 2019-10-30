package com.gogolo.jadys.sql.functions;

import com.gogolo.jadys.sql.SqlStatement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gogolo.jadys.sql.functions.impl.SqlFunctionLibrary.*;
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
        SqlStatement sql = select(extract("alpha", "beta")).from(tableName);

        // Assert
        assertThat(sql.toString(), equalTo("SELECT EXTRACT(alpha FROM beta) FROM USER"));
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
    void shouldBuildSelectStatementWithMiscellaneousFunction(){
        // Arrange

        // Act
        SqlStatement sql = select(uid()).fromDual();

        // Assert
        assertThat(sql.toString(), equalTo("SELECT UID FROM DUAL"));
    }

}