package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.TabularStream;
import ch.antonovic.tabularstream.function.FloatFunction;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator.FromFloatTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.TabularStreamIterator;

public class ObjectTabularStreamWithFloatFunctionMapping<T> extends AbstractObjectTabularStreamWithFunctionMapping<float[], T> {

	private final FloatFunction<T> floatFunction;

	public ObjectTabularStreamWithFloatFunctionMapping(final TabularStream<float[], ? extends TabularStreamIterator<float[]>> sourceStream, final FloatFunction<T> floatFunction, final Class<T> type) {
		super(sourceStream, type);
		this.floatFunction = floatFunction;
	}

	@Override
	public ObjectTabularStreamIterator<T> iterator() {
		return new FromFloatTabularStreamIterator<>(sourceStream.iterator(), floatFunction, type);
	}
}
