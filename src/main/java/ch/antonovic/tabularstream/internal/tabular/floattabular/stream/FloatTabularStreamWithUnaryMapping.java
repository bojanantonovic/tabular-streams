package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import ch.antonovic.tabularstream.FloatTabularStream;
import ch.antonovic.tabularstream.internal.tabular.floattabular.iterator.UnaryMappingIterator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

public class FloatTabularStreamWithUnaryMapping extends FloatTabularStreamWithSingleRowReducing {

	private final FloatUnaryOperator unaryOperator;

	public FloatTabularStreamWithUnaryMapping(final FloatTabularStream sourceStream, final FloatUnaryOperator unaryOperator) {
		super(sourceStream);
		this.unaryOperator = unaryOperator;
	}

	@Override
	public FloatTabularStreamIterator iterator() {
		return new UnaryMappingIterator(sourceStream.iterator(), unaryOperator);
	}
}
