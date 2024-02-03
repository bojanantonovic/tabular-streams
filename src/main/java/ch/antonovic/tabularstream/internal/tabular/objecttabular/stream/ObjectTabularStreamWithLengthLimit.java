package ch.antonovic.tabularstream.internal.tabular.objecttabular.stream;

import ch.antonovic.tabularstream.ObjectTabularStream;
import ch.antonovic.tabularstream.internal.tabular.objecttabular.iterator.LengthLimitIterator;
import ch.antonovic.tabularstream.iterator.ObjectTabularStreamIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ObjectTabularStreamWithLengthLimit<T> extends ObjectTabularStreamWrapper<T> {

	private static final Logger LOGGER = LogManager.getLogger(ObjectTabularStreamWithLengthLimit.class);

	private final long limit;

	public ObjectTabularStreamWithLengthLimit(final ObjectTabularStream<T> parent, final long limit) {
		super(parent);
		this.limit = limit;
	}

	@Override
	public boolean isInfinite() {
		return false;
	}

	@Override
	public long estimatedGrossLength() {
		return limit;
	}

	@Override
	public long count() {
		if (parent.isInfinite()) {
			return limit;
		}
		return super.count();
	}

	@Override
	public ObjectTabularStreamIterator<T> iterator() {
		return new LengthLimitIterator<>(parent.iterator(), limit);
	}
}
