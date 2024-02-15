package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.FloatTabularStream;
import ch.antonovic.tabularstream.internal.tabular.floattabular.iterator.SkippingIterator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

public class FloatTabularStreamWithSkipping extends FloatTabularStreamWrapper {

	private final int amount;

	public FloatTabularStreamWithSkipping(FloatTabularStream parent, int amount) {
		super(parent);
		this.amount = amount;
	}

	@Override
	public FloatTabularStreamIterator iterator() {
		return new SkippingIterator(parent.iterator(), amount);
	}
}
