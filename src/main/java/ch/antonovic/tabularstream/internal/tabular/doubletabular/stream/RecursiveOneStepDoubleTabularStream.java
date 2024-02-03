package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator.RecursiveOneStepDoubleTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.function.DoubleUnaryOperator;

public class RecursiveOneStepDoubleTabularStream extends DoubleTabularStreamByRecursion {
	private final double[] first;

	private final DoubleUnaryOperator unaryOperator;

	public RecursiveOneStepDoubleTabularStream(final double[] first, final DoubleUnaryOperator unaryOperator) {
		super(first.length);
		this.first = first;
		this.unaryOperator = unaryOperator;
	}

	@Override
	public DoubleTabularStreamIterator iterator() {
		return new RecursiveOneStepDoubleTabularStreamIterator(first, unaryOperator);
	}
}
