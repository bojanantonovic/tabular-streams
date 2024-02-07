package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.FloatTabularStream;
import ch.antonovic.tabularstream.function.FloatBinaryOperator;
import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import jdk.incubator.vector.FloatVector;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public abstract class FloatTabularStreamWrapper extends FloatTabularStream {
	protected final FloatTabularStream parent;

	protected FloatTabularStreamWrapper(final FloatTabularStream parent) {
		super(parent.getNumberOfColumns());
		this.parent = parent;
	}

	@Override
	public boolean isInfinite() {
		return parent.isInfinite();
	}

	@Override
	public boolean isFiltered() {
		return parent.isFiltered();
	}

	@Override
	public int numberOfLayers() {
		return 1 + parent.numberOfLayers();
	}

	@Override
	public long estimatedGrossLength() {
		return parent.estimatedGrossLength();
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
