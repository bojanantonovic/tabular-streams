package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.TabularStream;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator.FromDoubleTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.TabularStreamIterator;

import java.util.function.DoubleFunction;

public class ObjectTabularStreamWithDoubleFunctionMapping<T> extends AbstractObjectTabularStreamWithFunctionMapping<double[], T> {

	private final DoubleFunction<T> floatFunction;

	public ObjectTabularStreamWithDoubleFunctionMapping(final TabularStream<double[], ? extends TabularStreamIterator<double[]>> sourceStream, final DoubleFunction<T> floatFunction, final Class<T> type) {
		super(sourceStream, type);
		this.floatFunction = floatFunction;
	}

	@Override
	public ObjectTabularStreamIterator<T> iterator() {
		return new FromDoubleTabularStreamIterator<>(sourceStream.iterator(), floatFunction, type);
	}
}
