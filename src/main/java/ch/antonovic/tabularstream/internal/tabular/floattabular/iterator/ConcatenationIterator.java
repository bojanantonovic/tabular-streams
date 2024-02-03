package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.function.FloatBinaryOperator;
import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import ch.antonovic.tabularstream.TabularStream;
import ch.antonovic.tabularstream.internal.tabular.AbstractConcatenationIterator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

import java.util.NoSuchElementException;

public class ConcatenationIterator extends AbstractConcatenationIterator<float[], FloatTabularStreamIterator> implements FloatTabularStreamIterator {

	public ConcatenationIterator(final TabularStream<float[], FloatTabularStreamIterator>[] streams) {
		super(streams);
	}

	@Override
	public float valueFromColumn(final int index) {
		return getCurrentStream().valueFromColumn(index);
	}

	@Override
	public float[] current() {
		return getCurrentStream().current();
	}

	@Override
	public float mapUnary(final FloatUnaryOperator operator) {
		if (currentStreamIndex == numberOfIterators) {
			throw new NoSuchElementException();
		}

		if (getCurrentStream().hasNext()) {
			return getCurrentStream().mapUnary(operator);
		} else {
			currentStreamIndex++;
			if (currentStreamIndex == numberOfIterators) {
				throw new NoSuchElementException();
			}

			return getCurrentStream().mapUnary(operator);
		}
	}

	@Override
	public float mapBinary(final FloatBinaryOperator operator) {
		if (currentStreamIndex == numberOfIterators) {
			throw new NoSuchElementException();
		}

		if (getCurrentStream().hasNext()) {
			return getCurrentStream().mapBinary(operator);
		} else {
			currentStreamIndex++;
			if (currentStreamIndex == numberOfIterators) {
				throw new NoSuchElementException();
			}

			return getCurrentStream().mapBinary(operator);
		}
	}
}
