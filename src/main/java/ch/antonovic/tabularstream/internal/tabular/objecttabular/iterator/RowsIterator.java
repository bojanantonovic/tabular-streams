package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.lang.reflect.Array;
import java.util.function.BinaryOperator;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;

public class RowsIterator<T> implements ObjectTabularStreamIterator<T> {

	private final T[][] table;

	private final int numberOfColumns;
	private final int numberOfRows;
	private final IntFunction<T[]> arrayCreator;

	private int actualPosition = 0;

	public RowsIterator(final T[][] table, final Class<T> type, final int numberOfColumns, final int numberOfRows) {
		this.table = table;
		this.numberOfColumns = numberOfColumns;
		this.numberOfRows = numberOfRows;
		this.arrayCreator = i -> (T[]) Array.newInstance(type, i);
	}

	@Override
	public T valueFromColumn(final int index) {
		return table[index][actualPosition];
	}

	@Override
	public boolean hasNext() {
		return actualPosition < numberOfRows;
	}

	@Override
	public void incrementPositionWithoutReading() {
		actualPosition++;
/*
if (actualPosition >= numberOfRows) {
	throw new NoSuchElementException();
}*/
	}

	@Override
	public void reset() {
		actualPosition = 0;
	}

	@Override
	public long numberOfDeliveredElements() {
		return actualPosition;
	}

	@Override
	public T[] current() {
		return extractRow(arrayCreator);
	}

	@Override
	public T[] next() {
		final var current = current();
		incrementPositionWithoutReading();
		return current;
	}

	@Override
	public T mapUnary(final UnaryOperator<T> operator) {
		if (numberOfColumns != 1) {
			throw new IllegalArgumentException("Stream has a different cardinality than 1: " + numberOfColumns);
		}
		final var rowValue = valueFromColumn(0);
		return operator.apply(rowValue);
	}

	@Override
	public T mapBinary(final BinaryOperator<T> operator) {
		if (numberOfColumns != 2) {
			throw new IllegalArgumentException("Stream has a different cardinality than 2: " + numberOfColumns);
		}
		final var y1 = valueFromColumn(0);
		final var y2 = valueFromColumn(1);
		return operator.apply(y1, y2);
	}

	public T[] extractRow(final IntFunction<T[]> rowGenerator) {
		final var row = rowGenerator.apply(numberOfColumns);

		for (var i = 0; i < numberOfColumns; i++) {
			row[i] = valueFromColumn(i);
		}
		return row;
	}
}
