package test.ch.antonovic.tabularstream;

import ch.antonovic.tabularstream.ObjectUnaryTabularStream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectUnaryTabularStreamTest {

	@Test
	void mapUnary() {
		final var sourceStream = ObjectUnaryTabularStream.of(Integer.class, ObjectTabularStreamTest.a);
		final var mappedStream = sourceStream.mapUnary(x -> x * 3);
		assertEquals(1, mappedStream.getNumberOfColumns());
		assertEquals(sourceStream.count(), mappedStream.count());
		assertEquals(sourceStream.isInfinite(), mappedStream.isInfinite());
		assertEquals(sourceStream.isFiltered(), mappedStream.isFiltered());
		assertEquals(sourceStream.numberOfLayers() + 1, mappedStream.numberOfLayers());
		final var mappedTable = mappedStream.toArray(Integer[][]::new);
		assertArrayEquals(new Integer[][] {{3}, {6}, {9}, {12}}, mappedTable);
		final var mappedTable2 = mappedStream.toArrayColumnStored(Integer[][]::new, Integer[]::new);
		assertArrayEquals(new Integer[] {3, 6, 9, 12}, mappedTable2[0]);
		final var aggregationResult = mappedStream.aggregateRows(Integer::max);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new Integer[] {12}, aggregationResult.get());
	}
}
