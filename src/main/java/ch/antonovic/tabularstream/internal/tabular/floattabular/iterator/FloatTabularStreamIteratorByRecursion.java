package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.function.FloatBinaryOperator;
import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class FloatTabularStreamIteratorByRecursion implements FloatTabularStreamIterator {
	protected final List<float[]> cache = new ArrayList<>();
	protected final int numberOfColumns;

	protected float[][] initialValues;

	protected int actualPosition = 0;

	protected FloatTabularStreamIteratorByRecursion(final float[]... initialValues) {
		this.numberOfColumns = initialValues[0].length;
		this.initialValues = initialValues;
		inizializeCache();
	}

	@Override
	public float valueFromColumn(final int index) {
		if (numberOfDeliveredElements() == 0) {
			throw new IllegalStateException("next() has not been called");
		}
		return current()[index];
	}

	@Override
	public float mapUnary(final FloatUnaryOperator operator) {
		throw new UnsupportedOperationException(); // TODO cardinality check if a Map is a source?
	}

	@Override
	public float mapBinary(final FloatBinaryOperator operator) {
		throw new UnsupportedOperationException(); // TODO cardinality check if a Map is a source?
	}

	@Override
	public float[] current() {
		return cache.getLast();
	}

	@Override
	public void incrementPositionWithoutReading() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long numberOfDeliveredElements() {
		return cache.size();
	}

	@Override
	public void reset() {
		//cache.clear();
		//inizializeCache();
		actualPosition = 0;
	}

	private void inizializeCache() {
		Collections.addAll(cache, initialValues);
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public float[] next() {
		if (actualPosition < cache.size()) {
			actualPosition++;
			return cache.getLast();
		}
		final var next = computeNextValue();
		cache.add(next);
		actualPosition++;
		return next;
	}

	protected abstract float[] computeNextValue();
}
