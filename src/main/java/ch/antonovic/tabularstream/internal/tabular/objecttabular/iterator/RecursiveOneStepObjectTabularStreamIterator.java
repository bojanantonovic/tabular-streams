package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import java.lang.reflect.Array;
import java.util.function.UnaryOperator;

public class RecursiveOneStepObjectTabularStreamIterator<T> extends ObjectTabularStreamIteratorByRecursion<T> {

	private final UnaryOperator<T> unaryOperator;

	public RecursiveOneStepObjectTabularStreamIterator(final Class<T> type, final T[] first, final UnaryOperator<T> unaryOperator) {
		super(type, first);
		this.unaryOperator = unaryOperator;
	}

	@Override
	protected T[] computeNextValue() {
		final var next = (T[]) Array.newInstance(type, numberOfColumns);
		final var a = cache.getLast();
		for (var i = 0; i < numberOfColumns; i++) {
			next[i] = unaryOperator.apply(a[i]);
		}
		return next;
	}
}
