package ch.antonovic.tabularstream.internal.tabular;

import ch.antonovic.tabularstream.TabularStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.OptionalLong;

public class CountingHelper {

	private static final Logger LOGGER = LogManager.getLogger(CountingHelper.class);

	private CountingHelper() {

	}

	public static long computeLengthAndVerifyIt(final TabularStream<?, ?> tabularStream) {
		final var countOptional = tabularStream.count();
		if (countOptional.isEmpty()) {
			throw new IllegalArgumentException("Length is not determinable");
		}
		final var countedLength = countOptional.orElse(0L);
		LOGGER.debug("counted length: {}", countOptional);
		if (countedLength > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("Required array countOptional exceeds array limit in Java!");
		}

		return countedLength;
	}

	public static OptionalLong countForConcatenation(final TabularStream<?, ?>... streams) {
		if (streams.length == 0) {
			return OptionalLong.empty();
		}
		final var optionalLongs = Arrays.stream(streams).map(TabularStream::count).toList();
		if (optionalLongs.stream().anyMatch(OptionalLong::isEmpty)) {
			throw new IllegalArgumentException("Length of a partial stream can not be determined");
		}
		final var sum = optionalLongs.stream().mapToLong(o -> o.orElse(0L)).sum();
		return OptionalLong.of(sum);
	}
}
