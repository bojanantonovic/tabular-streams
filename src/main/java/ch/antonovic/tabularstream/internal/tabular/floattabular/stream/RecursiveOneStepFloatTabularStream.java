package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import ch.antonovic.tabularstream.internal.tabular.floattabular.iterator.RecursiveOneStepFloatTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

public class RecursiveOneStepFloatTabularStream extends FloatTabularStreamByRecursion {
	private final float[] first;

	private final FloatUnaryOperator unaryOperator;

	public RecursiveOneStepFloatTabularStream(final float[] first, final FloatUnaryOperator unaryOperator) {
		super(first.length);
		this.first = first;
		this.unaryOperator = unaryOperator;
	}

	@Override
	public FloatTabularStreamIterator iterator() {
		return new RecursiveOneStepFloatTabularStreamIterator(first, unaryOperator);
	}
}
