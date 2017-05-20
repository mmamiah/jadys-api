package com.chadekin.jadys.syntax.select;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import com.chadekin.jadys.JadysSqlQueryBuilder;
import com.chadekin.jadys.commons.AbstractSqlQueryBuilder;
import com.chadekin.jadys.commons.enums.SqlLexical;
import com.chadekin.jadys.commons.resourcekeys.JadysExceptionMessageKeys;
import com.chadekin.jadys.commons.resourcekeys.JadysKeys;
import com.chadekin.jadys.syntax.from.FromClauseBuilder;
import com.chadekin.jadys.syntax.select.impl.DynamicSqlFactory;
import com.chadekin.jadys.syntax.select.operation.SelectStatementOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * AbstractSelectBuilder
 * @param <T> MySqlSelect<T, FX, W, O>
 * @param <F> The FromClause as per Sql dialect
 * @param <FX> FX extends FromClauseExtendedBuilder<FX, W, O>
 */
public abstract class AbstractSelectBuilder<T, F, FX> extends AbstractSqlQueryBuilder<T> implements SelectClauseBuilder<F, FX>  {

	private static Logger LOGGER = Logger.getLogger(DynamicSqlFactory.class);
	private StringBuilder sqlQuery;
	private String footer;
	private boolean lazy = false;
	
	protected AbstractSelectBuilder(){
		super(null);
	}

	@Override
	public T append(String... value) {
		if(value.length > 0){
			for(String item: value){
				if(StringUtils.isBlank(item)){
					continue;
				}
				if(getResultSql().length()!=0){
					getResultSql().append(JadysKeys.COMMA).append(JadysKeys.SPACE);
				}
				getResultSql().append(item);
			}
		}
		return (T) this;
	}

	@Override
	public T addArgument(Object arg) {
		if(arg != null){
			append(arg.toString());
		}
		return (T) this;
	}

	@Override
	public String buildNext() {
		StringBuilder result = new StringBuilder();
		String value = getValue().trim();
		extractAlias(value).stream()
				.forEach(this::withAlias);
		if(StringUtils.isNotBlank(value)){
			result.append(getType())
					.append(JadysKeys.SPACE)
					.append(value);
		}
		else if(getChild() != null){
			result.append(getType())
					.append(JadysKeys.SPACE)
					.append(JadysKeys.STAR);
		}
		
		if(getChild() != null){
			if(!isValidSelectClausePredicate(getChild()).test(value)){
				StringBuilder exceptionMessage = new StringBuilder(JadysExceptionMessageKeys.INVALID_ALIAS)
						.append(JadysKeys.COLUMN).append(JadysKeys.SPACE);
				if(StringUtils.isBlank(value)){
					exceptionMessage.append("in Select Statement");
				}
				else{
					exceptionMessage.append(JadysKeys.APOSTROPHE).append(value).append(JadysKeys.APOSTROPHE);
				}
				
				throw new IllegalArgumentException(exceptionMessage.toString());
			}
			
			result.append(JadysKeys.SPACE)
					.append(getChild().buildNext());
		}
		return result.toString().trim();
	}

	private Predicate<String> isValidSelectClausePredicate(JadysSqlQueryBuilder fromBuilder){
		return selected_columns -> {
			boolean result = false;
			String[] columns = selected_columns.split(JadysKeys.COMMA);
			if(fromClauseWithAliasPredicate().test(fromBuilder)){
				result = Stream.of(columns)
						.allMatch(columnAsAlias(fromBuilder.getAlias()));
			}
			else{
				result = Stream.of(columns)
						.allMatch(column_name -> extractAlias(column_name).isEmpty());
				int i=1;
				i++;
			}
			return result;
		};
	}
	
	private Predicate<JadysSqlQueryBuilder> fromClauseWithAliasPredicate(){
		return f -> f instanceof FromClauseBuilder && !f.getAlias().isEmpty();
	}
	
	private Predicate<String> columnAsAlias(Set<String> sourceAlias){
		return column_name -> {
			Set<String> extractedAlias = extractAlias(column_name);
			return extractedAlias.size()==1 && sourceAlias.contains(extractedAlias.iterator().next());
		};
	}
	
	@Override
	public SqlLexical getType() {
		return SqlLexical.SELECT;
	}

	@Override
	public SelectStatement<SelectStatementOperation<F, FX>> lazy() {
		this.lazy = true;
		return this;
	}

	@Override
	public boolean isLazy() {
		return lazy;
	}
}
