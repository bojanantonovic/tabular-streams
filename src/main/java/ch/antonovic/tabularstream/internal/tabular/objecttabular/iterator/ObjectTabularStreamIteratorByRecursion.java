package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

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
		return current()[index];
	}

	@Override
	public T mapUnary(final UnaryOperator<T> operator) {
		throw new UnsupportedOperationException(); // TODO cardinality check if a Map is a source?
	}

	@Override
	public T mapBinary(final BinaryOperator<T> operator) {
		throw new UnsupportedOperationException(); // TODO cardinality check if a Map is a source?
	}

	@Override
	public T[] current() {
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
	public T[] next() {
		if (actualPosition < cache.size()) {
			actualPosition++;
			return cache.getLast();
		}
		final var next = computeNextValue();
		cache.add(next);
		actualPosition++;
		return next;
	}

	protected abstract T[] computeNextValue();
}
