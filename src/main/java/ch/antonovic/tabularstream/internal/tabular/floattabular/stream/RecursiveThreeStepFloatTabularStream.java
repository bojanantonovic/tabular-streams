package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.function.FloatTernaryOperator;
import ch.antonovic.tabularstream.internal.tabular.floattabular.iterator.RecursiveThreeStepFloatTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

public class RecursiveThreeStepFloatTabularStream extends FloatTabularStreamByRecursion {
	private final float[] first;
	private final float[] second;

	private final float[] third;

	private final FloatTernaryOperator ternaryOperator;

	public RecursiveThreeStepFloatTabularStream(final float[] first, final float[] second, final float[] third, final FloatTernaryOperator ternaryOperator) {
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
	public FloatTabularStreamIterator iterator() {
		return new RecursiveThreeStepFloatTabularStreamIterator(first, second, third, ternaryOperator);
	}
}
