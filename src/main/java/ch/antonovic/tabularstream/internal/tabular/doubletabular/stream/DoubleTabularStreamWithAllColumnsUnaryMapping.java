package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleTabularStream;
import ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator.UnaryMappingColumnsIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;

import java.util.function.DoubleUnaryOperator;

public class DoubleTabularStreamWithAllColumnsUnaryMapping extends DoubleTabularStreamWrapper {
	private final DoubleUnaryOperator[] operators;

	public DoubleTabularStreamWithAllColumnsUnaryMapping(final DoubleTabularStream parent, final DoubleUnaryOperator[] operators) {
		super(parent);
		this.operators = operators;
	}

	@Override
	public DoubleTabularStreamIterator iterator() {
		return new UnaryMappingColumnsIterator(parent.iterator(), operators);
	}
}
