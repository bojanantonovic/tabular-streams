package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.function.DoubleUnaryOperator;

public class UnaryMappingIterator extends DoubleTabularStreamIteratorWrapper {
	private final DoubleUnaryOperator unaryOperator;

	public UnaryMappingIterator(final DoubleTabularStreamIterator parentIterator, final DoubleUnaryOperator unaryOperator) {
		super(parentIterator);
		this.unaryOperator = unaryOperator;
	}

	@Override
	public double[] next() {
		final var nextValue = new double[] {valueFromColumn(0)};
		moveCursorToNextPosition();
		return nextValue;
	}

	@Override
	public double valueFromColumn(final int index) {
		return unaryOperator.applyAsDouble(parentIterator.valueFromColumn(0));
	}
}
