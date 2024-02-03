package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.ObjectTabularStream;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator.UnaryMappingAllFieldsIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.util.function.UnaryOperator;

public class ObjectTabularStreamWithAllValuesUnaryMapping<T> extends ObjectTabularStreamWrapper<T> {
	private final UnaryOperator<T> operator;

	public ObjectTabularStreamWithAllValuesUnaryMapping(final ObjectTabularStream<T> parent, final UnaryOperator<T> operator) {
		super(parent);
		this.operator = operator;
	}

	@Override
	public ObjectTabularStreamIterator<T> iterator() {
		return new UnaryMappingAllFieldsIterator(parent.iterator(), operator);
	}
}
