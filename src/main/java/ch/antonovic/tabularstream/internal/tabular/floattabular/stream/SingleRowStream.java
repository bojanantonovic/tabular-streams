package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.FloatTabularStream;
import ch.antonovic.tabularstream.function.FloatBinaryOperator;
import ch.antonovic.tabularstream.function.FloatTernaryOperator;
import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import ch.antonovic.tabularstream.function.TernaryOperator;
import ch.antonovic.tabularstream.internal.tabular.floattabular.iterator.SingleRowIterator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;
import jdk.incubator.vector.FloatVector;

import java.util.OptionalLong;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class SingleRowStream extends FloatTabularStream {
	private final float[] row;

	public SingleRowStream(final float[] row) {
		super(row.length);
		this.row = row;
	}

	@Override
	public FloatTabularStreamIterator iterator() {
		return new SingleRowIterator(row);
	}

	@Override
	public float[] fusedMapUnaryAndThenToArray(final UnaryOperator<FloatVector> unaryOperator, final FloatUnaryOperator floatUnaryOperator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public float[] fusedMapBinaryAndThenToArray(final BinaryOperator<FloatVector> binaryOperator, final FloatBinaryOperator floatBinaryOperator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public float[] fusedMapTernaryAndThenToArray(final TernaryOperator<FloatVector> ternaryOperator, final FloatTernaryOperator floatTernaryOperator) {
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
