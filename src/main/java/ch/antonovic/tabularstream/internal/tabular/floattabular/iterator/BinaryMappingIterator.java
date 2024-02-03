package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.function.FloatBinaryOperator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

public class BinaryMappingIterator extends FloatTabularStreamIteratorWrapper {
	private final FloatBinaryOperator binaryOperator;

	private float currentValue = Float.NaN;

	public BinaryMappingIterator(final FloatTabularStreamIterator parentIterator, final FloatBinaryOperator binaryOperator) {
		super(parentIterator);
		this.binaryOperator = binaryOperator;
	}

	@Override
	public float[] current() {
		currentValue = binaryOperator.applyAsFloat( //
				parentIterator.valueFromColumn(0), //
				parentIterator.valueFromColumn(1));
		return new float[] {currentValue};
	}

	@Override
	public float[] next() {
		final var current = current();
		incrementPositionWithoutReading();
		return current;
	}

	@Override
	public float valueFromColumn(final int index) {
		if (index > 0) {
			throw new IllegalArgumentException();
		}
		return currentValue;
	}
}
