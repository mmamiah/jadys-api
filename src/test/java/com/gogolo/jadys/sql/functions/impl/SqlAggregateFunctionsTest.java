package com.gogolo.jadys.sql.functions.impl;

import com.gogolo.jadys.sql.SqlStatement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gogolo.jadys.sql.functions.impl.SqlAdvancedFunctions.nvl;
import static com.gogolo.jadys.sql.functions.impl.SqlAggregateFunctions.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class SqlAggregateFunctionsTest {

    @Test
    void shouldGenerateSumSqlStatement(){
        // Arrange
        String child_weight = "child_weight";

        // Act
        SqlStatement sql = sum(child_weight);

        // Assert
        assertThat(String.valueOf(sql), equalTo("SUM(child_weight)"));
    }

    @Test
    void shouldGenerateSumDistinctSqlStatement(){
        // Arrange
        String child_weight = "child_weight";

        // Act
        SqlStatement sql = sumDistinct(child_weight);

        // Assert
        assertThat(String.valueOf(sql), equalTo("SUM(DISTINCT child_weight)"));
    }

    @Test
    void shouldGenerateAvgSqlStatement(){
        // Arrange
        String child_weight = "child_weight";

        // Act
        SqlStatement sql = avg(child_weight);

        // Assert
        assertThat(String.valueOf(sql), equalTo("AVG(child_weight)"));
    }

    @Test
    void shouldGenerateAvgDistinctSqlStatement(){
        // Arrange
        String child_weight = "child_weight";

        // Act
        SqlStatement sql = avgDistinct(child_weight);

        // Assert
        assertThat(String.valueOf(sql), equalTo("AVG(DISTINCT child_weight)"));
    }

    @Test
    void shouldGenerateMaxSqlStatement(){
        // Arrange
        String child_weight = "child_weight";

        // Act
        SqlStatement sql = max(child_weight);

        // Assert
        assertThat(String.valueOf(sql), equalTo("MAX(child_weight)"));
    }

    @Test
    void shouldGenerateMaxDistinctSqlStatement(){
        // Arrange
        String child_weight = "child_weight";

        // Act
        SqlStatement sql = maxDistinct(child_weight);

        // Assert
        assertThat(String.valueOf(sql), equalTo("MAX(DISTINCT child_weight)"));
    }

    @Test
    void shouldGenerateMinSqlStatement(){
        // Arrange
        String child_weight = "child_weight";

        // Act
        SqlStatement sql = min(child_weight);

        // Assert
        assertThat(String.valueOf(sql), equalTo("MIN(child_weight)"));
    }

    @Test
    void shouldGenerateMinDistinctSqlStatement(){
        // Arrange
        String child_weight = "child_weight";

        // Act
        SqlStatement sql = minDistinct(child_weight);

        // Assert
        assertThat(String.valueOf(sql), equalTo("MIN(DISTINCT child_weight)"));
    }

    @Test
    void shouldGenerateCountSqlStatement(){
        // Arrange
        String child_weight = "child_weight";

        // Act
        SqlStatement sql = count(child_weight);

        // Assert
        assertThat(String.valueOf(sql), equalTo("COUNT(child_weight)"));
    }

    @Test
    void shouldGenerateCountDistinctSqlStatement(){
        // Arrange
        String child_weight = "child_weight";

        // Act
        SqlStatement sql = countDistinct(child_weight);

        // Assert
        assertThat(String.valueOf(sql), equalTo("COUNT(DISTINCT child_weight)"));
    }

}