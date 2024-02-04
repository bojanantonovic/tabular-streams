package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.internal.tabular.ComposablePredicateIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.function.Predicate;

public class PredicateIterator extends DoubleTabularStreamIteratorWrapper {
	private final ComposablePredicateIterator<double[], DoubleTabularStreamIterator> composablePredicateIterator;

	public PredicateIterator(final DoubleTabularStreamIterator parentIterator, final Predicate<double[]> predicate) {
		super(parentIterator);
		this.composablePredicateIterator = new ComposablePredicateIterator<>(parentIterator, predicate);
	}

	@Override
	public boolean hasNext() {
		return composablePredicateIterator.hasNext();
	}

	@Override
	public double[] next() {
		return composablePredicateIterator.next();
	}

	@Override
	public void moveCursorToNextPosition() {
		composablePredicateIterator.moveCursorToNextPosition();
	}

	@Override
	public long numberOfDeliveredElements() {
		return composablePredicateIterator.numberOfDeliveredElements();
	}
}
