package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.function.TernaryOperator;

import java.lang.reflect.Array;

public class RecursiveThreeStepObjectTabularStreamIterator<T> extends ObjectTabularStreamIteratorByRecursion<T> {

	private final TernaryOperator<T> ternaryOperator;

	public RecursiveThreeStepObjectTabularStreamIterator(final Class<T> type, final T[] first, final T[] second, final T[] third, final TernaryOperator<T> ternaryOperator) {
		super(type, first, second, third);
		this.ternaryOperator = ternaryOperator;
	}

	@Override
	protected T[] computeNextValue() {
		final var next = (T[]) Array.newInstance(type, numberOfColumns);
		final var a = cache.get(cache.size() - 3);
		final var b = cache.get(cache.size() - 2);
		final var c = cache.getLast();
		for (var i = 0; i < numberOfColumns; i++) {
			next[i] = ternaryOperator.apply(a[i], b[i], c[i]);
		}
		return next;
	}
}
