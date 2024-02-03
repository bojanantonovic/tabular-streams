package ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator;

import ch.antonovic.tabularstream.function.FloatFunction;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;
import org.springframework.lang.Nullable;

import java.lang.reflect.Array;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class ObjectFloatTabularStreamIterator<T> implements ObjectTabularStreamIterator<T> {
	private final FloatTabularStreamIterator sourceIterator;
	private final FloatFunction<T> floatFunction;

	private final Class<T> type;

	private @Nullable T[] currentValue;

	public ObjectFloatTabularStreamIterator(final FloatTabularStreamIterator sourceIterator, final FloatFunction<T> floatFunction, final Class<T> type) {
		this.sourceIterator = sourceIterator;
		this.floatFunction = floatFunction;
		this.type = type;
	}

	@Override
	public void incrementPositionWithoutReading() {
		sourceIterator.incrementPositionWithoutReading();
	}

	@Override
	public long numberOfDeliveredElements() {
		return sourceIterator.numberOfDeliveredElements();
	}

	@Override
	public T valueFromColumn(final int index) {
		assert currentValue != null;
		return currentValue[index];
	}

	@Override
	public T mapUnary(final UnaryOperator<T> operator) {
		throw new UnsupportedOperationException(); // TODO
	}

	@Override
	public T mapBinary(final BinaryOperator<T> operator) {
		throw new UnsupportedOperationException(); // TODO
	}

	@Override
	public void reset() {
		sourceIterator.reset();
	}

	@Override
	public T[] current() {
		assert currentValue != null;
		return currentValue;
	}

	@Override
	public boolean hasNext() {
		return sourceIterator.hasNext();
	}

	@Override
	public T[] next() {
		final var next = sourceIterator.next();
		currentValue = (T[]) Array.newInstance(type, next.length);
		for (var i = 0; i < next.length; i++) {
			currentValue[i] = floatFunction.apply(next[i]);
		}
		return currentValue;
	}
}
