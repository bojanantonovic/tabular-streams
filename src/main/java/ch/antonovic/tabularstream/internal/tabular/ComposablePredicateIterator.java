package ch.antonovic.tabularstream.internal.tabular;

import ch.antonovic.tabularstream.iterator.TabularStreamIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.lang.Nullable;

import java.util.function.Predicate;

public class ComposablePredicateIterator<R, I extends TabularStreamIterator<R>> implements TabularStreamIterator<R> {
	private static final Logger LOGGER = LogManager.getLogger(ComposablePredicateIterator.class);

	private final I parentIterator;
	private final Predicate<? super R> predicate;

	private @Nullable R currentValue = null;

	private boolean nextValidElementExists = false;

	private long numberOfDeliveredElements = 0;

	public ComposablePredicateIterator(final I parentIterator, final Predicate<? super R> predicate) {
		this.parentIterator = parentIterator;
		this.predicate = predicate;
		moveCursorToNextPosition();
	}

	@Override
	public void moveCursorToNextPosition() {
		while (parentIterator.hasNext()) {
			currentValue = parentIterator.next();
			LOGGER.debug("current value: {}", currentValue);

			nextValidElementExists = predicate.test(currentValue);
			LOGGER.debug("is valid: {}", nextValidElementExists);
			if (nextValidElementExists) {
				numberOfDeliveredElements++;
				LOGGER.debug("number of valid elements so far: {}", numberOfDeliveredElements);
				return;
			}
		}
		nextValidElementExists = false;
	}

	@Override
	public long numberOfDeliveredElements() {
		return numberOfDeliveredElements;
	}

	@Override
	public boolean hasNext() {
		return nextValidElementExists;
	}

	@Override
	public R next() {
		final var result = currentValue;
		moveCursorToNextPosition();
		return result;
	}

	@Override
	public void reset() {
		numberOfDeliveredElements = 0;
		parentIterator.reset();
	}
}
