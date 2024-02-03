package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.function.TernaryOperator;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator.RecursiveThreeStepObjectTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

public class RecursiveThreeStepObjectTabularStream<T> extends ObjectTabularStreamByRecursion<T> {
	private final T[] first;
	private final T[] second;

	private final T[] third;

	private final TernaryOperator<T> ternaryOperator;

	public RecursiveThreeStepObjectTabularStream(final Class<T> type, final T[] first, final T[] second, final T[] third, final TernaryOperator<T> ternaryOperator) {
		super(first.length, type);
		this.first = first;
		this.second = second;
		this.third = third;
		if (first.length != second.length || first.length != third.length) {
			throw new IllegalArgumentException("Arrays have different length");
		}
		this.ternaryOperator = ternaryOperator;
	}

	@Override
	public ObjectTabularStreamIterator<T> iterator() {
		return new RecursiveThreeStepObjectTabularStreamIterator<>(type, first, second, third, ternaryOperator);
	}
}
