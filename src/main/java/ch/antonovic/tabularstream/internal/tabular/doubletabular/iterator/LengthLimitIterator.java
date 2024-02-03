package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

public class LengthLimitIterator extends DoubleTabularStreamIteratorWrapper {

	private final long limit;

	public LengthLimitIterator(final DoubleTabularStreamIterator parentIterator, final long limit) {
		super(parentIterator);
		this.limit = limit;
	}

	@Override
	public boolean hasNext() {
	/*	if (numberOfDeliveredElements() >= limit) {
			return false;
		}*/

		return parentIterator.hasNext() && (numberOfDeliveredElements() < limit);
	}
}
