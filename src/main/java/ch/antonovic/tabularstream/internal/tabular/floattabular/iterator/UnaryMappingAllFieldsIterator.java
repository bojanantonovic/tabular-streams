package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;
import org.springframework.lang.Nullable;

public class UnaryMappingAllFieldsIterator extends FloatTabularStreamIteratorWrapper {

	private final FloatUnaryOperator operator;
	private @Nullable float[] current;

	public UnaryMappingAllFieldsIterator(final FloatTabularStreamIterator parentIterator, final FloatUnaryOperator operator) {
		super(parentIterator);
		this.operator = operator;
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
			next[i] = operator.applyAsFloat(next[i]);
		}
		current = next;
		return next;
	}
}
