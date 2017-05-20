package com.chadekin.jadys.commons;

/**
 * LazyBuilder
 */
public interface LazyBuilder<T> {

	public T lazy();

	public boolean isLazy();
	
}
