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
package com.chadekin.jadys.sqlfunction;

import com.chadekin.jadys.sqlfunction.aggregate.commons.AverageSqlFunction;
import com.chadekin.jadys.sqlfunction.aggregate.commons.CountSqlFunction;
import com.chadekin.jadys.sqlfunction.aggregate.commons.MaxSqlFunction;
import com.chadekin.jadys.sqlfunction.aggregate.commons.MinSqlFunction;
import com.chadekin.jadys.sqlfunction.aggregate.commons.RoundSqlFunction;
import com.chadekin.jadys.sqlfunction.aggregate.commons.SumSqlFunction;

/**
 * SqlAggregateFunction
 * Aggregate functions calculate a single value from multiple rows. Aggregate functions ignore NULL values.
 */
public interface SqlAggregateFunction<T> extends CountSqlFunction<T>, AverageSqlFunction<T>, MaxSqlFunction<T>, MinSqlFunction<T>, RoundSqlFunction<T>, SumSqlFunction<T> {
	
}
