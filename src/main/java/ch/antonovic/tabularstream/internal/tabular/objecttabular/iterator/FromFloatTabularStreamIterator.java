package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.function.FloatFunction;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.TabularStreamIterator;

import java.lang.reflect.Array;

public class FromFloatTabularStreamIterator<T> extends AbstractToObjectTabularStreamIterator<float[]/*, FloatTabularStreamIterator*/, T> {
	private final FloatFunction<T> floatFunction;

	public FromFloatTabularStreamIterator(final TabularStreamIterator<?> sourceIterator, final FloatFunction<T> floatFunction, final Class<T> type) {
		super(sourceIterator, type);
		this.floatFunction = floatFunction;
	}

	private FloatTabularStreamIterator getSourceIterator() {
		return (FloatTabularStreamIterator) sourceIterator;
	}

	@Override
	public T valueFromColumn(final int index) {
		return floatFunction.apply(getSourceIterator().valueFromColumn(index));
	}

	@Override
	public T[] next() {
		final var next = getSourceIterator().next();
		final var result = (T[]) Array.newInstance(type, next.length);
		for (var i = 0; i < next.length; i++) {
			result[i] = floatFunction.apply(next[i]);
		}
		return result;
	}
}
