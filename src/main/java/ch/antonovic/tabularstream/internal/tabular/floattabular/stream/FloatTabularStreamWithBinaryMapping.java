package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.function.FloatBinaryOperator;
import ch.antonovic.tabularstream.FloatTabularStream;
import ch.antonovic.tabularstream.internal.tabular.floattabular.iterator.BinaryMappingIterator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

public class FloatTabularStreamWithBinaryMapping extends FloatTabularStreamWithSingleRowReducing {

	private final FloatBinaryOperator binaryOperator;

	public FloatTabularStreamWithBinaryMapping(final FloatTabularStream sourceStream, final FloatBinaryOperator binaryOperator) {
		super(sourceStream);
		this.binaryOperator = binaryOperator;
	}

	@Override
	public FloatTabularStreamIterator iterator() {
		return new BinaryMappingIterator(sourceStream.iterator(), binaryOperator);
	}
}
