package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleTabularStream;
import ch.antonovic.tabularstream.function.DoubleTernaryOperator;
import ch.antonovic.tabularstream.function.TernaryOperator;
import ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator.SingleRowIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;
import jdk.incubator.vector.DoubleVector;

import java.util.OptionalLong;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.UnaryOperator;

public class SingleRowStream extends DoubleTabularStream {
	private final double[] row;

	public SingleRowStream(final double[] row) {
		super(row.length);
		this.row = row;
	}

	@Override
	public DoubleTabularStreamIterator iterator() {
		return new SingleRowIterator(row);
	}

	@Override
	public double[] fusedMapUnaryAndThenToArray(final UnaryOperator<DoubleVector> unaryOperator, final DoubleUnaryOperator doubleUnaryOperator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double[] fusedMapBinaryAndThenToArray(final BinaryOperator<DoubleVector> binaryOperator, final DoubleBinaryOperator doubleBinaryOperator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double[] fusedMapTernaryAndThenToArray(final TernaryOperator<DoubleVector> ternaryOperator, final DoubleTernaryOperator doubleTernaryOperator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isInfinite() {
		return false;
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
		return 1;
	}

	@Override
	public OptionalLong count() {
		return OptionalLong.of(1);
	}
}
