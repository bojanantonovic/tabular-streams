package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleTabularStream;
import ch.antonovic.tabularstream.function.DoubleTernaryOperator;
import ch.antonovic.tabularstream.function.TernaryOperator;
import ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator.RowsIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;
import jdk.incubator.vector.DoubleVector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.OptionalLong;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.UnaryOperator;

public class DoubleTabularStreamWithColumns extends DoubleTabularStream {

	private static final Logger LOGGER = LogManager.getLogger(DoubleTabularStreamWithColumns.class);

	private final double[][] table;
	private final int numberOfRows;

	public DoubleTabularStreamWithColumns(final double[]... table) {
		super(table.length);
		this.table = table;
		if (getNumberOfColumns() == 0) {
			this.numberOfRows = 0; // default
		} else {
			final var intSummaryStatistics = Arrays.stream(table) //
					.mapToInt(s -> s.length) //
					.summaryStatistics();
			if (intSummaryStatistics.getMax() != intSummaryStatistics.getMin()) {
				throw new IllegalArgumentException("Streams do not have same number of columns (cardinality)");
			}
			this.numberOfRows = intSummaryStatistics.getMax();
		}
	}

	@Override
	public OptionalLong count() {
		return OptionalLong.of(numberOfRows);
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
		return numberOfRows;
	}

	@Override
	public DoubleTabularStreamIterator iterator() {
		return new RowsIterator(table, numberOfRows);
	}

	@Override
	public double[] fusedMapUnaryAndThenToArray(final UnaryOperator<DoubleVector> unaryOperator, final DoubleUnaryOperator doubleUnaryOperator) {
		checkRequiredArity(this, 1);
		return SimdHelper.fusedMapUnaryAndThenToArray(this, unaryOperator, doubleUnaryOperator);
	}

	@Override
	public double[] fusedMapBinaryAndThenToArray(final BinaryOperator<DoubleVector> binaryOperator, final DoubleBinaryOperator doubleBinaryOperator) {
		checkRequiredArity(this, 2);
		return SimdHelper.fusedMapBinaryAndThenToArray(this, binaryOperator, doubleBinaryOperator);
	}

	@Override
	public double[] fusedMapTernaryAndThenToArray(final TernaryOperator<DoubleVector> ternaryOperator, final DoubleTernaryOperator floatTernaryOperator) {
		checkRequiredArity(this, 3);
		return SimdHelper.fusedMapTernaryAndThenToArray(this, ternaryOperator, floatTernaryOperator);
	}
}
