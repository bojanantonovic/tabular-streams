package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.function.FloatFunction;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.lang.reflect.Array;

public class FromFloatTabularStreamIterator<T> implements ObjectTabularStreamIterator<T> {
	private final FloatTabularStreamIterator sourceIterator;
	private final FloatFunction<T> floatFunction;

	private final Class<T> type;

	public FromFloatTabularStreamIterator(final FloatTabularStreamIterator sourceIterator, final FloatFunction<T> floatFunction, final Class<T> type) {
		this.sourceIterator = sourceIterator;
		this.floatFunction = floatFunction;
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
		return floatFunction.apply(sourceIterator.valueFromColumn(index));
	}

	@Override
	public T[] next() {
		final var next = sourceIterator.next();
		final var result = (T[]) Array.newInstance(type, next.length);
		for (var i = 0; i < next.length; i++) {
			result[i] = floatFunction.apply(next[i]);
		}
		return result;
	}
}
