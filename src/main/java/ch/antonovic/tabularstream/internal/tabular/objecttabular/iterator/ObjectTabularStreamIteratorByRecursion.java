package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ObjectTabularStreamIteratorByRecursion<T> implements ObjectTabularStreamIterator<T> {
	protected final List<T[]> cache = new ArrayList<>();
	protected final int numberOfColumns;

	protected final Class<T> type;
	protected T[][] initialValues;

	protected int actualPosition = 0;

	@SafeVarargs
	protected ObjectTabularStreamIteratorByRecursion(final Class<T> type, final T[]... initialValues) {
		this.numberOfColumns = initialValues[0].length;
		this.initialValues = initialValues;
		this.type = type;
		inizializeCache();
	}

	@Override
	public T valueFromColumn(final int index) {
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
	public T[] next() {
		final var result = cache.get(actualPosition);
		moveCursorToNextPosition();
		return result;
	}

	protected abstract T[] computeNextValue();
}
