package ch.antonovic.tabularstream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectBinaryTabularStreamTest {

	@Test
	void mapBinary() {
		final var sourceStream = ObjectBinaryTabularStream.of(Integer.class, ObjectTabularStreamTest.a, ObjectTabularStreamTest.b);
		final var mappedStream = sourceStream.mapBinary(Integer::sum);
		assertEquals(1, mappedStream.getNumberOfColumns());
		assertEquals(sourceStream.count(), mappedStream.count());
		assertEquals(sourceStream.isInfinite(), mappedStream.isInfinite());
		assertEquals(sourceStream.isFiltered(), mappedStream.isFiltered());
		assertEquals(sourceStream.numberOfLayers() + 1, mappedStream.numberOfLayers());
		final var mappedTable = mappedStream.toArray(Integer[][]::new);
		assertArrayEquals(new Integer[][] {{6}, {8}, {10}, {12}}, mappedTable);
		final var mappedTable2 = mappedStream.toArraysColumStored(Integer[][]::new, Integer[]::new);
		assertArrayEquals(new Integer[] {6, 8, 10, 12}, mappedTable2[0]);
		final var aggregationResult = mappedStream.aggregateRows(Integer::max);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new Integer[] {12}, aggregationResult.get());
	}
}
