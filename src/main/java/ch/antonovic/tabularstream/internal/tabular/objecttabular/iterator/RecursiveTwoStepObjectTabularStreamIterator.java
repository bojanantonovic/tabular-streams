package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import java.lang.reflect.Array;
import java.util.function.BinaryOperator;

public class RecursiveTwoStepObjectTabularStreamIterator<T> extends ObjectTabularStreamIteratorByRecursion<T> {

	private final BinaryOperator<T> binaryOperator;

	public RecursiveTwoStepObjectTabularStreamIterator(final Class<T> type, final T[] first, final T[] second, final BinaryOperator<T> binaryOperator) {
		super(type, first, second);
		this.binaryOperator = binaryOperator;
	}

	@Override
	protected T[] computeNextValue() {
		final var next = (T[]) Array.newInstance(type, numberOfColumns);
		final var a = cache.get(cache.size() - 2);
		final var b = cache.getLast();
		for (var i = 0; i < numberOfColumns; i++) {
			next[i] = binaryOperator.apply(a[i], b[i]);
		}
		return next;
	}
}
