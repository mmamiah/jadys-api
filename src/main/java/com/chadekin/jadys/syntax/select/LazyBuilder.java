package com.chadekin.jadys.syntax.select;

/**
 * LazyBuilder
 */
public interface LazyBuilder<T> {

	public T lazy();

	public boolean isLazy();
	
}
