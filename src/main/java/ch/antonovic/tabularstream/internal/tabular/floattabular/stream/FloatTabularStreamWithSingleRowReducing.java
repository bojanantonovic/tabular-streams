package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.FloatTabularStream;
import ch.antonovic.tabularstream.FloatUnaryTabularStream;
import ch.antonovic.tabularstream.function.FloatBinaryOperator;
import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import jdk.incubator.vector.FloatVector;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public abstract class FloatTabularStreamWithSingleRowReducing extends FloatUnaryTabularStream {

	protected final FloatTabularStream sourceStream;

	protected FloatTabularStreamWithSingleRowReducing(final FloatTabularStream sourceStream) {
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
	public float[] fusedMapUnaryAndThenToArray(final UnaryOperator<FloatVector> unaryOperator, final FloatUnaryOperator floatUnaryOperator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public float[] fusedMapBinaryAndThenToArray(final BinaryOperator<FloatVector> binaryOperator, final FloatBinaryOperator floatBinaryOperator) {
		throw new UnsupportedOperationException();
	}
}
