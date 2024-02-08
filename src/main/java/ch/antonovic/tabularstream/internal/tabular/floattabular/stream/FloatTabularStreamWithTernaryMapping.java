package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.FloatTabularStream;
import ch.antonovic.tabularstream.function.FloatTernaryOperator;
import ch.antonovic.tabularstream.internal.tabular.floattabular.iterator.TernaryMappingIterator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

public class FloatTabularStreamWithTernaryMapping extends FloatTabularStreamWithSingleRowReducing {

	private final FloatTernaryOperator ternaryOperator;

	public FloatTabularStreamWithTernaryMapping(final FloatTabularStream sourceStream, final FloatTernaryOperator ternaryOperator) {
		super(sourceStream);
		this.ternaryOperator = ternaryOperator;
	}

	@Override
	public FloatTabularStreamIterator iterator() {
		return new TernaryMappingIterator(sourceStream.iterator(), ternaryOperator);
	}
}
