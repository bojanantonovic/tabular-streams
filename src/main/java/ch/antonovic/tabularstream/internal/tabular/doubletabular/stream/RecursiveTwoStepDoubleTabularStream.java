package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator.RecursiveTwoStepDoubleTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.function.DoubleBinaryOperator;

public class RecursiveTwoStepDoubleTabularStream extends DoubleTabularStreamByRecursion {
	private final double[] first;
	private final double[] second;
	private final DoubleBinaryOperator binaryOperator;

	public RecursiveTwoStepDoubleTabularStream(final double[] first, final double[] second, final DoubleBinaryOperator binaryOperator) {
		super(first.length);
		this.first = first;
		this.second = second;
		if (first.length != second.length) {
			throw new IllegalArgumentException("Arrays have different length");
		}
		this.binaryOperator = binaryOperator;
	}

	@Override
	public DoubleTabularStreamIterator iterator() {
		return new RecursiveTwoStepDoubleTabularStreamIterator(first, second, binaryOperator);
	}
}
