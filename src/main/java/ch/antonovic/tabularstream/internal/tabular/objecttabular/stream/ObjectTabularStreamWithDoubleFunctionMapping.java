package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.DoubleTabularStream;
import ch.antonovic.tabularstream.ObjectTabularStream;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator.FromDoubleTabularStreamIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;

import java.util.function.DoubleFunction;

public class ObjectTabularStreamWithDoubleFunctionMapping<T> extends ObjectTabularStream<T> {

	private final DoubleTabularStream floatTabularStream;
	private final DoubleFunction<T> floatFunction;

	public ObjectTabularStreamWithDoubleFunctionMapping(final DoubleTabularStream floatTabularStream, final DoubleFunction<T> floatFunction, final Class<T> type) {
		super(floatTabularStream.getNumberOfColumns(), type);
		this.floatTabularStream = floatTabularStream;
		this.floatFunction = floatFunction;
	}

	@Override
	public ObjectTabularStreamIterator<T> iterator() {
		return new FromDoubleTabularStreamIterator<>(floatTabularStream.iterator(), floatFunction, type);
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
