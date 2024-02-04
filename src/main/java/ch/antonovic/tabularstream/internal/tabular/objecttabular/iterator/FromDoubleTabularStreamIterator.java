package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.lang.reflect.Array;
import java.util.function.DoubleFunction;

public class FromDoubleTabularStreamIterator<T> implements ObjectTabularStreamIterator<T> {
	private final DoubleTabularStreamIterator sourceIterator;
	private final DoubleFunction<T> doubleFunction;

	private final Class<T> type;

	public FromDoubleTabularStreamIterator(final DoubleTabularStreamIterator sourceIterator, final DoubleFunction<T> doubleFunction, final Class<T> type) {
		this.sourceIterator = sourceIterator;
		this.doubleFunction = doubleFunction;
		this.type = type;
	}

	@Override
	public void moveCursorToNextPosition() {
		sourceIterator.moveCursorToNextPosition();
	}

	@Override
	public long numberOfDeliveredElements() {
		return sourceIterator.numberOfDeliveredElements();
	}

	@Override
	public void reset() {
		sourceIterator.reset();
	}

	@Override
	public boolean hasNext() {
		return sourceIterator.hasNext();
	}

	@Override
	public T valueFromColumn(final int index) {
		return doubleFunction.apply(sourceIterator.valueFromColumn(index));
	}

	@Override
	public T[] next() {
		final var next = sourceIterator.next();
		final var result = (T[]) Array.newInstance(type, next.length);
		for (var i = 0; i < next.length; i++) {
			result[i] = doubleFunction.apply(next[i]);
		}
		return result;
	}
}
