package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

public class UnaryMappingColumnsIterator extends FloatTabularStreamIteratorWrapper {

	private final FloatUnaryOperator[] operators;

	public UnaryMappingColumnsIterator(final FloatTabularStreamIterator parentIterator, final FloatUnaryOperator[] operators) {
		super(parentIterator);
		this.operators = operators;
	}

	@Override
	public float valueFromColumn(final int index) {
		return operators[index].applyAsFloat(parentIterator.valueFromColumn(index));
	}

	@Override
	public float[] next() {
		final float[] next = parentIterator.next();
		for (var i = 0; i < next.length; i++) {
			next[i] = operators[i].applyAsFloat(next[i]);
		}

		return next;
	}
}
