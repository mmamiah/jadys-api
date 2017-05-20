package com.chadekin.jadys.validation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Ignore;
import org.junit.Test;

/**
 * SqlInjectionDDLPredicateTest
 */
@Ignore
public class SqlInjectionDDLPredicateTest {

	//	Data Definition Language(DDL) 		DROP, RENAME, CREATE, ALTER, TRUNCATE
	//	Data Manipulation Language(DML)		SELECT, UPDATE, DELETE, INSERT
	//	Transaction Control Language(TCL)	COMMIT, ROLLBACK, SAVEPOINT
	//	Data Control Language(DCL)			GRANT, REVOKE

	@Test
	public void shouldFindSqlInjectionForDropDatabase() {
		// Arrange
		String sql = "DROP DATABASE utility";

		// Act
		boolean isSqlInjection = SqlInjectionPredicate.isSqlInjection().test(sql);

		// Assert
		assertThat(isSqlInjection, is(true));
	}
	
	@Test
	public void shouldFindSqlInjectionForDropTable() {
		// Arrange
		String sql = "DROP TABLE  utility";

		// Act
		boolean isSqlInjection = SqlInjectionPredicate.isSqlInjection().test(sql);

		// Assert
		assertThat(isSqlInjection, is(true));
	}

	@Test
	public void shouldFindSqlInjectionForDropUser() {
		// Arrange
		String sql = "DROP USER 'james'@'localhost'";

		// Act
		boolean isSqlInjection = SqlInjectionPredicate.isSqlInjection().test(sql);

		// Assert
		assertThat(isSqlInjection, is(true));
	}

	@Test
	public void shouldFindSqlInjectionForAlter() {
		// Arrange
		String sql = "ALTER USER 'jeffrey'@'localhost' IDENTIFIED BY 'new_password' PASSWORD EXPIRE";

		// Act
		boolean isSqlInjection = SqlInjectionPredicate.isSqlInjection().test(sql);

		// Assert
		assertThat(isSqlInjection, is(true));
	}
	
}
