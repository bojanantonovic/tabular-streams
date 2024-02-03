package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.ObjectTabularStream;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator.BinaryMappingIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.util.function.BinaryOperator;

public class ObjectTabularStreamWithBinaryMapping<T> extends ObjectTabularStreamWithSingleRowReducing<T> {

	private final BinaryOperator<T> binaryOperator;

	public ObjectTabularStreamWithBinaryMapping(final ObjectTabularStream<T> sourceStream, final BinaryOperator<T> binaryOperator) {
		super(sourceStream);
		this.binaryOperator = binaryOperator;
	}

	@Override
	public ObjectTabularStreamIterator<T> iterator() {
		return new BinaryMappingIterator<>(sourceStream.iterator(), sourceStream.getType(), binaryOperator);
	}
}
