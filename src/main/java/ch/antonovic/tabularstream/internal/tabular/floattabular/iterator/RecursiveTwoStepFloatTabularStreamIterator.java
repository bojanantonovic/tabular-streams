package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.function.FloatBinaryOperator;

public class RecursiveTwoStepFloatTabularStreamIterator extends FloatTabularStreamIteratorByRecursion {

	private final FloatBinaryOperator binaryOperator;

	public RecursiveTwoStepFloatTabularStreamIterator(final float[] first, final float[] second, final FloatBinaryOperator binaryOperator) {
		super(first, second);
		this.binaryOperator = binaryOperator;
	}

	@Override
	protected float[] computeNextValue() {
		final var next = new float[numberOfColumns];
		final var a = cache.get(cache.size() - 2);
		final var b = cache.getLast();
		for (var i = 0; i < numberOfColumns; i++) {
			next[i] = binaryOperator.applyAsFloat(a[i], b[i]);
		}
		return next;
	}
}
