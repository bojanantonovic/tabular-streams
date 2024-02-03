package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.TabularStream;
import ch.antonovic.tabularstream.internal.tabular.AbstractConcatenationIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.NoSuchElementException;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public class ConcatenationIterator extends AbstractConcatenationIterator<double[], DoubleTabularStreamIterator> implements DoubleTabularStreamIterator {

	public ConcatenationIterator(final TabularStream<double[], DoubleTabularStreamIterator>[] streams) {
		super(streams);
	}

	@Override
	public double valueFromColumn(final int index) {
		return getCurrentStream().valueFromColumn(index);
	}

	@Override
	public double[] current() {
		return getCurrentStream().current();
	}

	@Override
	public double mapUnary(final DoubleUnaryOperator operator) {
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
	public double mapBinary(final DoubleBinaryOperator operator) {
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
