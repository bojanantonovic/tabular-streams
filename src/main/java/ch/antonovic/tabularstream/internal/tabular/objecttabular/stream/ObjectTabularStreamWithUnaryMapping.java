package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.ObjectTabularStream;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator.UnaryMappingIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.util.function.UnaryOperator;

public class ObjectTabularStreamWithUnaryMapping<T> extends ObjectTabularStreamWithSingleRowReducing<T> {

	private final UnaryOperator<T> unaryOperator;

	public ObjectTabularStreamWithUnaryMapping(final ObjectTabularStream<T> sourceStream, final UnaryOperator<T> unaryOperator) {
		super(sourceStream);
		this.unaryOperator = unaryOperator;
	}

	@Override
	public ObjectTabularStreamIterator<T> iterator() {
		return new UnaryMappingIterator<>(sourceStream.iterator(), sourceStream.getType(), unaryOperator);
	}
}
