package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleTabularStream;
import jdk.incubator.vector.DoubleVector;

import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.UnaryOperator;

public abstract class DoubleTabularStreamWrapper extends DoubleTabularStream {
	protected final DoubleTabularStream parent;

	protected DoubleTabularStreamWrapper(final DoubleTabularStream parent) {
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
	public double[] fusedMapUnaryAndThenToArray(final UnaryOperator<DoubleVector> unaryOperator, final DoubleUnaryOperator doubleUnaryOperator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double[] fusedMapBinaryAndThenToArray(final BinaryOperator<DoubleVector> binaryOperator, final DoubleBinaryOperator doubleBinaryOperator) {
		throw new UnsupportedOperationException();
	}
}
