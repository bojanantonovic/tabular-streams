package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

public class LengthLimitIterator extends FloatTabularStreamIteratorWrapper {

	private final long limit;

	public LengthLimitIterator(final FloatTabularStreamIterator parentIterator, final long limit) {
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
