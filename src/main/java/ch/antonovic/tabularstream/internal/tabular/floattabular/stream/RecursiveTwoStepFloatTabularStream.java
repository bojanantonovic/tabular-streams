package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.function.FloatBinaryOperator;
import ch.antonovic.tabularstream.internal.tabular.floattabular.iterator.RecursiveTwoStepFloatTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

public class RecursiveTwoStepFloatTabularStream extends FloatTabularStreamByRecursion {
	private final float[] first;
	private final float[] second;
	private final FloatBinaryOperator binaryOperator;

	public RecursiveTwoStepFloatTabularStream(final float[] first, final float[] second, final FloatBinaryOperator binaryOperator) {
		super(first.length);
		this.first = first;
		this.second = second;
		if (first.length != second.length) {
			throw new IllegalArgumentException("Arrays have different length");
		}
		this.binaryOperator = binaryOperator;
	}

	@Override
	public FloatTabularStreamIterator iterator() {
		return new RecursiveTwoStepFloatTabularStreamIterator(first, second, binaryOperator);
	}
}
