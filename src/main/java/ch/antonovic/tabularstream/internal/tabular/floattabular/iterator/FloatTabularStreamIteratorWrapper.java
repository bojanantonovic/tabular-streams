package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

public class FloatTabularStreamIteratorWrapper implements FloatTabularStreamIterator {
	protected final FloatTabularStreamIterator parentIterator;

	public FloatTabularStreamIteratorWrapper(final FloatTabularStreamIterator parentIterator) {
		this.parentIterator = parentIterator;
	}

	@Override
	public float valueFromColumn(final int index) {
		return parentIterator.valueFromColumn(index);
	}

	@Override
	public boolean hasNext() {
		return parentIterator.hasNext();
	}

	@Override
	public float[] next() {
		return parentIterator.next();
	}

	@Override
	public void reset() {
		parentIterator.reset();
	}

	@Override
	public long numberOfDeliveredElements() {
		return parentIterator.numberOfDeliveredElements();
	}

	@Override
	public void moveCursorToNextPosition() {
		parentIterator.moveCursorToNextPosition();
	}
}
