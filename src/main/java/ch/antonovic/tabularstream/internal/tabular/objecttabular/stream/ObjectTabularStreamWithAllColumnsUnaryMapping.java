package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.ObjectTabularStream;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator.UnaryMappingColumnsIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.util.function.UnaryOperator;

public class ObjectTabularStreamWithAllColumnsUnaryMapping<T> extends ObjectTabularStreamWrapper<T> {
	private final UnaryOperator<T>[] operators;

	public ObjectTabularStreamWithAllColumnsUnaryMapping(final ObjectTabularStream<T> parent, final UnaryOperator<T>[] operators) {
		super(parent);
		this.operators = operators;
	}

	@Override
	public ObjectTabularStreamIterator<T> iterator() {
		return new UnaryMappingColumnsIterator<>(parent.iterator(), operators);
	}
}
