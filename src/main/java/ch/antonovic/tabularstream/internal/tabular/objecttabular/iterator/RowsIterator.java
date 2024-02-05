package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import java.lang.reflect.Array;
import java.util.function.IntFunction;

public class RowsIterator<T> extends AbstractObjectTabularStreamIterator<T> {

	private final T[][] table;

	private final int numberOfColumns;
	private final int numberOfRows;
	private final IntFunction<T[]> arrayCreator;

	private int actualPosition = 0;

	public RowsIterator(final T[][] table, final Class<T> type, final int numberOfRows) {
		this.table = table;
		this.numberOfColumns = table.length;
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
		next(row);
		return row;
	}
}
