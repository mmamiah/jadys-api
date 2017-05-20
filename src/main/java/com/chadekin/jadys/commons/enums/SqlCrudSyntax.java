/**
 *                     GNU General Public License (GPL)
 *                        version 3.0, 29 June 2007
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 *
 * Copyright (C) 2017 Marc Mamiah.
 * License GPLv3+: GNU GPL version 3
 */
package com.chadekin.jadys.commons.enums;

/**
 * SqlSyntax
 */
public enum SqlCrudSyntax implements BusinessEnum {

//	TODO-mmamiah Should be devided again between
	
//	Data Definition Language(DDL) 		DROP, RENAME, CREATE, ALTER, TRUNCATE
//	Data Manipulation Language(DML)		SELECT, UPDATE, DELETE, INSERT
//	Transaction Control Language(TCL)	COMMIT, ROLLBACK, SAVEPOINT
//	Data Control Language(DCL)			GRANT, REVOKE

	SELECT("SELECT", "SELECT", SyntaxCategory.DML),
	UPDATE("UPDATE", "UPDATE", SyntaxCategory.DML),
	DELETE("DELETE", "DELETE", SyntaxCategory.DML),
	INSERT_INTO("INSERT_INTO", "INSERT INTO", SyntaxCategory.DML),
	MERGE("MERGE", "MERGE", SyntaxCategory.DML),
	EXPLAIN_PLAN("EXPLAIN_PLAN", "EXPLAIN PLAN", SyntaxCategory.DML),
	LOCK_TABLE("LOCK_TABLE", "LOCK TABLE", SyntaxCategory.DML),
	CALL("CALL", "CALL", SyntaxCategory.DML),

	CREATE("CREATE", "CREATE", SyntaxCategory.DDL),
	ALTER("ALTER", "ALTER", SyntaxCategory.DDL),
	DROP("DROP", "DROP", SyntaxCategory.DDL),
	TRUNCATE("TRUNCATE", "TRUNCATE", SyntaxCategory.DDL),
	COMMENT("COMMENT", "COMMENT", SyntaxCategory.DDL),
	RENAME("RENAME", "RENAME", SyntaxCategory.DDL),

	GRANT("GRANT", "GRANT", SyntaxCategory.DCL),
	REVOKE("REVOKE", "REVOKE", SyntaxCategory.DCL),

	COMMIT("COMMIT", "COMMIT", SyntaxCategory.TCL),
	SAVEPOINT("SAVEPOINT", "SAVEPOINT", SyntaxCategory.TCL),
	ROLLBACK("ROLLBACK", "ROLLBACK", SyntaxCategory.TCL),
	SET_TRANSACTION("SET_TRANSACTION", "SET TRANSACTION", SyntaxCategory.TCL);
	
	private String code;
	private String name;
	private SyntaxCategory category;

	SqlCrudSyntax(String code, String name, SyntaxCategory category) {
		this.code = code;
		this.name = name;
		this.category = category;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getName() {
		return name;
	}

	public SyntaxCategory getCategory() {
		return category;
	}
	
}
