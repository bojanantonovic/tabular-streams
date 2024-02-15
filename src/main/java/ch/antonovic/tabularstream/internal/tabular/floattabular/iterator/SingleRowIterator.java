package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;

import java.util.NoSuchElementException;

public class SingleRowIterator implements FloatTabularStreamIterator {
	private final float[] row;

	private int actualPosition = 0;

	public SingleRowIterator(final float[] row) {
		this.row = row;
	}

	@Override
	public float valueFromColumn(final int index) {
		return row[index];
	}

	@Override
	public FloatVector valueFromColumn(final int column, final VectorSpecies<Float> species) {
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
	public float[] next() {
		actualPosition++;
		return row;
	}
}
