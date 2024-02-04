package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;
import org.springframework.lang.Nullable;

import java.util.function.Supplier;

public class InfinityIterator<T> implements ObjectTabularStreamIterator<T> {
	private final Supplier<T[]> supplier;

	private int actualPosition = 0;

	private @Nullable T[] cachedCurrentValue;

	public InfinityIterator(final Supplier<T[]> supplier) {
		this.supplier = supplier;
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
	public void moveCursorToNextPosition() {
		cachedCurrentValue = supplier.get();
		actualPosition++;
	}

	@Override
	public T valueFromColumn(final int index) {
		return cachedCurrentValue[index];
	}

	@Override
	public T[] next() {
		moveCursorToNextPosition();
		return cachedCurrentValue;
	}

	@Override
	public void reset() {
		actualPosition = 0;
		cachedCurrentValue = null;
	}
}
