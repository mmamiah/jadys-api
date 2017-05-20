package com.chadekin.jadys.syntax;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.chadekin.jadys.commons.resourcekeys.JadysExceptionMessageKeys;
import com.chadekin.jadys.syntax.select.impl.DynamicSqlFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit test for AliasFiltering
 */
public class AliasFilteringTest {

	@Rule
	public ExpectedException exception =  ExpectedException.none();

	@Test
	public void shouldDetectMissingAliasIssueInFromClause(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.INVALID_ALIAS);
		
		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select("user.name", "user.surname")
				.from("User_")
				.build();
	}

	@Test
	public void shouldDetectMissingAliasIssueInSelectStatement(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.INVALID_ALIAS);
		
		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select("name", "surname")
				.from("User_").as("user")
				.build();
	}

	@Test
	public void shouldThrowExceptionWhenFromClauseHasAliasAndNotAllSelectedColumnHasAlias(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.INVALID_ALIAS);

		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select("name", "user.surname")
				.from("User_").as("user")
				.build();
	}

	@Test
	public void shouldThrowExceptionWhenAliasNotMatch(){
		// Assert
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(JadysExceptionMessageKeys.INVALID_ALIAS);

		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select("us.name", "user.surname")
				.from("User_").as("user")
				.build();
	}

	@Test
	public void shouldBuildQueryContainingAlias(){
		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select("user.name", "user.surname")
				.from("User_").as("user")
				.build();

		// Assert
		assertThat(sql, is("SELECT user.name, user.surname FROM User_ AS user"));
	}

	@Test
	public void shouldBuildQueryNotContainingAlias(){
		// Act
		String sql = DynamicSqlFactory.newQuery()
				.select("name", "surname")
				.from("User_")
				.build();

		// Assert
		assertThat(sql, is("SELECT name, surname FROM User_"));
	}
}
