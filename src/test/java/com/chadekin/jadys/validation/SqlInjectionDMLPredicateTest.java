package com.chadekin.jadys.validation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

/**
 * unit test for SqlInjectionPredicateTest
 */
public class SqlInjectionDMLPredicateTest {

	//	Data Definition Language(DDL) 		DROP, RENAME, CREATE, ALTER, TRUNCATE
	//	Data Manipulation Language(DML)		SELECT, UPDATE, DELETE, INSERT
	//	Transaction Control Language(TCL)	COMMIT, ROLLBACK, SAVEPOINT
	//	Data Control Language(DCL)			GRANT, REVOKE

	@Test
	public void shouldNotFindSqlInjectionForSelect() {
		// Arrange
		String sql = "SELECT * FROM users";
		
		// Act
		boolean isSqlInjection = SqlInjectionPredicate.isSqlInjection().test(sql);
		
		// Assert
		assertThat(isSqlInjection, is(false));
	}
	
	@Test
	public void shouldNotFindSqlInjectionWhenOneSelectSubQuery(){
		// Arrange
		String sql = "SELECT id FROM users where name  in (select name from users where age=25)";

		// Act
		boolean isSqlInjection = SqlInjectionPredicate.isSqlInjection().test(sql);

		// Assert
		assertThat(isSqlInjection, is(false));
	}

	@Test
	public void shouldNotFindSqlInjectionWhenTwoSelectSubQuery(){
		// Arrange
		String sql = "SELECT id " +
				"FROM users where name in " +
				"(" +
				"	select name " +
				"	from users " +
				"	where age=(select count(id) from cars)" +
				")";

		// Act
		boolean isSqlInjection = SqlInjectionPredicate.isSqlInjection().test(sql);

		// Assert
		assertThat(isSqlInjection, is(false));
	}

	@Test
	public void shouldFindSqlInjectionWhenThreeSelectSubQueryAndUpper(){
		// Arrange
		String sql = "SELECT id " +
				"FROM users where name in " +
				"(" +
				"	select name " +
				"	from users " +
				"	where age=(select count(id) from cars) and size=(select count(id) from clothes)" +
				")";

		// Act
		boolean isSqlInjection = SqlInjectionPredicate.isSqlInjection().test(sql);

		// Assert
		assertThat(isSqlInjection, is(true));
	}

	@Test
	public void shouldNotFindSqlInjectionForUpdate() {
		// Arrange
		String sql = "UPDATE users SET size=20";

		// Act
		boolean isSqlInjection = SqlInjectionPredicate.isSqlInjection().test(sql);

		// Assert
		assertThat(isSqlInjection, is(true));
	}

	@Test
	public void shouldNotFindSqlInjectionForDelete() {
		// Arrange
		String sql = "DELETE FROM users WHERE size=20";

		// Act
		boolean isSqlInjection = SqlInjectionPredicate.isSqlInjection().test(sql);

		// Assert
		assertThat(isSqlInjection, is(true));
	}

	@Test
	public void shouldNotFindSqlInjectionForInsert() {
		// Arrange
		String sql = "INSERT INTO users Values size=20";

		// Act
		boolean isSqlInjection = SqlInjectionPredicate.isSqlInjection().test(sql);

		// Assert
		assertThat(isSqlInjection, is(true));
	}
	
}