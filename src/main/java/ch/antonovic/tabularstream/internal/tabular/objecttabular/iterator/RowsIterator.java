package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.lang.reflect.Array;
import java.util.function.IntFunction;

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
	public void moveCursorToNextPosition() {
		actualPosition++;
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
	public T[] next() {
		final var current = extractRow(arrayCreator);
		moveCursorToNextPosition();
		return current;
	}

	public T[] extractRow(final IntFunction<T[]> rowGenerator) {
		final var row = rowGenerator.apply(numberOfColumns);

		for (var i = 0; i < numberOfColumns; i++) {
			row[i] = valueFromColumn(i);
		}
		return row;
	}
}
