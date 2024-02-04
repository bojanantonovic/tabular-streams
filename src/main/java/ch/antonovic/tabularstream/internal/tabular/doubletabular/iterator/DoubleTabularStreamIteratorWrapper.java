package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

public class DoubleTabularStreamIteratorWrapper implements DoubleTabularStreamIterator {
	protected final DoubleTabularStreamIterator parentIterator;

	public DoubleTabularStreamIteratorWrapper(final DoubleTabularStreamIterator parentIterator) {
		this.parentIterator = parentIterator;
	}

	@Override
	public double valueFromColumn(final int index) {
		return parentIterator.valueFromColumn(index);
	}

	@Override
	public boolean hasNext() {
		return parentIterator.hasNext();
	}

	@Override
	public double[] next() {
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
