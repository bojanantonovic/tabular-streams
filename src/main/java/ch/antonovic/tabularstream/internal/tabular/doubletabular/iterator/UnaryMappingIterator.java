package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.function.DoubleUnaryOperator;

public class UnaryMappingIterator extends DoubleTabularStreamIteratorWrapper {
	private final DoubleUnaryOperator unaryOperator;

	private double currentValue = Double.NaN;

	public UnaryMappingIterator(final DoubleTabularStreamIterator parentIterator, final DoubleUnaryOperator unaryOperator) {
		super(parentIterator);
		this.unaryOperator = unaryOperator;
	}

	@Override
	public double[] next() {
		currentValue = unaryOperator.applyAsDouble(parentIterator.valueFromColumn(0));
		final var nextValue = new double[] {currentValue};
		incrementPositionWithoutReading();
		return nextValue;
	}

	@Override
	public double valueFromColumn(final int index) {
		if (index > 0) {
			throw new IllegalArgumentException();
		}
		return currentValue;
	}
}
