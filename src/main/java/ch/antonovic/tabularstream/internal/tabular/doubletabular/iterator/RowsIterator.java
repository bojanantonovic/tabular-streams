package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
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
	public double cachedValueFromColumn(final int index) {
		return table[index][actualPosition];
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
	public void incrementPositionWithoutReading() {
		moveCursorToNextPosition();
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
	public double[] current() {
		return extractRow(double[]::new);
	}

	@Override
	public double[] next() {
		final var current = current();
		incrementPositionWithoutReading();
		return current;
	}

	@Override
	public double mapUnary(final DoubleUnaryOperator operator) {
		if (numberOfColumns != 1) {
			throw new IllegalArgumentException("Stream has a different cardinality than 1: " + numberOfColumns);
		}
		final var rowValue = rowProxy().applyAsDouble(0);
		return operator.applyAsDouble(rowValue);
	}

	/**
	 * Calculates the result of applying a binary operator to two values from the stream.
	 *
	 * @param operator The binary operator to apply to the two values.
	 * @return The result of applying the binary operator.
	 * @throws IllegalArgumentException If the stream has a different cardinality than 2.
	 */
	@Override
	public double mapBinary(final DoubleBinaryOperator operator) {
		if (numberOfColumns != 2) {
			throw new IllegalArgumentException("Stream has a different cardinality than 2: " + numberOfColumns);
		}
		final var y1 = rowProxy().applyAsDouble(0);
		final var y2 = rowProxy().applyAsDouble(1);
		return operator.applyAsDouble(y1, y2);
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
