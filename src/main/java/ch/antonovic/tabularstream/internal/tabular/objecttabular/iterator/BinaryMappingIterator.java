package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.lang.reflect.Array;
import java.util.function.BinaryOperator;
import java.util.function.IntFunction;

public class BinaryMappingIterator<T> extends ObjectTabularStreamIteratorWrapper<T> {
	private final BinaryOperator<T> binaryOperator;

	private final IntFunction<T[]> arrayCreator;

	public BinaryMappingIterator(final ObjectTabularStreamIterator<T> parentIterator, final Class<T> type, final BinaryOperator<T> binaryOperator) {
		super(parentIterator);
		this.binaryOperator = binaryOperator;
		this.arrayCreator = i -> (T[]) Array.newInstance(type, i);
	}

	@Override
	public T valueFromColumn(final int index) {
		return binaryOperator.apply( //
				parentIterator.valueFromColumn(0), //
				parentIterator.valueFromColumn(1));
	}

	@Override
	public T[] next() {
		final var nextValue = arrayCreator.apply(1);
		nextValue[0] = valueFromColumn(0);
		moveCursorToNextPosition();
		return nextValue;
	}
}
