package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.ObjectTabularStream;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator.SkippingIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

public class ObjectTabularStreamWithSkipping<T> extends ObjectTabularStreamWrapper<T> {

	private final int amount;

	public ObjectTabularStreamWithSkipping(ObjectTabularStream<T> parent, int amount) {
		super(parent);
		this.amount = amount;
	}

	@Override
	public ObjectTabularStreamIterator<T> iterator() {
		return new SkippingIterator<>(parent.iterator(), amount);
	}
}
