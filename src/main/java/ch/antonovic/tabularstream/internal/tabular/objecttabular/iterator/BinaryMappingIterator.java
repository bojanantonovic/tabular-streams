package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.lang.reflect.Array;
import java.util.function.BinaryOperator;
import java.util.function.IntFunction;

public class BinaryMappingIterator<T> extends ObjectTabularStreamIteratorWrapper<T> {
	private final BinaryOperator<T> binaryOperator;

	private final IntFunction<T[]> arrayCreator;

	private T currentValue;

	public BinaryMappingIterator(final ObjectTabularStreamIterator<T> parentIterator, final Class<T> type, final BinaryOperator<T> binaryOperator) {
		super(parentIterator);
		this.binaryOperator = binaryOperator;
		this.arrayCreator = i -> (T[]) Array.newInstance(type, i);
	}

	@Override
	public T[] next() {
		currentValue = binaryOperator.apply( //
				parentIterator.valueFromColumn(0), //
				parentIterator.valueFromColumn(1));
		final var nextValue = arrayCreator.apply(1);
		nextValue[0] = currentValue;
		incrementPositionWithoutReading();
		return nextValue;
	}

	@Override
	public T valueFromColumn(final int index) {
		if (index > 0) {
			throw new IllegalArgumentException();
		}
		return currentValue;
	}
}
