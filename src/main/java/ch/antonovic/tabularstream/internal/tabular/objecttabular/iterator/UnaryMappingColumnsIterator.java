package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.util.function.UnaryOperator;

public class UnaryMappingColumnsIterator<T> extends ObjectTabularStreamIteratorWrapper<T> {

	private final UnaryOperator<T>[] operators;

	public UnaryMappingColumnsIterator(final ObjectTabularStreamIterator<T> parentIterator, final UnaryOperator<T>[] operators) {
		super(parentIterator);
		this.operators = operators;
	}

	@Override
	public T valueFromColumn(final int index) {
		return operators[index].apply(parentIterator.valueFromColumn(index));
	}

	@Override
	public T[] next() {
		final T[] next = parentIterator.next();
		for (var i = 0; i < next.length; i++) {
			next[i] = operators[i].apply(next[i]);
		}
		return next;
	}
}
