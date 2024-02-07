package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.FloatTabularStream;
import ch.antonovic.tabularstream.internal.tabular.floattabular.iterator.UnaryMappingIteratorWithSimd;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;
import jdk.incubator.vector.FloatVector;

import java.util.function.UnaryOperator;

public class FloatTabularStreamWithUnarySimdMapping extends FloatTabularStreamWithSingleRowReducing {

	private final UnaryOperator<FloatVector> unaryOperator;

	public FloatTabularStreamWithUnarySimdMapping(final FloatTabularStream sourceStream, final UnaryOperator<FloatVector> unaryOperator) {
		super(sourceStream);
		this.unaryOperator = unaryOperator;
	}

	@Override
	public FloatTabularStreamIterator iterator() {
		return new UnaryMappingIteratorWithSimd(sourceStream.iterator(), unaryOperator);
	}
}
