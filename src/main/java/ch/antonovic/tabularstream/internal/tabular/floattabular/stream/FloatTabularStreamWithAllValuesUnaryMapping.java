package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.FloatTabularStream;
import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator.UnaryMappingAllFieldsIterator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

public class FloatTabularStreamWithAllValuesUnaryMapping extends FloatTabularStreamWrapper {
	private final FloatUnaryOperator operator;

	public FloatTabularStreamWithAllValuesUnaryMapping(final FloatTabularStream parent, final FloatUnaryOperator operator) {
		super(parent);
		this.operator = operator;
	}

	@Override
	public FloatTabularStreamIterator iterator() {
		return new UnaryMappingAllFieldsIterator(this.iterator(), operator);
	}
}
