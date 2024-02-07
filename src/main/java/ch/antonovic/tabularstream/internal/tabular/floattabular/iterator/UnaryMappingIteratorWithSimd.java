package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;
import jdk.incubator.vector.FloatVector;

import java.util.function.UnaryOperator;

public class UnaryMappingIteratorWithSimd extends FloatTabularStreamIteratorWrapper {
	final UnaryOperator<FloatVector> unaryOperator;

	public UnaryMappingIteratorWithSimd(final FloatTabularStreamIterator parentIterator, final UnaryOperator<FloatVector> unaryOperator) {
		super(parentIterator);
		this.unaryOperator = unaryOperator;
	}
/*
	@Override
	public float valueFromColumn(final int index) {
		return unaryOperator.applyAsFloat(parentIterator.valueFromColumn(0));
	}*/

	@Override
	public float[] next() {
		final var current = new float[] {valueFromColumn(0)};
		moveCursorToNextPosition();
		return current;
	}
}
