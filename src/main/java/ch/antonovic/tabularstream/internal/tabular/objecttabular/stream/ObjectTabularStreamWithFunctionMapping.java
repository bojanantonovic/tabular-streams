package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.TabularStream;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator.FromObjectTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.TabularStreamIterator;

import java.util.function.Function;

public class ObjectTabularStreamWithFunctionMapping<U, T> extends AbstractObjectTabularStreamWithFunctionMapping<U[], T> {

	private final Function<U, T> function;

	public ObjectTabularStreamWithFunctionMapping(final TabularStream<U[], ? extends TabularStreamIterator<U[]>> sourceStream, final Function<U, T> function, final Class<T> type) {
		super(sourceStream, type);
		this.function = function;
	}

	@Override
	public ObjectTabularStreamIterator<T> iterator() {
		return new FromObjectTabularStreamIterator<>((TabularStreamIterator<U>) sourceStream.iterator(), function, type);
	}
}
