package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.function.FloatUnaryOperator;

public class RecursiveOneStepFloatTabularStreamIterator extends FloatTabularStreamIteratorByRecursion {

	private final FloatUnaryOperator unaryOperator;

	public RecursiveOneStepFloatTabularStreamIterator(final float[] first, final FloatUnaryOperator unaryOperator) {
		super(first);
		this.unaryOperator = unaryOperator;
	}

	@Override
	protected float[] computeNextValue() {
		final var next = new float[numberOfColumns];
		final var a = cache.get(cache.size() - 1);
		for (var i = 0; i < numberOfColumns; i++) {
			next[i] = unaryOperator.applyAsFloat(a[i]);
		}
		return next;
	}
}
