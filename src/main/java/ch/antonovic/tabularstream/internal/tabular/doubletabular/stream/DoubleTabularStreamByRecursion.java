package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleTabularStream;
import jdk.incubator.vector.DoubleVector;

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.UnaryOperator;

public abstract class DoubleTabularStreamByRecursion extends DoubleTabularStream {

	protected DoubleTabularStreamByRecursion(final int numberOfColumns) {
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
	public Optional<double[]> aggregateRows(final DoubleBinaryOperator... binaryOperators) {
		throw new UnsupportedOperationException("Stream is infinite. Only special cases can be computed in finite time.");
	}

	@Override
	public double[] fusedMapUnaryAndThenToArray(final UnaryOperator<DoubleVector> unaryOperator, final DoubleUnaryOperator doubleUnaryOperator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double[] fusedMapBinaryAndThenToArray(final BinaryOperator<DoubleVector> binaryOperator, final DoubleBinaryOperator doubleBinaryOperator) {
		throw new UnsupportedOperationException();
	}
}
