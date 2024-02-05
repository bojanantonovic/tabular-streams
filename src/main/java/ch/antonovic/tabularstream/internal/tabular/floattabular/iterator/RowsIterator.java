package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.function.LoaderWithOffset;
import ch.antonovic.tabularstream.function.StoreWithOffset;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

import java.util.function.BinaryOperator;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;

public class RowsIterator implements FloatTabularStreamIterator {

	private final float[][] table;

	private final int numberOfColumns;
	private final int numberOfRows;
	private int actualPosition = 0;

	public RowsIterator(final float[][] table, final int numberOfRows) {
		this.table = table;
		this.numberOfColumns = table.length;
		this.numberOfRows = numberOfRows;
	}

	@Override
	public float valueFromColumn(final int index) {
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
	public float[] next() {
		final var current = extractRow(float[]::new);
		moveCursorToNextPosition();
		return current;
	}

	// TODO
	public <R> float[] loadAndMapUnary(final LoaderWithOffset<float[], R> loader, final UnaryOperator<R> unaryOperator, final StoreWithOffset<R, float[]> storeWithOffset, final int stepWidth) {
		if (numberOfColumns != 1) {
			throw new IllegalArgumentException("Stream has a different cardinality than 1: " + numberOfColumns);
		}
		final var a1 = loader.load(table[0], actualPosition);
		final var c1 = unaryOperator.apply(a1);
		final var result = new float[stepWidth];
		storeWithOffset.store(c1, result, actualPosition);
		return result;
	}

	// TODO
	public <R> float[] loadAndMapBinary(final LoaderWithOffset<float[], R> loader, final BinaryOperator<R> binaryOperator, final StoreWithOffset<R, float[]> storeWithOffset, final int stepWidth) {
		if (numberOfColumns != 2) {
			throw new IllegalArgumentException("Stream has a different cardinality than 2: " + numberOfColumns);
		}
		final var a1 = loader.load(table[0], actualPosition);
		final var b1 = loader.load(table[1], actualPosition);
		final var c1 = binaryOperator.apply(a1, b1);
		final var result = new float[stepWidth];
		storeWithOffset.store(c1, result, actualPosition);
		return result;
	}

	public float[] extractRow(final IntFunction<float[]> rowGenerator) {
		final var row = rowGenerator.apply(numberOfColumns);

		for (var i = 0; i < numberOfColumns; i++) {
			row[i] = valueFromColumn(i);
		}
		return row;
	}
}
