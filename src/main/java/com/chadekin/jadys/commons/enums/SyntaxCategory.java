package com.chadekin.jadys.commons.enums;

/**
 * SyntaxCategory
 */
public enum SyntaxCategory {

	// Data Definition Language(DDL) 		DROP, RENAME, CREATE, ALTER, TRUNCATE
	DDL, 
	
	// Data Manipulation Language(DML)		SELECT, UPDATE, DELETE, INSERT
	DML, 
	
	// Transaction Control Language(TCL)	COMMIT, ROLLBACK, SAVEPOINT
	TCL, 
	
	// Data Control Language(DCL)			GRANT, REVOKE
	DCL;
	
}
