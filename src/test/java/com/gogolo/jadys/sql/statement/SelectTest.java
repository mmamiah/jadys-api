package com.gogolo.jadys.sql.statement;

import com.gogolo.jadys.sql.SqlStatement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class SelectTest {

    @Test
    void shouldAggregateColumnWhenSumFunction(){
        // Arrange
        String tableName = "User";

        // Act
        SqlStatement sql = Select.select()
                                .from("USER").as("u")
                                .join("CAR","c").on("u.id=c.id")
                                .where("u.name='john'")
                                .groupBy("u.name")
                                .orderBy("u.surname").asc();

        // Assert
        assertThat(sql.toString(), equalTo("" +
                "SELECT * " +
                "FROM USER AS u " +
                "JOIN CAR c ON u.id=c.id " +
                "WHERE u.name='john' " +
                "GROUP BY u.name " +
                "ORDER BY u.surname ASC"));
    }

}