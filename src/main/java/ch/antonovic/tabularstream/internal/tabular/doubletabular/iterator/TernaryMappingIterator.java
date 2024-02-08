package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.function.DoubleTernaryOperator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

public class TernaryMappingIterator extends DoubleTabularStreamIteratorWrapper {
	private final DoubleTernaryOperator ternaryOperator;

	public TernaryMappingIterator(final DoubleTabularStreamIterator parentIterator, final DoubleTernaryOperator ternaryOperator) {
		super(parentIterator);
		this.ternaryOperator = ternaryOperator;
	}

	@Override
	public double valueFromColumn(final int index) {
		return ternaryOperator.applyAsDouble( //
				parentIterator.valueFromColumn(0), //
				parentIterator.valueFromColumn(1), //
				parentIterator.valueFromColumn(2));
	}

	@Override
	public double[] next() {
		final var current = new double[] {valueFromColumn(0)};
		moveCursorToNextPosition();
		return current;
	}
}
