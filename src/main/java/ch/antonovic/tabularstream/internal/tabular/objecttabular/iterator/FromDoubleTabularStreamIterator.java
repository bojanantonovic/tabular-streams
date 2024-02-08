package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.TabularStreamIterator;

import java.lang.reflect.Array;
import java.util.function.DoubleFunction;

public class FromDoubleTabularStreamIterator<T> extends AbstractToObjectTabularStreamIterator<double[]/*, FloatTabularStreamIterator*/, T> {
	private final DoubleFunction<T> doubleFunction;

	public FromDoubleTabularStreamIterator(final TabularStreamIterator<?> sourceIterator, final DoubleFunction<T> doubleFunction, final Class<T> type) {
		super(sourceIterator, type);
		this.doubleFunction = doubleFunction;
	}

	private DoubleTabularStreamIterator getSourceIterator() {
		return (DoubleTabularStreamIterator) sourceIterator;
	}

	@Override
	public T valueFromColumn(final int index) {
		return doubleFunction.apply(getSourceIterator().valueFromColumn(index));
	}

	@Override
	public T[] next() {
		final var next = getSourceIterator().next();
		final var result = (T[]) Array.newInstance(type, next.length);
		for (var i = 0; i < next.length; i++) {
			result[i] = doubleFunction.apply(next[i]);
		}
		return result;
	}
}
