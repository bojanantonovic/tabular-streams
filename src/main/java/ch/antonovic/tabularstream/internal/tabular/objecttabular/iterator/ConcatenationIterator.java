package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.TabularStream;
import ch.antonovic.tabularstream.internal.tabular.AbstractConcatenationIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

public class ConcatenationIterator<T> extends AbstractConcatenationIterator<T[], ObjectTabularStreamIterator<T>> implements ObjectTabularStreamIterator<T> {

	public ConcatenationIterator(final TabularStream<T[], ObjectTabularStreamIterator<T>>[] streams) {
		super(streams);
	}

	@Override
	public T valueFromColumn(final int index) {
		return getCurrentStream().valueFromColumn(index);
	}
}
