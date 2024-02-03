package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import java.util.function.DoubleBinaryOperator;

public class RecursiveTwoStepDoubleTabularStreamIterator extends DoubleTabularStreamIteratorByRecursion {

	private final DoubleBinaryOperator binaryOperator;

	public RecursiveTwoStepDoubleTabularStreamIterator(final double[] first, final double[] second, final DoubleBinaryOperator binaryOperator) {
		super(first, second);
		this.binaryOperator = binaryOperator;
	}

	@Override
	protected double[] computeNextValue() {
		final var next = new double[numberOfColumns];
		final var a = cache.get(cache.size() - 2);
		final var b = cache.getLast();
		for (var i = 0; i < numberOfColumns; i++) {
			next[i] = binaryOperator.applyAsDouble(a[i], b[i]);
		}
		return next;
	}
}
