package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleTabularStream;
import ch.antonovic.tabularstream.DoubleUnaryTabularStream;
import jdk.incubator.vector.DoubleVector;

import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.UnaryOperator;

public abstract class DoubleTabularStreamWithSingleRowReducing extends DoubleUnaryTabularStream {

	protected final DoubleTabularStream sourceStream;

	protected DoubleTabularStreamWithSingleRowReducing(final DoubleTabularStream sourceStream) {
		this.sourceStream = sourceStream;
	}

	@Override
	public boolean isInfinite() {
		return sourceStream.isInfinite();
	}

	@Override
	public boolean isFiltered() {
		return sourceStream.isFiltered();
	}

	@Override
	public int numberOfLayers() {
		return 1 + sourceStream.numberOfLayers();
	}

	@Override
	public long estimatedGrossLength() {
		return sourceStream.estimatedGrossLength();
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
