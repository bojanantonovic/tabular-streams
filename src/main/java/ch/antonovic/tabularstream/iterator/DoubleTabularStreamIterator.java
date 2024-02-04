package ch.antonovic.tabularstream.iterator;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public interface DoubleTabularStreamIterator extends TabularStreamIterator<double[]> {

	double valueFromColumn(int index);

	@Deprecated
	double cachedValueFromColumn(int index);

	// shortcuts for efficiency
	double mapUnary(DoubleUnaryOperator operator);
	double mapBinary(DoubleBinaryOperator operator);
}
