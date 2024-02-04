package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.function.DoubleBinaryOperator;

public class BinaryMappingIterator extends DoubleTabularStreamIteratorWrapper {
	private final DoubleBinaryOperator binaryOperator;

	private double currentValue = Double.NaN;

	public BinaryMappingIterator(final DoubleTabularStreamIterator parentIterator, final DoubleBinaryOperator binaryOperator) {
		super(parentIterator);
		this.binaryOperator = binaryOperator;
	}

	@Override
	public double valueFromColumn(final int index) {
		currentValue = binaryOperator.applyAsDouble( //
				parentIterator.valueFromColumn(0), //
				parentIterator.valueFromColumn(1));
		return currentValue;
	}

	@Override
	public double[] next() {
		final var nextValue = new double[] {valueFromColumn(0)};
		incrementPositionWithoutReading();
		return nextValue;
	}

	@Override
	public double cachedValueFromColumn(final int index) {
		if (index > 0) {
			throw new IllegalArgumentException();
		}
		return currentValue;
	}
}
