package com.chadekin.jadys.sqldialect;

import com.chadekin.jadys.syntax.having.HavingStatement;
import com.chadekin.jadys.syntax.orderby.OrderByStatement;

/**
 * PostGroupByDefault
 */
public interface DefaultPostGroupByTerm<G> extends HavingStatement<G>, OrderByStatement<G> {
}
