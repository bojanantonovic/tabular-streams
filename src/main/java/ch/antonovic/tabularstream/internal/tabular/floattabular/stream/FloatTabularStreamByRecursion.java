package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.FloatTabularStream;
import ch.antonovic.tabularstream.function.FloatBinaryOperator;
import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import jdk.incubator.vector.FloatVector;

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public abstract class FloatTabularStreamByRecursion extends FloatTabularStream {

	protected FloatTabularStreamByRecursion(final int numberOfColumns) {
		super(numberOfColumns);
	}

	@Override
	public boolean isInfinite() {
		return true;
	}

	@Override
	public boolean isFiltered() {
		return false;
	}

	@Override
	public int numberOfLayers() {
		return 1;
	}

	@Override
	public long estimatedGrossLength() {
		return Long.MAX_VALUE;
	}

	@Override
	public Optional<float[]> aggregateRows(final FloatBinaryOperator... binaryOperators) {
		throw new UnsupportedOperationException("Stream is infinite. Only special cases can be computed in finite time.");
	}

	@Override
	public float[] fusedMapUnaryAndThenToArray(final UnaryOperator<FloatVector> unaryOperator, final FloatUnaryOperator floatUnaryOperator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public float[] fusedMapBinaryAndThenToArray(final BinaryOperator<FloatVector> binaryOperator, final FloatBinaryOperator floatBinaryOperator) {
		throw new UnsupportedOperationException();
	}
}
