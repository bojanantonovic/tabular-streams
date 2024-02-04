package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

public class UnaryMappingIterator extends FloatTabularStreamIteratorWrapper {
	private final FloatUnaryOperator unaryOperator;

	public UnaryMappingIterator(final FloatTabularStreamIterator parentIterator, final FloatUnaryOperator unaryOperator) {
		super(parentIterator);
		this.unaryOperator = unaryOperator;
	}

	@Override
	public float valueFromColumn(final int index) {
		return unaryOperator.applyAsFloat(parentIterator.valueFromColumn(0));
	}

	@Override
	public float[] next() {
		final var current = new float[] {valueFromColumn(0)};
		moveCursorToNextPosition();
		return current;
	}
}
