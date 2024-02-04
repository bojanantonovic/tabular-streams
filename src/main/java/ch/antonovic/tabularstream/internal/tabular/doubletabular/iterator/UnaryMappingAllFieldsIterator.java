package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;
import org.springframework.lang.Nullable;

import java.util.function.DoubleUnaryOperator;

public class UnaryMappingAllFieldsIterator extends DoubleTabularStreamIteratorWrapper {

	private final DoubleUnaryOperator operator;
	private @Nullable double[] current;

	public UnaryMappingAllFieldsIterator(final DoubleTabularStreamIterator parentIterator, final DoubleUnaryOperator operator) {
		super(parentIterator);
		this.operator = operator;
	}

	@Override
	public double[] current() {
		assert current != null;
		return current;
	}

	@Override
	public double valueFromColumn(final int index) {
		return operator.applyAsDouble(parentIterator.valueFromColumn(index));
	}

	@Override
	public double[] next() {
		final double[] next = parentIterator.next();
		for (var i = 0; i < next.length; i++) {
			next[i] = operator.applyAsDouble(next[i]);
		}
		current = next;
		return next;
	}
}
