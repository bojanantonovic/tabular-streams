package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

public class SkippingIterator extends DoubleTabularStreamIteratorWrapper {

	private int actualPosition = 0;

	public SkippingIterator(final DoubleTabularStreamIterator parentIterator, final int amount) {
		super(parentIterator);
		parentIterator.skip(amount);
	}

	@Override
	public void reset() {
		super.reset();
		actualPosition = 0;
	}

	@Override
	public long numberOfDeliveredElements() {
		return actualPosition;
	}

	@Override
	public void moveCursorToNextPosition() {
		super.moveCursorToNextPosition();
		actualPosition++;
	}

	@Override
	public void moveCursorToNextPosition(final long stepWidth) {
		super.moveCursorToNextPosition(stepWidth);
		actualPosition += (int) stepWidth;
	}
}
