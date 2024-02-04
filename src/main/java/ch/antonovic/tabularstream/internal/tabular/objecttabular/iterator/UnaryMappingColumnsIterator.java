package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;
import org.springframework.lang.Nullable;

import java.util.function.UnaryOperator;

public class UnaryMappingColumnsIterator<T> extends ObjectTabularStreamIteratorWrapper<T> {

	private final UnaryOperator<T>[] operators;

	@Deprecated
	private @Nullable T[] current;

	public UnaryMappingColumnsIterator(final ObjectTabularStreamIterator<T> parentIterator, final UnaryOperator<T>[] operators) {
		super(parentIterator);
		this.operators = operators;
	}

	@Override
	public T[] current() {
		assert current != null;
		return current;
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
		current = next;
		return next;
	}
}
