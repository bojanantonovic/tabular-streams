package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.FloatTabularStream;
import ch.antonovic.tabularstream.function.FloatUnaryOperator;
import ch.antonovic.tabularstream.internal.tabular.floattabular.iterator.UnaryMappingColumnsIterator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;

public class FloatTabularStreamWithAllColumnsUnaryMapping extends FloatTabularStreamWrapper {
	private final FloatUnaryOperator[] operators;

	public FloatTabularStreamWithAllColumnsUnaryMapping(final FloatTabularStream parent, final FloatUnaryOperator[] operators) {
		super(parent);
		this.operators = operators;
	}

	@Override
	public FloatTabularStreamIterator iterator() {
		return new UnaryMappingColumnsIterator(parent.iterator(), operators);
	}
}
