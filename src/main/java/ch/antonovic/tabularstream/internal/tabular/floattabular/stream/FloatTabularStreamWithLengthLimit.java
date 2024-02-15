package ch.antonovic.tabularstream.internal.tabular.floattabular.stream;

import ch.antonovic.tabularstream.FloatTabularStream;
import ch.antonovic.tabularstream.internal.tabular.floattabular.iterator.LengthLimitIterator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.OptionalLong;

public class FloatTabularStreamWithLengthLimit extends FloatTabularStreamWrapper {

	private static final Logger LOGGER = LogManager.getLogger(FloatTabularStreamWithLengthLimit.class);

	private final long limit;

	public FloatTabularStreamWithLengthLimit(final FloatTabularStream parent, final long limit) {
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
	public OptionalLong count() {
		if (parent.isInfinite()) {
			return OptionalLong.of(limit);
		}
		return super.count();
	}

	@Override
	public FloatTabularStreamIterator iterator() {
		return new LengthLimitIterator(parent.iterator(), limit);
	}
}
