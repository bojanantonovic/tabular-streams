package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.TabularStream;
import ch.antonovic.tabularstream.internal.tabular.AbstractConcatenationIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.util.NoSuchElementException;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class ConcatenationIterator<T> extends AbstractConcatenationIterator<T[], ObjectTabularStreamIterator<T>> implements ObjectTabularStreamIterator<T> {

	public ConcatenationIterator(final TabularStream<T[], ObjectTabularStreamIterator<T>>[] streams) {
		super(streams);
	}

	@Override
	public T valueFromColumn(final int index) {
		return getCurrentStream().valueFromColumn(index);
	}

	@Override
	public T[] current() {
		return getCurrentStream().current();
	}

	@Override
	public T mapUnary(final UnaryOperator<T> operator) {
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
	public T mapBinary(final BinaryOperator<T> operator) {
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
