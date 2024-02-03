package ch.antonovic.tabularstream.internal.tabular;

import ch.antonovic.tabularstream.TabularStream;
import ch.antonovic.tabularstream.iterator.TabularStreamIterator;

import java.util.Arrays;
import java.util.List;

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
	public long numberOfDeliveredElements() {
		return iterators.stream() //
				.mapToLong(TabularStreamIterator::numberOfDeliveredElements) //
				.sum();
	}

	@Override
	public boolean hasNext() {
		if (currentStreamIndex == numberOfIterators) {
			return false;
		}

		if (getCurrentStream().hasNext()) {
			return true;
		}

		currentStreamIndex++;

		if (currentStreamIndex == numberOfIterators) {
			return false;
		}

		return getCurrentStream().hasNext();
	}

	@Override
	public void incrementPositionWithoutReading() {
		if (currentStreamIndex == numberOfIterators) {
			return;
		}

		if (getCurrentStream().hasNext()) {
			getCurrentStream().incrementPositionWithoutReading();
		} else {
			currentStreamIndex++;
			if (currentStreamIndex == numberOfIterators) {
				return;
			}

			getCurrentStream().incrementPositionWithoutReading();
		}
	}
}
