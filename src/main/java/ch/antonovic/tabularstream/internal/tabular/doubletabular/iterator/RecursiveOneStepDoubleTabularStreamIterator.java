package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import java.util.function.DoubleUnaryOperator;

public class RecursiveOneStepDoubleTabularStreamIterator extends DoubleTabularStreamIteratorByRecursion {

	private final DoubleUnaryOperator unaryOperator;

	public RecursiveOneStepDoubleTabularStreamIterator(final double[] first, final DoubleUnaryOperator unaryOperator) {
		super(first);
		this.unaryOperator = unaryOperator;
	}

	@Override
	protected double[] computeNextValue() {
		final var next = new double[numberOfColumns];
		final var a = cache.get(cache.size() - 1);
		for (var i = 0; i < numberOfColumns; i++) {
			next[i] = unaryOperator.applyAsDouble(a[i]);
		}
		return next;
	}
}
