package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public class DoubleTabularStreamIteratorWrapper implements DoubleTabularStreamIterator {
	protected final DoubleTabularStreamIterator parentIterator;

	public DoubleTabularStreamIteratorWrapper(final DoubleTabularStreamIterator parentIterator) {
		this.parentIterator = parentIterator;
	}

	@Override
	public double valueFromColumn(final int index) {
		return parentIterator.valueFromColumn(index);
	}

	@Override
	public boolean hasNext() {
		return parentIterator.hasNext();
	}

	@Override
	public double[] current() {
		return parentIterator.current();
	}

	@Override
	public double[] next() {
		return parentIterator.next();
	}
/*
	@Override
	public double[] extractRow(IntFunction<double[]> rowGenerator) {
		return parent.extractRow(rowGenerator);
	}

	@Override
	public <T> T mapRow(Function<double[], T> rowMapper) {
		return parent.mapRow(rowMapper);
	}

	@Override
	public double mapRow(ToDoubleFunction<double[]> rowReducer) {
		return parent.mapRow(rowReducer);
	}*/

	@Override
	public void reset() {
		parentIterator.reset();
	}

	@Override
	public long numberOfDeliveredElements() {
		return parentIterator.numberOfDeliveredElements();
	}

	@Override
	public void incrementPositionWithoutReading() {
		parentIterator.incrementPositionWithoutReading();
	}

	@Override
	public double mapUnary(final DoubleUnaryOperator operator) {
		return parentIterator.mapUnary(operator);
	}

	@Override
	public double mapBinary(final DoubleBinaryOperator operator) {
		return parentIterator.mapBinary(operator);
	}
}