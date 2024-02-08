package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleTabularStream;
import ch.antonovic.tabularstream.function.DoubleTernaryOperator;
import ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator.TernaryMappingIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

public class DoubleTabularStreamWithTernaryMapping extends DoubleTabularStreamWithSingleRowReducing {

	private final DoubleTernaryOperator ternaryOperator;

	public DoubleTabularStreamWithTernaryMapping(final DoubleTabularStream sourceStream, final DoubleTernaryOperator ternaryOperator) {
		super(sourceStream);
		this.ternaryOperator = ternaryOperator;
	}

	@Override
	public DoubleTabularStreamIterator iterator() {
		return new TernaryMappingIterator(sourceStream.iterator(), ternaryOperator);
	}
}
