package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.lang.reflect.Array;
import java.util.function.Function;

public class FromObjectTabularStreamIterator<U, T> extends AbstractToObjectTabularStreamIterator<U/*, ObjectTabularStreamIterator<U>*/, T> {
	private final Function<U, T> function;

	public FromObjectTabularStreamIterator(final ObjectTabularStreamIterator<U> sourceIterator, final Function<U, T> function, final Class<T> type) {
		super(sourceIterator, type);
		this.function = function;
	}

	private ObjectTabularStreamIterator<U> getSourceIterator() {
		return (ObjectTabularStreamIterator<U>) sourceIterator;
	}

	@Override
	public T valueFromColumn(final int index) {
		return function.apply(getSourceIterator().valueFromColumn(index));
	}

	@Override
	public T[] next() {
		final var next = getSourceIterator().next();
		final var result = (T[]) Array.newInstance(type, next.length);
		for (var i = 0; i < next.length; i++) {
			result[i] = function.apply(next[i]);
		}
		return result;
	}
}
