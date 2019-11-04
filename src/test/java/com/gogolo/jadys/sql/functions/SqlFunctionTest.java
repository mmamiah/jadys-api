package com.gogolo.jadys.sql.functions;

import com.gogolo.jadys.sql.SqlStatement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gogolo.jadys.sql.functions.impl.SqlConversionFunctions.toChar;
import static com.gogolo.jadys.sql.functions.impl.SqlConversionFunctions.toDate;
import static com.gogolo.jadys.sql.statement.Select.select;
import static com.gogolo.jadys.sql.statement.impl.Column.column;
import static com.gogolo.jadys.sql.statement.impl.Distinct.distinct;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class SqlFunctionTest {

    @Test
    void shouldSelectDayFromGivenDateAndMasks(){
        // Arrange
        String userAge = "user_age";

        // Act
        SqlStatement sql = select(toChar(toDate("user_age", "dd-mon-yyyy"), "Day"))
                            .fromDual();

        // Assert
        assertThat(sql.toString(), equalTo("SELECT to_char(to_date(user_age, 'dd-mon-yyyy'), 'Day') FROM DUAL"));
    }

    @Test
    void shouldSelectFromTableStatement(){
        // Arrange
        String tableName = "User";

        // Act
        SqlStatement sql = select().from(tableName);

        // Assert
        assertThat(sql.toString(), equalTo("SELECT * FROM USER"));
    }

    @Test
    void shouldSelectAllFromTableStatementWhenNullString(){
        // Arrange
        String tableName = "User";

        // Act
        SqlStatement sql = select((String)null).from(tableName);

        // Assert
        assertThat(sql.toString(), equalTo("SELECT * FROM USER"));
    }

    @Test
    void shouldSelectAllFromTableStatementWhenNullSelectExpression(){
        // Arrange
        String tableName = "User";

        // Act
        SqlStatement sql = select((SqlStatement) null).from(tableName);

        // Assert
        assertThat(sql.toString(), equalTo("SELECT * FROM USER"));
    }

    @Test
    void shouldSelectAllFromTableStatementWhenStartProvided(){
        // Arrange
        String tableName = "User";

        // Act
        SqlStatement sql = select("*").from(tableName);

        // Assert
        assertThat(sql.toString(), equalTo("SELECT * FROM USER"));
    }

    @Test
    void shouldSelectArgsFromTableStatementWhenArgsProvided(){
        // Arrange
        String tableName = "User";

        // Act
        SqlStatement sql = select("name", "surname").from(tableName);

        // Assert
        assertThat(sql.toString(), equalTo("SELECT name, surname FROM USER"));
    }

    @Test
    void shouldSelectArgFromTableStatementWhenField() {
        // Arrange
        String tableName = "User";

        // Act
        SqlStatement sql = select(column("name")).from(tableName);

        // Assert
        assertThat(sql.toString(), equalTo("SELECT name FROM USER"));
    }

    @Test
    void shouldSelectArgFromTableStatementWhenArgsProvided(){
        // Arrange
        String tableName = "User";

        // Act
        SqlStatement sql = select(column("name").as("sur_name")).from(tableName);

        // Assert
        assertThat(sql.toString(), equalTo("SELECT name as sur_name FROM USER"));
    }

    @Test
    void shouldSelectArgFromTableStatementWhen2Args(){
        // Arrange
        String tableName = "User";

        // Act
        SqlStatement sql = select(
                column("name").as("sur_name"),
                column("date_of_birth").as("dob")
        ).from(tableName);

        // Assert
        assertThat(sql.toString(), equalTo("SELECT name as sur_name, date_of_birth as dob FROM USER"));
    }

    @Test
    void shouldSelectArgFromTableStatementWhen3Args(){
        // Arrange
        String tableName = "User";

        // Act
        SqlStatement sql = select(
                column("name").as("sur_name"),
                column("date_of_birth").as("dob"),
                column("place_of_birth").as("pob")
        ).from(tableName);

        // Assert
        assertThat(sql.toString(), equalTo(
                "SELECT name as sur_name, date_of_birth as dob, place_of_birth as pob " +
                        "FROM USER"));
    }

    @Test
    void shouldSelectArgFromTableStatementWhen4Args(){
        // Arrange
        String tableName = "User";

        // Act
        SqlStatement sql = select(
                column("name").as("sur_name"),
                column("date_of_birth").as("dob"),
                column("place_of_birth").as("pob"),
                column("password").as("pwd")
        ).from(tableName);

        // Assert
        assertThat(sql.toString(), equalTo(
                "SELECT name as sur_name, date_of_birth as dob, place_of_birth as pob, password as pwd " +
                        "FROM USER"));
    }

    @Test
    void shouldSelectDistinctArgFromTableStatementWhenField() {
        // Arrange
        String tableName = "User";

        // Act
        SqlStatement sql = select(distinct("name")).from(tableName);

        // Assert
        assertThat(sql.toString(), equalTo("SELECT DISTINCT name FROM USER"));
    }

    @Test
    void shouldSelectDistinctArgAndAdditionalColumnFromTableStatement() {
        // Arrange
        String tableName = "User";

        // Act
        SqlStatement sql = select(distinct("name").as("nm"), column("date_of_birth").as("dob"))
                .from(tableName);

        // Assert
        assertThat(sql.toString(), equalTo("SELECT DISTINCT name as nm, date_of_birth as dob FROM USER"));
    }

    @Test
    void shouldSelectFromDual() {
        // Arrange
        String tableName = "User";

        // Act
        SqlStatement sql = select(column("name"))
                .fromDual();

        // Assert
        assertThat(sql.toString(), equalTo("SELECT name FROM DUAL"));
    }

}