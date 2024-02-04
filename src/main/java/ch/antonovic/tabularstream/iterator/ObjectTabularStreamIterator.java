package ch.antonovic.tabularstream.iterator;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public interface ObjectTabularStreamIterator<T> extends TabularStreamIterator<T[]> {

	T valueFromColumn(int index);

	@Deprecated
	T cachedValueFromColumn(int index);

	// shortcuts for efficiency
	T mapUnary(UnaryOperator<T> operator);
	T mapBinary(BinaryOperator<T> operator);
}
