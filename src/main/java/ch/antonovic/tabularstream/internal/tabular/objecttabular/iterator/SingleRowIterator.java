package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.util.NoSuchElementException;

public class SingleRowIterator<T> implements ObjectTabularStreamIterator<T> {
	private final T[] row;

	private int actualPosition = 0;

	public SingleRowIterator(final T[] row) {
		this.row = row;
	}

	@Override
	public T valueFromColumn(final int index) {
		return row[index];
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
	public T[] next() {
		actualPosition++;
		return row;
	}

	@Override
	public void next(final T[] target) {
		System.arraycopy(row, 0, target, 0, target.length);
	}
}
