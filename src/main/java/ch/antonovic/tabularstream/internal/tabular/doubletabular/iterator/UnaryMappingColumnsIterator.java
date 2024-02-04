package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;
import org.springframework.lang.Nullable;

import java.util.function.DoubleUnaryOperator;

public class UnaryMappingColumnsIterator extends DoubleTabularStreamIteratorWrapper {

	private final DoubleUnaryOperator[] operators;
	private @Nullable double[] current;

	public UnaryMappingColumnsIterator(final DoubleTabularStreamIterator parentIterator, final DoubleUnaryOperator[] operators) {
		super(parentIterator);
		this.operators = operators;
	}

	@Override
	public double[] current() {
		assert current != null;
		return current;
	}

	@Override
	public double valueFromColumn(final int index) {
		return operators[index].applyAsDouble(parentIterator.valueFromColumn(index));
	}

	@Override
	public double[] next() {
		final double[] next = parentIterator.next();
		for (var i = 0; i < next.length; i++) {
			next[i] = operators[i].applyAsDouble(next[i]);
		}
		current = next;
		return next;
	}
}
