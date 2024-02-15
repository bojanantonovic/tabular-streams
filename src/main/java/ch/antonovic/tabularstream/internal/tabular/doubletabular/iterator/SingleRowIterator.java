package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;
import jdk.incubator.vector.DoubleVector;
import jdk.incubator.vector.VectorSpecies;

import java.util.NoSuchElementException;

public class SingleRowIterator implements DoubleTabularStreamIterator {
	private final double[] row;

	private int actualPosition = 0;

	public SingleRowIterator(final double[] row) {
		this.row = row;
	}

	@Override
	public double valueFromColumn(final int index) {
		return row[index];
	}

	@Override
	public DoubleVector valueFromColumn(final int column, final VectorSpecies<Double> species) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void moveCursorToNextPosition() {
		actualPosition++;
	}

	@Override
	public void moveCursorToNextPosition(final long stepWidth) {
		if (stepWidth > 1) {
			throw new IllegalArgumentException();
		}
		actualPosition++;
	}

	@Override
	public int skip(final int amount) {
		if (amount > 1) {
			throw new NoSuchElementException();
		}

		actualPosition++;

		return amount;
	}

	@Override
	public long numberOfDeliveredElements() {
		return actualPosition;
	}

	@Override
	public boolean hasNext() {
		return numberOfDeliveredElements() < 1;
	}

	@Override
	public boolean hasNext(final long stepWidth) {
		if (stepWidth > 1) {
			throw new IllegalArgumentException();
		}
		return numberOfDeliveredElements() < 1;
	}

	@Override
	public void reset() {
		actualPosition = 0;
	}

	@Override
	public double[] next() {
		actualPosition++;
		return row;
	}
}
