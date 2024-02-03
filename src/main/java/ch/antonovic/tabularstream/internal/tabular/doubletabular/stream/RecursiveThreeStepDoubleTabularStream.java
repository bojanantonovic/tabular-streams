package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.function.DoubleTernaryOperator;
import ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator.RecursiveThreeStepDoubleTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

public class RecursiveThreeStepDoubleTabularStream extends DoubleTabularStreamByRecursion {
	private final double[] first;
	private final double[] second;

	private final double[] third;

	private final DoubleTernaryOperator ternaryOperator;

	public RecursiveThreeStepDoubleTabularStream(final double[] first, final double[] second, final double[] third, final DoubleTernaryOperator ternaryOperator) {
		super(first.length);
		this.first = first;
		this.second = second;
		this.third = third;
		if (first.length != second.length || first.length != third.length) {
			throw new IllegalArgumentException("Arrays have different length");
		}
		this.ternaryOperator = ternaryOperator;
	}

	@Override
	public DoubleTabularStreamIterator iterator() {
		return new RecursiveThreeStepDoubleTabularStreamIterator(first, second, third, ternaryOperator);
	}
}
