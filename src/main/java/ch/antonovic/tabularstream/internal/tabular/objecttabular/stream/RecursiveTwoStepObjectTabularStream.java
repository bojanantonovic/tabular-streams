package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator.RecursiveTwoStepObjectTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.util.function.BinaryOperator;

public class RecursiveTwoStepObjectTabularStream<T> extends ObjectTabularStreamByRecursion<T> {
	private final T[] first;
	private final T[] second;
	private final BinaryOperator<T> binaryOperator;

	public RecursiveTwoStepObjectTabularStream(final Class<T> type, final T[] first, final T[] second, final BinaryOperator<T> binaryOperator) {
		super(first.length, type);
		this.first = first;
		this.second = second;
		if (first.length != second.length) {
			throw new IllegalArgumentException("Arrays have different length");
		}
		this.binaryOperator = binaryOperator;
	}

	@Override
	public ObjectTabularStreamIterator<T> iterator() {
		return new RecursiveTwoStepObjectTabularStreamIterator<>(type, first, second, binaryOperator);
	}
}
