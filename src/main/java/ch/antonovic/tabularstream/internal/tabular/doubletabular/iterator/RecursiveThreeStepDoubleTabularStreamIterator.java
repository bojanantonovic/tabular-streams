package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.function.DoubleTernaryOperator;

public class RecursiveThreeStepDoubleTabularStreamIterator extends DoubleTabularStreamIteratorByRecursion {

	private final DoubleTernaryOperator ternaryOperator;

	public RecursiveThreeStepDoubleTabularStreamIterator(final double[] first, final double[] second, final double[] third, final DoubleTernaryOperator ternaryOperator) {
		super(first, second, third);
		this.ternaryOperator = ternaryOperator;
	}

	@Override
	protected double[] computeNextValue() {
		final var next = new double[numberOfColumns];
		final var a = cache.get(cache.size() - 3);
		final var b = cache.get(cache.size() - 2);
		final var c = cache.getLast();
		for (var i = 0; i < numberOfColumns; i++) {
			next[i] = ternaryOperator.applyAsDouble(a[i], b[i], c[i]);
		}
		return next;
	}
}
