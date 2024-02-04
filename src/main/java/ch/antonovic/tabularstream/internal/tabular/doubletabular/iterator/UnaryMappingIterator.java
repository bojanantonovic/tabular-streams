package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.function.DoubleUnaryOperator;

public class UnaryMappingIterator extends DoubleTabularStreamIteratorWrapper {
	private final DoubleUnaryOperator unaryOperator;

	private double cachedValue = Double.NaN;

	public UnaryMappingIterator(final DoubleTabularStreamIterator parentIterator, final DoubleUnaryOperator unaryOperator) {
		super(parentIterator);
		this.unaryOperator = unaryOperator;
	}

	@Override
	public double[] next() {
		final var nextValue = new double[] {valueFromColumn(0)};
		incrementPositionWithoutReading();
		return nextValue;
	}

	@Override
	public double valueFromColumn(final int index) {
		cachedValue = unaryOperator.applyAsDouble(parentIterator.valueFromColumn(0));
		return cachedValue;
	}

	@Override
	public double cachedValueFromColumn(final int index) {
		if (index > 0) {
			throw new IllegalArgumentException();
		}
		return cachedValue;
	}
}
