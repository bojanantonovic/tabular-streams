package ch.antonovic.tabularstream.internal.tabular;

import ch.antonovic.tabularstream.iterator.TabularStreamIterator;
import org.springframework.lang.Nullable;

import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class ComposablePredicateIterator<R, I extends TabularStreamIterator<R>> implements TabularStreamIterator<R> {
	private final I parentIterator;
	private final Predicate<? super R> predicate;

	private @Nullable R currentValue = null;

	private boolean hasNextWasCalled = false;

	public ComposablePredicateIterator(final I parentIterator, final Predicate<? super R> predicate) {
		this.parentIterator = parentIterator;
		this.predicate = predicate;
	}

	@Override
	public boolean hasNext() {
		hasNextWasCalled = true;
		while (parentIterator.hasNext()) {
			currentValue = parentIterator.next();

			if (predicate.test(currentValue)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public R next() {
		if (!hasNextWasCalled) {
			throw new IllegalStateException();
		}
		hasNextWasCalled = false;
		assert currentValue != null;
		return currentValue;
	}

	@Override
	public R current() {
		return next();
	}

	@Override
	public void incrementPositionWithoutReading() {
		final var success = hasNextWasCalled || hasNext();
		if (!success) {
			throw new NoSuchElementException();
		}
	}

	@Override
	public long numberOfDeliveredElements() {
		return parentIterator.numberOfDeliveredElements();
	}

	@Override
	public void reset() {
		parentIterator.reset();
	}
}
