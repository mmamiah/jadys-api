package com.chadekin.jadys.syntax.groupby.generic;

import com.chadekin.jadys.syntax.having.HavingStatement;
import com.chadekin.jadys.syntax.orderby.OrderByStatement;

/**
 * PostGroupByDefault
 */
public interface DefaultPostGroupByTerm<G> extends HavingStatement<G>, OrderByStatement<G> {
}
