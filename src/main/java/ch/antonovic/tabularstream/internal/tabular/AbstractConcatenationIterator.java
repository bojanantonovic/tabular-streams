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
		return getCurrentStream().next();
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
		if (currentStreamIndex < numberOfIterators && getCurrentStream().hasNext()) {
			getCurrentStream().moveCursorToNextPosition();
		} else {
			currentStreamIndex++;
			if (currentStreamIndex >= numberOfIterators) {
				throw new NoSuchElementException();
			}
			getCurrentStream().moveCursorToNextPosition();
		}
	}

	@Override
	public boolean hasNext() {
		if (currentStreamIndex < numberOfIterators && getCurrentStream().hasNext()) {
			return true;
		}
		if (currentStreamIndex <= numberOfIterators - 2) {
			return iterators.get(currentStreamIndex + 1).hasNext();
		}

		return false;
	}
}
