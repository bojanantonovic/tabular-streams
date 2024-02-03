package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.FloatTabularStream;
import ch.antonovic.tabularstream.ObjectTabularStream;
import ch.antonovic.tabularstream.function.FloatFunction;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator.FromFloatTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

public class ObjectTabularStreamWithFloatFunctionMapping<T> extends ObjectTabularStream<T> {

	private final FloatTabularStream floatTabularStream;
	private final FloatFunction<T> floatFunction;

	public ObjectTabularStreamWithFloatFunctionMapping(final FloatTabularStream floatTabularStream, final FloatFunction<T> floatFunction, final Class<T> type) {
		super(floatTabularStream.getNumberOfColumns(), type);
		this.floatTabularStream = floatTabularStream;
		this.floatFunction = floatFunction;
	}

	@Override
	public ObjectTabularStreamIterator<T> iterator() {
		return new FromFloatTabularStreamIterator<>(floatTabularStream.iterator(), floatFunction, type);
	}

	@Override
	public boolean isInfinite() {
		return floatTabularStream.isInfinite();
	}

	@Override
	public boolean isFiltered() {
		return floatTabularStream.isFiltered();
	}

	@Override
	public int numberOfLayers() {
		return 1 + floatTabularStream.numberOfLayers();
	}

	@Override
	public long estimatedGrossLength() {
		return floatTabularStream.estimatedGrossLength();
	}
}
