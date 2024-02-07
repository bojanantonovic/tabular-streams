package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;

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
		return cache.get(actualPosition)[index];
	}

	@Override
	public FloatVector valueFromColumn(final int column, final VectorSpecies<Float> species) {
		throw new UnsupportedOperationException("No efficient native support possible.");
	}

	@Override
	public long numberOfDeliveredElements() {
		return actualPosition;
	}

	@Override
	public void reset() {
		cache.clear();
		inizializeCache();
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
	public boolean hasNext(final long stepWidth) {
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
	public void moveCursorToNextPosition(final long stepWidth) {
		for (var i = 0; i < stepWidth; i++) {
			moveCursorToNextPosition();
		}
	}

	@Override
	public float[] next() {
		final var result = cache.get(actualPosition);
		moveCursorToNextPosition();
		return result;
	}

	protected abstract float[] computeNextValue();
}
