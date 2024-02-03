package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleTabularStream;
import ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator.BinaryMappingIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.function.DoubleBinaryOperator;

public class DoubleTabularStreamWithBinaryMapping extends DoubleTabularStreamWithSingleRowReducing {

	private final DoubleBinaryOperator binaryOperator;

	public DoubleTabularStreamWithBinaryMapping(final DoubleTabularStream sourceStream, final DoubleBinaryOperator binaryOperator) {
		super(sourceStream);
		this.binaryOperator = binaryOperator;
	}

	@Override
	public DoubleTabularStreamIterator iterator() {
		return new BinaryMappingIterator(sourceStream.iterator(), binaryOperator);
	}
}
