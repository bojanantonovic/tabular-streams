package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.function.LoaderWithOffset;
import ch.antonovic.tabularstream.function.StoreWithOffset;
import ch.antonovic.tabularstream.function.TernaryOperator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;

import java.util.NoSuchElementException;
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
	public boolean hasNext(final long stepWidth) {
		return actualPosition + stepWidth <= numberOfRows;
	}

	@Override
	public void moveCursorToNextPosition() {
		actualPosition++;
	}

	@Override
	public void moveCursorToNextPosition(final long stepWidth) {
		actualPosition += (int) stepWidth;
	}

	@Override
	public int skip(final int amount) {
		if (actualPosition + amount >= numberOfRows) {
			throw new NoSuchElementException();
		}
		actualPosition += amount;

		return amount;
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

	public void next(final float[] target) {
		if (target.length != numberOfColumns) {
			throw new IllegalArgumentException();
		}
		for (var i = 0; i < numberOfColumns; i++) {
			target[i] = valueFromColumn(i);
		}
	}

	public float[][] nextChunk(final int stepWidth) {
		final int remainingRows = numberOfRows - actualPosition;
		final int chunkSize = Math.min(stepWidth, remainingRows);
		final float[][] chunk = new float[numberOfColumns][chunkSize];
		for (int i = 0; i < numberOfColumns; i++) {
			System.arraycopy(table[i], actualPosition, chunk[i], 0, chunkSize);
		}
		actualPosition += chunkSize;
		return chunk;
	}

	@Override
	public FloatVector valueFromColumn(final int column, final VectorSpecies<Float> species) {
		return (FloatVector) species.fromArray(table[column], actualPosition);
	}

	public FloatVector nextChunkWithUnaryMapping(final VectorSpecies<Float> species, final UnaryOperator<FloatVector> unaryOperator) {
		return unaryOperator.apply(valueFromColumn(0, species));
	}

	public FloatVector nextChunkWithBinaryMapping(final VectorSpecies<Float> species, final BinaryOperator<FloatVector> binaryOperator) {
		return binaryOperator.apply( //
				valueFromColumn(0, species), //
				valueFromColumn(1, species));
	}

	public FloatVector nextChunkWithTernaryMapping(final VectorSpecies<Float> species, final TernaryOperator<FloatVector> ternaryOperator) {
		return ternaryOperator.apply( //
				valueFromColumn(0, species), //
				valueFromColumn(1, species), //
				valueFromColumn(2, species));
	}

	public void nextChunkWithUnaryMapping(final VectorSpecies<Float> species, final UnaryOperator<FloatVector> unaryOperator, final float[] target) {
		final var doubleVector = nextChunkWithUnaryMapping(species, unaryOperator);
		storeSimdVector(doubleVector, target);
	}

	public void nextChunkWithBinaryMapping(final VectorSpecies<Float> species, final BinaryOperator<FloatVector> binaryOperator, final float[] target) {
		final var doubleVector = nextChunkWithBinaryMapping(species, binaryOperator);
		storeSimdVector(doubleVector, target);
	}

	public void nextChunkWithTernaryMapping(final VectorSpecies<Float> species, final TernaryOperator<FloatVector> ternaryOperator, final float[] target) {
		final var doubleVector = nextChunkWithTernaryMapping(species, ternaryOperator);
		storeSimdVector(doubleVector, target);
	}

	public void storeSimdVector(final FloatVector vector, final float[] target) {
		vector.intoArray(target, actualPosition);
	}
/*
	public void nextByOffset(final float[] target, final int offset) {
		target[offset] = valueFromColumn(0);
	}*/

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
