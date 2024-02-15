package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleUnaryTabularStream;
import ch.antonovic.tabularstream.function.DoubleTernaryOperator;
import ch.antonovic.tabularstream.function.TernaryOperator;
import ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator.RowsIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;
import jdk.incubator.vector.DoubleVector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.OptionalLong;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.UnaryOperator;

public class DoubleUnaryTabularStreamWithColumn extends DoubleUnaryTabularStream {

	private static final Logger LOGGER = LogManager.getLogger(DoubleUnaryTabularStreamWithColumn.class);

	private final double[] column;

	public DoubleUnaryTabularStreamWithColumn(final double[] column) {
		this.column = column;
	}

	@Override
	public OptionalLong count() {
		return OptionalLong.of(column.length);
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
		return column.length;
	}

	@Override
	public DoubleTabularStreamIterator iterator() {
		return new RowsIterator(new double[][] {column}, column.length);
	}

	@Override
	public double[] fusedMapUnaryAndThenToArray(final UnaryOperator<DoubleVector> unaryOperator, final DoubleUnaryOperator doubleUnaryOperator) {
		return SimdHelper.fusedMapUnaryAndThenToArray(this, unaryOperator, doubleUnaryOperator);
	}

	@Override
	public double[] fusedMapBinaryAndThenToArray(final BinaryOperator<DoubleVector> binaryOperator, final DoubleBinaryOperator doubleBinaryOperator) {
		checkRequiredArity(this, 2);
		return new double[0]; // dummy value
	}

	@Override
	public double[] fusedMapTernaryAndThenToArray(final TernaryOperator<DoubleVector> ternaryOperator, final DoubleTernaryOperator doubleTernaryOperator) {
		checkRequiredArity(this, 3);
		return new double[0];
	}
}
