package com.chadekin.jadys.syntax.orderby;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;
import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.commons.AbstractSqlQueryBuilder;
import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.commons.resourcekeys.JadysKeys;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;

/**
 * AbstractOrderByClauseBuilderImpl
 */
public abstract class AbstractOrderByClauseBuilder<P> extends AbstractSqlQueryBuilder<P> {

	private static final Set<String> SORTING_ORDER = Sets.newHashSet(SqlLexical.ASC.getCode(), SqlLexical.DESC.getCode());

	private List<String> columnsToOrder = Lists.newArrayList();
	
	protected AbstractOrderByClauseBuilder(JadysSqlQueryBuilder parent) {
		super(parent);
	}

	@Override
	public SqlLexical getType() {
		return SqlLexical.ORDER_BY;
	}

	@Override
	public P append(String... expression) {
		if(expression!=null && expression.length > 0){

			Stream.of(expression)
					.filter(value -> StringUtils.isNotBlank(value))
					.forEach(expressionConsumer());
		}
		return (P) this;
	}

	private Consumer<String> expressionConsumer(){
		return value -> {
			extractAlias(value).stream()
					.forEach(this::withAlias);

			String tempValue = null;
			if (SORTING_ORDER.contains(value) && !columnsToOrder.isEmpty()) {
				tempValue = columnsToOrder.stream()
						.map(item -> new StringBuilder(item).append(JadysKeys.SPACE).append(value).toString())
						.reduce((a, b) -> new StringBuilder(a).append(JadysKeys.COMMA).append(JadysKeys.SPACE).append(b).toString())
						.orElse(StringUtils.EMPTY);
				columnsToOrder.clear();
			}
			else if (!SORTING_ORDER.contains(value)) {
				columnsToOrder.add(value);
			}
			if (StringUtils.isNotBlank(tempValue)) {
				if (getResultSql().length() > 0) {
					getResultSql().append(JadysKeys.COMMA);
				}
				getResultSql().append(JadysKeys.SPACE).append(tempValue);
			}
		};
	}

	@Override
	public String buildNext() {
		columnsToOrder.stream()
				.forEach(column_name -> {
					if (getResultSql().length() > 0) {
						getResultSql().append(JadysKeys.COMMA);
					}
					getResultSql().append(JadysKeys.SPACE).append(column_name);
				});
		return super.buildNext();
	}
	
}
