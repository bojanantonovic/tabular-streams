package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.function.FloatBinaryOperator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

public class BinaryMappingIterator extends FloatTabularStreamIteratorWrapper {
	private final FloatBinaryOperator binaryOperator;

	public BinaryMappingIterator(final FloatTabularStreamIterator parentIterator, final FloatBinaryOperator binaryOperator) {
		super(parentIterator);
		this.binaryOperator = binaryOperator;
	}

	@Override
	public float valueFromColumn(final int index) {
		return binaryOperator.applyAsFloat( //
				parentIterator.valueFromColumn(0), //
				parentIterator.valueFromColumn(1));
	}

	@Override
	public float[] next() {
		final var current = new float[] {valueFromColumn(0)};
		moveCursorToNextPosition();
		return current;
	}
}
