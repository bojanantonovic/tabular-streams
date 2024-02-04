package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.internal.tabular.ComposablePredicateIterator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

import java.util.function.Predicate;

public class PredicateIterator extends FloatTabularStreamIteratorWrapper {
	private final ComposablePredicateIterator<float[], FloatTabularStreamIterator> composablePredicateIterator;

	public PredicateIterator(final FloatTabularStreamIterator parentIterator, final Predicate<float[]> predicate) {
		super(parentIterator);
		this.composablePredicateIterator = new ComposablePredicateIterator<>(parentIterator, predicate);
	}

	@Override
	public boolean hasNext() {
		return composablePredicateIterator.hasNext();
	}

	@Override
	public void moveCursorToNextPosition() {
		composablePredicateIterator.moveCursorToNextPosition();
	}

	@Override
	public long numberOfDeliveredElements() {
		return composablePredicateIterator.numberOfDeliveredElements();
	}

	@Override
	public float[] next() {
		return composablePredicateIterator.next();
	}

	@Override
	public void incrementPositionWithoutReading() {
		composablePredicateIterator.incrementPositionWithoutReading();
	}
}
