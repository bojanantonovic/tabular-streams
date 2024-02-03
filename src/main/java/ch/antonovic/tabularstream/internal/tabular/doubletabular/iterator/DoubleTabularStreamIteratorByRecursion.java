package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public abstract class DoubleTabularStreamIteratorByRecursion implements DoubleTabularStreamIterator {
	protected final List<double[]> cache = new ArrayList<>();
	protected final int numberOfColumns;

	protected double[][] initialValues;

	protected int actualPosition = 0;

	protected DoubleTabularStreamIteratorByRecursion(final double[]... initialValues) {
		this.numberOfColumns = initialValues[0].length;
		this.initialValues = initialValues;
		inizializeCache();
	}

	@Override
	public double valueFromColumn(final int index) {
		if (numberOfDeliveredElements() == 0) {
			throw new IllegalStateException("next() has not been called");
		}
		return current()[index];
	}

	@Override
	public double mapUnary(final DoubleUnaryOperator operator) {
		throw new UnsupportedOperationException(); // TODO cardinality check if a Map is a source?
	}

	@Override
	public double mapBinary(final DoubleBinaryOperator operator) {
		throw new UnsupportedOperationException(); // TODO cardinality check if a Map is a source?
	}

	@Override
	public double[] current() {
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
	public double[] next() {
		if (actualPosition < cache.size()) {
			actualPosition++;
			return cache.getLast();
		}
		final var next = computeNextValue();
		cache.add(next);
		actualPosition++;
		return next;
	}

	protected abstract double[] computeNextValue();
}
