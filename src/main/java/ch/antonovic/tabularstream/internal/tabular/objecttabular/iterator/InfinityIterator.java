package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;
import org.springframework.lang.Nullable;

import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class InfinityIterator<T> implements ObjectTabularStreamIterator<T> {
	private final Supplier<T[]> supplier;

	private int actualPosition = 0;

	private @Nullable T[] currentValue;

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
		assert currentValue != null;
		return currentValue;
	}

	@Override
	public T valueFromColumn(final int index) {
		return currentValue[index];
	}

	@Override
	public T[] next() {
		incrementPositionWithoutReading();
		currentValue = supplier.get();
		return current();
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
		currentValue = null;
	}
}
