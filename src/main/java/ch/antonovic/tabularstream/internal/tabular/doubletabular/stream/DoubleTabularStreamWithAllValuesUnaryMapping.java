package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleTabularStream;
import ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator.UnaryMappingAllFieldsIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.function.DoubleUnaryOperator;

public class DoubleTabularStreamWithAllValuesUnaryMapping extends DoubleTabularStreamWrapper {
	private final DoubleUnaryOperator operator;

	public DoubleTabularStreamWithAllValuesUnaryMapping(final DoubleTabularStream parent, final DoubleUnaryOperator operator) {
		super(parent);
		this.operator = operator;
	}

	@Override
	public DoubleTabularStreamIterator iterator() {
		return new UnaryMappingAllFieldsIterator(parent.iterator(), operator);
	}
}
