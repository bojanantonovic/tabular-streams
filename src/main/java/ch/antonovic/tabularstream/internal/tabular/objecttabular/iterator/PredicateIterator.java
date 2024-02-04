package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.internal.tabular.ComposablePredicateIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.util.function.Predicate;

public class PredicateIterator<T> extends ObjectTabularStreamIteratorWrapper<T> {
	private final ComposablePredicateIterator<T[], ObjectTabularStreamIterator<T>> composablePredicateIterator;

	public PredicateIterator(final ObjectTabularStreamIterator<T> parentIterator, final Predicate<T[]> predicate) {
		super(parentIterator);
		this.composablePredicateIterator = new ComposablePredicateIterator<>(parentIterator, predicate);
	}

	@Override
	public long numberOfDeliveredElements() {
		return composablePredicateIterator.numberOfDeliveredElements();
	}

	@Override
	public void moveCursorToNextPosition() {
		composablePredicateIterator.moveCursorToNextPosition();
	}

	@Override
	public boolean hasNext() {
		return composablePredicateIterator.hasNext();
	}

	@Override
	public T[] next() {
		return composablePredicateIterator.next();
	}
}
