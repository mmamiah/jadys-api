package com.gogolo.jadys.sql.clause;

import com.gogolo.jadys.sql.SqlStatement;
import com.gogolo.jadys.sql.statement.Select;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@ExtendWith(MockitoExtension.class)
class WhereClauseTest {

    @Test
    void shouldRunSqlCommand(){
        // Arrange

        // Act
        SqlStatement sql = Select.select().from("Students").where("name = 'john'");

        // Assert
        assertThat(sql.toString(), equalTo("SELECT * FROM STUDENTS WHERE name = 'john'"));
    }

}