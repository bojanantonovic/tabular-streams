package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.util.function.UnaryOperator;

public class UnaryMappingAllFieldsIterator<T> extends ObjectTabularStreamIteratorWrapper<T> {

	private final UnaryOperator<T> operator;

	public UnaryMappingAllFieldsIterator(final ObjectTabularStreamIterator<T> parentIterator, final UnaryOperator<T> operator) {
		super(parentIterator);
		this.operator = operator;
	}

	@Override
	public T valueFromColumn(final int index) {
		return operator.apply(parentIterator.valueFromColumn(index));
	}

	@Override
	public T[] next() {
		final T[] next = parentIterator.next();
		for (var i = 0; i < next.length; i++) {
			next[i] = operator.apply(next[i]);
		}
		return next;
	}
}
