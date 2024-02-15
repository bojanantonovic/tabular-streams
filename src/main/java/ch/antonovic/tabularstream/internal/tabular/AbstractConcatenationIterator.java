package ch.antonovic.tabularstream.internal.tabular;

import ch.antonovic.tabularstream.TabularStream;
import ch.antonovic.tabularstream.iterator.TabularStreamIterator;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class AbstractConcatenationIterator<R, I extends TabularStreamIterator<R>> implements TabularStreamIterator<R> {
	protected final List<I> iterators;

	protected final int numberOfIterators;

	protected int currentStreamIndex = 0;

	protected AbstractConcatenationIterator(final TabularStream<R, I>[] streams) {
		this.numberOfIterators = streams.length;
		this.iterators = Arrays.stream(streams) //
				.map(TabularStream::iterator) //
				.toList();
	}

	protected I getCurrentStream() {
		return iterators.get(currentStreamIndex);
	}

	@Override
	public R next() {
		if (currentStreamIndex < numberOfIterators && getCurrentStream().hasNext()) {
			return getCurrentStream().next();
		} else {
			currentStreamIndex++;
			if (currentStreamIndex >= numberOfIterators) {
				throw new NoSuchElementException();
			}
			return getCurrentStream().next();
		}
	}

	@Override
	public void reset() {
		currentStreamIndex = 0;
	}

	@Override
	public final long numberOfDeliveredElements() {
		return iterators.stream() //
				.mapToLong(TabularStreamIterator::numberOfDeliveredElements) //
				.sum();
	}

	@Override
	public final void moveCursorToNextPosition() {
		moveCursorToNextPosition(1);
	}

	@Override
	public void moveCursorToNextPosition(final long stepWidth) {
		if (currentStreamIndex < numberOfIterators && getCurrentStream().hasNext(stepWidth)) {
			getCurrentStream().moveCursorToNextPosition(stepWidth);
		} else {
			currentStreamIndex++;
			if (currentStreamIndex >= numberOfIterators) {
				throw new NoSuchElementException();
			}
			getCurrentStream().moveCursorToNextPosition(stepWidth);
		}
	}

	@Override
	public boolean hasNext() {
		return hasNext(1);
	}

	@Override
	public boolean hasNext(final long stepWidth) {
		if (currentStreamIndex < numberOfIterators && getCurrentStream().hasNext(stepWidth)) {
			return true;
		}
		if (currentStreamIndex <= numberOfIterators - 2) {
			return iterators.get(currentStreamIndex + 1).hasNext(stepWidth);
		}

		return false;
	}

	@Override
	public int skip(final int amount) {
		var skippedTotally = 0L;
		var remainingAmountToSkip = amount;
		while (remainingAmountToSkip > 0) {
			final var skipped = getCurrentStream().skip(amount);
			skippedTotally += skipped;
			remainingAmountToSkip -= skipped;
			currentStreamIndex++;
			if (currentStreamIndex >= numberOfIterators) {
				throw new NoSuchElementException();
			}
		}

		return (int) skippedTotally;
	}
}
