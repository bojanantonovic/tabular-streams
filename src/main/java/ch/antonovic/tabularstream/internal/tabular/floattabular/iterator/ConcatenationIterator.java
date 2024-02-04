package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.TabularStream;
import ch.antonovic.tabularstream.internal.tabular.AbstractConcatenationIterator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

public class ConcatenationIterator extends AbstractConcatenationIterator<float[], FloatTabularStreamIterator> implements FloatTabularStreamIterator {

	public ConcatenationIterator(final TabularStream<float[], FloatTabularStreamIterator>[] streams) {
		super(streams);
	}

	@Override
	public float valueFromColumn(final int index) {
		return getCurrentStream().valueFromColumn(index);
	}
}
