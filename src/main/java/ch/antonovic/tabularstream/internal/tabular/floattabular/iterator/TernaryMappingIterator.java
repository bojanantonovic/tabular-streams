package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.function.FloatTernaryOperator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

public class TernaryMappingIterator extends FloatTabularStreamIteratorWrapper {
	private final FloatTernaryOperator ternaryOperator;

	public TernaryMappingIterator(final FloatTabularStreamIterator parentIterator, final FloatTernaryOperator ternaryOperator) {
		super(parentIterator);
		this.ternaryOperator = ternaryOperator;
	}

	@Override
	public float valueFromColumn(final int index) {
		return ternaryOperator.applyAsFloat( //
				parentIterator.valueFromColumn(0), //
				parentIterator.valueFromColumn(1), //
				parentIterator.valueFromColumn(2));
	}

	@Override
	public float[] next() {
		final var current = new float[] {valueFromColumn(0)};
		moveCursorToNextPosition();
		return current;
	}
}
