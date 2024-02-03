package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.function.FloatTernaryOperator;

public class RecursiveThreeStepFloatTabularStreamIterator extends FloatTabularStreamIteratorByRecursion {

	private final FloatTernaryOperator ternaryOperator;

	public RecursiveThreeStepFloatTabularStreamIterator(final float[] first, final float[] second, final float[] third, final FloatTernaryOperator ternaryOperator) {
		super(first, second, third);
		this.ternaryOperator = ternaryOperator;
	}

	@Override
	protected float[] computeNextValue() {
		final var next = new float[numberOfColumns];
		final var a = cache.get(cache.size() - 3);
		final var b = cache.get(cache.size() - 2);
		final var c = cache.getLast();
		for (var i = 0; i < numberOfColumns; i++) {
			next[i] = ternaryOperator.applyAsFloat(a[i], b[i], c[i]);
		}
		return next;
	}
}
