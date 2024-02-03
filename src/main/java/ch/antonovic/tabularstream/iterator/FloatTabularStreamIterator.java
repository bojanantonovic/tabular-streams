package ch.antonovic.tabularstream.iterator;

import ch.antonovic.tabularstream.function.FloatBinaryOperator;
import ch.antonovic.tabularstream.function.FloatUnaryOperator;

public interface FloatTabularStreamIterator extends TabularStreamIterator<float[]> {
	float valueFromColumn(int index);

	// shortcuts for efficiency
	float mapUnary(FloatUnaryOperator operator);
	float mapBinary(FloatBinaryOperator operator);
}
