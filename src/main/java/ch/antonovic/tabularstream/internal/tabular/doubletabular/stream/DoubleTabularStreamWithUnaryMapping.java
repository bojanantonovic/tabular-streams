package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleTabularStream;
import ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator.UnaryMappingIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.function.DoubleUnaryOperator;

public class DoubleTabularStreamWithUnaryMapping extends DoubleTabularStreamWithSingleRowReducing {

	private final DoubleUnaryOperator unaryOperator;

	public DoubleTabularStreamWithUnaryMapping(final DoubleTabularStream sourceStream, final DoubleUnaryOperator unaryOperator) {
		super(sourceStream);
		this.unaryOperator = unaryOperator;
	}

	@Override
	public DoubleTabularStreamIterator iterator() {
		return new UnaryMappingIterator(sourceStream.iterator(), unaryOperator);
	}
}
