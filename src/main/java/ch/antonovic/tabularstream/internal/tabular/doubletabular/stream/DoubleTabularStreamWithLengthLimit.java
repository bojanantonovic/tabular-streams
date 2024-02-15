package ch.antonovic.tabularstream.internal.tabular.doubletabular.stream;

import ch.antonovic.tabularstream.DoubleTabularStream;
import ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator.LengthLimitIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.OptionalLong;

public class DoubleTabularStreamWithLengthLimit extends DoubleTabularStreamWrapper {

	private static final Logger LOGGER = LogManager.getLogger(DoubleTabularStreamWithLengthLimit.class);

	private final long limit;

	public DoubleTabularStreamWithLengthLimit(final DoubleTabularStream parent, final long limit) {
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
	public DoubleTabularStreamIterator iterator() {
		return new LengthLimitIterator(parent.iterator(), limit);
	}
}
