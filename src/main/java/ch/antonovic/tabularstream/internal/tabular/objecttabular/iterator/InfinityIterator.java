package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;
import org.springframework.lang.Nullable;

import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class InfinityIterator<T> implements ObjectTabularStreamIterator<T> {
	private final Supplier<T[]> supplier;

	private int actualPosition = 0;

	private @Nullable T[] cachedCurrentValue;

	public InfinityIterator(final Supplier<T[]> supplier) {
		this.supplier = supplier;
	}

	@Override
	public void incrementPositionWithoutReading() {
		actualPosition++;
	}

	@Override
	public long numberOfDeliveredElements() {
		return actualPosition;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public T[] current() {
		if (actualPosition == 0) {
			throw new IllegalStateException("next() has not been called");
		}
		assert cachedCurrentValue != null;
		return cachedCurrentValue;
	}

	@Override
	public void moveCursorToNextPosition() {
		cachedCurrentValue = supplier.get();
		actualPosition++;
	}

	@Override
	public T valueFromColumn(final int index) {
		return cachedCurrentValue[index];
	}

	@Override
	public T cachedValueFromColumn(final int index) {
		return cachedCurrentValue[index];
	}

	@Override
	public T[] next() {
		moveCursorToNextPosition();
		return cachedCurrentValue;
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
	public void reset() {
		actualPosition = 0;
		cachedCurrentValue = null;
	}
}
