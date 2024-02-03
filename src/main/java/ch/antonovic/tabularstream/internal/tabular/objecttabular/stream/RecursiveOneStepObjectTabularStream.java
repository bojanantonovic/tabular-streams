package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator.RecursiveOneStepObjectTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.util.function.UnaryOperator;

public class RecursiveOneStepObjectTabularStream<T> extends ObjectTabularStreamByRecursion<T> {
	private final T[] first;

	private final UnaryOperator<T> unaryOperator;

	public RecursiveOneStepObjectTabularStream(final T[] first, final UnaryOperator<T> unaryOperator) {
		super(first.length, (Class<T>) first.getClass().componentType());
		this.first = first;
		this.unaryOperator = unaryOperator;
	}

	@Override
	public ObjectTabularStreamIterator<T> iterator() {
		return new RecursiveOneStepObjectTabularStreamIterator<>(type, first, unaryOperator);
	}
}
