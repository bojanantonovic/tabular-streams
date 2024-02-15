package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleTabularStream;
import ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator.SkippingIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

public class DoubleTabularStreamWithSkipping extends DoubleTabularStreamWrapper {

	private final int amount;

	public DoubleTabularStreamWithSkipping(DoubleTabularStream parent, int amount) {
		super(parent);
		this.amount = amount;
	}

	@Override
	public DoubleTabularStreamIterator iterator() {
		return new SkippingIterator(parent.iterator(), amount);
	}
}
