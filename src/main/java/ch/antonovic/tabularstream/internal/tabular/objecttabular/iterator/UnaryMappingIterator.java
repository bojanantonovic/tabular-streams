package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.lang.reflect.Array;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;

public class UnaryMappingIterator<T> extends ObjectTabularStreamIteratorWrapper<T> {
	private final UnaryOperator<T> unaryOperator;

	private final IntFunction<T[]> arrayCreator;

	public UnaryMappingIterator(final ObjectTabularStreamIterator<T> parentIterator, final Class<T> type, final UnaryOperator<T> unaryOperator) {
		super(parentIterator);
		this.unaryOperator = unaryOperator;
		this.arrayCreator = i -> (T[]) Array.newInstance(type, i);
	}

	@Override
	public T valueFromColumn(final int index) {
		return unaryOperator.apply(parentIterator.valueFromColumn(0));
	}

	@Override
	public T[] next() {
		final var nextValue = arrayCreator.apply(1);
		nextValue[0] = valueFromColumn(0);
		moveCursorToNextPosition();
		return nextValue;
	}
}
