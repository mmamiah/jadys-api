package com.chadekin.jadys.sqldialect;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.chadekin.jadys.syntax.select.impl.DynamicSqlFactory;
import org.junit.Test;

/**
 * MySqlDialectTest
 */
public class MySqlDialectTest {

	@Test
	public void shouldSqlQueryViaMySqlBuilder(){
		// Arrange

		DynamicSqlFactory.newQuery()
				.select("name")
				.from("User_")
				.where("one").equal("this")
				.groupBy("name")
				.having("age").lessThan(25)
				.orderBy("size").desc()
				.build();
		
		DynamicSqlFactory.newQuery()
				.select("user.name")
				.from("User_").as("user")
				.fullJoin("Automotive", "car").on("car.id").equal("user.carId")
				.leftJoin("Building", "house").on("house.id").equal("car.BuildingId")
				.join("NationalBank", "bank").on("bank.id").equal("house.nationalBankId")
				.rightJoin("HighSchool", "school").on("school.id").equal("bank.schoolId")
				.where("user.one").equal("this")
				.groupBy("user.name")
				.having("user.age").lessThan(25)
				.orderBy("user.size").desc()
				.build();
		
		// Act
		String sql = DynamicSqlFactory.newMySqlQuery()
				.select("user.name")
				.from("User_").as("user")
				.fullJoin("Automotive", "car").on("car.id").equal("user.carId")
				.leftJoin("Building", "house").on("house.id").equal("car.BuildingId")
				.join("NationalBank", "bank").on("bank.id").equal("house.nationalBankId")
				.rightJoin("HighSchool", "school").on("school.id").equal("bank.schoolId")
				.where("user.one").equal("this")
				.groupBy("user.name")
				.having("user.age").lessThan(25)
//				.orderBy("user.size").desc()
				.build();
		
		// Assert
		assertThat(sql, is(""));
	}
}