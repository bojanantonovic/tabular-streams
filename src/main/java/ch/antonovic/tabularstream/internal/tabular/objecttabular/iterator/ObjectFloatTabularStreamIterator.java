package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.function.FloatFunction;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

import java.lang.reflect.Array;

public class ObjectFloatTabularStreamIterator<T> extends AbstractObjectTabularStreamIterator<T> {
	private final FloatTabularStreamIterator sourceIterator;
	private final FloatFunction<T> floatFunction;

	private final Class<T> type;

	public ObjectFloatTabularStreamIterator(final FloatTabularStreamIterator sourceIterator, final FloatFunction<T> floatFunction, final Class<T> type) {
		this.sourceIterator = sourceIterator;
		this.floatFunction = floatFunction;
		this.type = type;
	}

	@Override
	public void moveCursorToNextPosition() {
		sourceIterator.moveCursorToNextPosition();
	}

	@Override
	public void moveCursorToNextPosition(final long stepWidth) {
		sourceIterator.moveCursorToNextPosition(stepWidth);
	}

	@Override
	public long numberOfDeliveredElements() {
		return sourceIterator.numberOfDeliveredElements();
	}

	@Override
	public T valueFromColumn(final int index) {
		return floatFunction.apply(sourceIterator.valueFromColumn(index));
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
	public boolean hasNext(final long stepWidth) {
		return sourceIterator.hasNext(stepWidth);
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
