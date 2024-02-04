package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

public class LengthLimitIterator<T> extends ObjectTabularStreamIteratorWrapper<T> {

	private final long limit;

	public LengthLimitIterator(final ObjectTabularStreamIterator<T> parentIterator, final long limit) {
		super(parentIterator);
		this.limit = limit;
	}

	@Override
	public boolean hasNext() {
		return parentIterator.hasNext() && (numberOfDeliveredElements() < limit);
	}
}
