package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;

public class RowsIterator implements DoubleTabularStreamIterator {

	private final double[][] table;

	private final int numberOfColumns;
	private final int numberOfRows;
	private int actualPosition = 0;

	public RowsIterator(final double[][] table, final int numberOfColumns, final int numberOfRows) {
		this.table = table;
		this.numberOfColumns = numberOfColumns;
		this.numberOfRows = numberOfRows;
	}

	@Override
	public boolean hasNext() {
		return actualPosition < numberOfRows;
	}

	@Override
	public double valueFromColumn(final int index) {
		return table[index][actualPosition];
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
	public double[] next() {
		final var current = extractRow(double[]::new);
		moveCursorToNextPosition();
		return current;
	}

	public IntToDoubleFunction rowProxy() {
		return i -> table[i][actualPosition];
	}

	public double[] extractRow(final IntFunction<double[]> rowGenerator) {
		final var row = rowGenerator.apply(numberOfColumns);

		for (var i = 0; i < numberOfColumns; i++) {
			row[i] = rowProxy().applyAsDouble(i);
		}
		return row;
	}
}
