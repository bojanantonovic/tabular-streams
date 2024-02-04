package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

public class UnaryMappingIterator extends FloatTabularStreamIteratorWrapper {
	private final FloatUnaryOperator unaryOperator;

	private float currentValue = Float.NaN;

	public UnaryMappingIterator(final FloatTabularStreamIterator parentIterator, final FloatUnaryOperator unaryOperator) {
		super(parentIterator);
		this.unaryOperator = unaryOperator;
	}

	@Override
	public float[] current() {
		return new float[] {valueFromColumn(0)};
	}

	@Override
	public float valueFromColumn(final int index) {
		currentValue = unaryOperator.applyAsFloat(parentIterator.valueFromColumn(0));
		return currentValue;
	}

	@Override
	public float[] next() {
		final var current = current();
		incrementPositionWithoutReading();
		return current;
	}

	@Override
	public float cachedValueFromColumn(final int index) {
		if (index > 0) {
			throw new IllegalArgumentException();
		}
		return currentValue;
	}
}
