package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;
import org.springframework.lang.Nullable;

public class UnaryMappingColumnsIterator extends FloatTabularStreamIteratorWrapper {

	private final FloatUnaryOperator[] operators;
	private @Nullable float[] current;

	public UnaryMappingColumnsIterator(final FloatTabularStreamIterator parentIterator, final FloatUnaryOperator[] operators) {
		super(parentIterator);
		this.operators = operators;
	}

	@Override
	public float[] current() {
		assert current != null;
		return current;
	}

	@Override
	public float[] next() {
		final float[] next = parentIterator.next();
		for (var i = 0; i < next.length; i++) {
			next[i] = operators[i].applyAsFloat(next[i]);
		}
		current = next;
		return next;
	}
}
