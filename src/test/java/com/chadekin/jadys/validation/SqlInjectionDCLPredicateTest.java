package com.chadekin.jadys.validation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Ignore;
import org.junit.Test;

/**
 * SqlInjectionDCLPredicateTest
 */
@Ignore
public class SqlInjectionDCLPredicateTest {

	//	Data Definition Language(DDL) 		DROP, RENAME, CREATE, ALTER, TRUNCATE
	//	Data Manipulation Language(DML)		SELECT, UPDATE, DELETE, INSERT
	//	Transaction Control Language(TCL)	COMMIT, ROLLBACK, SAVEPOINT
	//	Data Control Language(DCL)			GRANT, REVOKE

	@Test
	public void shouldFindSqlInjectionForGrant() {
		// Arrange
		String sql = "GRANT ALL ON alphadb.* TO 'james'@'localhost';";

		// Act
		boolean isSqlInjection = SqlInjectionPredicate.isSqlInjection().test(sql);

		// Assert
		assertThat(isSqlInjection, is(true));
	}
	
	@Test
	public void shouldFindSqlInjectionForRevoke() {
		// Arrange
		String sql = "REVOKE INSERT ON *.* FROM 'james'@'localhost';";

		// Act
		boolean isSqlInjection = SqlInjectionPredicate.isSqlInjection().test(sql);

		// Assert
		assertThat(isSqlInjection, is(true));
	}
	
}
