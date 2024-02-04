package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

public class UnaryMappingAllFieldsIterator extends FloatTabularStreamIteratorWrapper {

	private final FloatUnaryOperator operator;

	public UnaryMappingAllFieldsIterator(final FloatTabularStreamIterator parentIterator, final FloatUnaryOperator operator) {
		super(parentIterator);
		this.operator = operator;
	}

	@Override
	public float valueFromColumn(final int index) {
		return operator.applyAsFloat(parentIterator.valueFromColumn(index));
	}

	@Override
	public float[] next() {
		final float[] next = parentIterator.next();
		for (var i = 0; i < next.length; i++) {
			next[i] = operator.applyAsFloat(next[i]);
		}
		return next;
	}
}
