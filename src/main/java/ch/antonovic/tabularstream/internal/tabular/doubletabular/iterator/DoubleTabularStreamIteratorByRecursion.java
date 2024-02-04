package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		return cache.get(actualPosition)[index];
	}

	@Override
	public long numberOfDeliveredElements() {
		return actualPosition;
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
	public void moveCursorToNextPosition() {
		actualPosition++;
		if (actualPosition >= cache.size()) {
			cache.add(computeNextValue());
		}
	}

	@Override
	public double[] next() {
		final var result = cache.get(actualPosition);
		moveCursorToNextPosition();
		return result;
	}

	protected abstract double[] computeNextValue();
}
