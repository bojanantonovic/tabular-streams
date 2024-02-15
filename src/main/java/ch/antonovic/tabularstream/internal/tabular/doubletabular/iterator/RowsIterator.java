package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.function.TernaryOperator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;
import jdk.incubator.vector.DoubleVector;
import jdk.incubator.vector.VectorSpecies;

import java.util.NoSuchElementException;
import java.util.function.BinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;
import java.util.function.UnaryOperator;

public class RowsIterator implements DoubleTabularStreamIterator {

	private final double[][] table;

	private final int numberOfColumns;
	private final int numberOfRows;
	private int actualPosition = 0;

	public RowsIterator(final double[][] table, final int numberOfRows) {
		this.table = table;
		this.numberOfColumns = table.length;
		this.numberOfRows = numberOfRows;
	}

	@Override
	public double valueFromColumn(final int index) {
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

	public void next(final double[] target) {
		if (target.length != numberOfColumns) {
			throw new IllegalArgumentException();
		}
		for (var i = 0; i < numberOfColumns; i++) {
			target[i] = valueFromColumn(i);
		}
	}

	public double[][] nextChunk(final int stepWidth) {
		final int remainingRows = numberOfRows - actualPosition;
		final int chunkSize = Math.min(stepWidth, remainingRows);
		final double[][] chunk = new double[numberOfColumns][chunkSize];
		for (int i = 0; i < numberOfColumns; i++) {
			System.arraycopy(table[i], actualPosition, chunk[i], 0, chunkSize);
		}
		actualPosition += chunkSize;
		return chunk;
	}

	@Override
	public DoubleVector valueFromColumn(final int column, final VectorSpecies<Double> species) {
		return (DoubleVector) species.fromArray(table[column], actualPosition);
	}

	public DoubleVector nextChunkWithUnaryMapping(final VectorSpecies<Double> species, final UnaryOperator<DoubleVector> unaryOperator) {
		return unaryOperator.apply(valueFromColumn(0, species));
	}

	public DoubleVector nextChunkWithBinaryMapping(final VectorSpecies<Double> species, final BinaryOperator<DoubleVector> binaryOperator) {
		return binaryOperator.apply( //
				valueFromColumn(0, species), //
				valueFromColumn(1, species));
	}

	public DoubleVector nextChunkWithTernaryMapping(final VectorSpecies<Double> species, final TernaryOperator<DoubleVector> ternaryOperator) {
		return ternaryOperator.apply( //
				valueFromColumn(0, species), //
				valueFromColumn(1, species), //
				valueFromColumn(2, species));
	}

	public void nextChunkWithUnaryMapping(final VectorSpecies<Double> species, final UnaryOperator<DoubleVector> unaryOperator, final double[] target) {
		final var doubleVector = nextChunkWithUnaryMapping(species, unaryOperator);
		storeSimdVector(doubleVector, target);
	}

	public void nextChunkWithBinaryMapping(final VectorSpecies<Double> species, final BinaryOperator<DoubleVector> binaryOperator, final double[] target) {
		final var doubleVector = nextChunkWithBinaryMapping(species, binaryOperator);
		storeSimdVector(doubleVector, target);
	}

	public void nextChunkWithTernaryMapping(final VectorSpecies<Double> species, final TernaryOperator<DoubleVector> ternaryOperator, final double[] target) {
		final var doubleVector = nextChunkWithTernaryMapping(species, ternaryOperator);
		storeSimdVector(doubleVector, target);
	}

	public void storeSimdVector(final DoubleVector vector, final double[] target) {
		vector.intoArray(target, actualPosition);
	}
}
