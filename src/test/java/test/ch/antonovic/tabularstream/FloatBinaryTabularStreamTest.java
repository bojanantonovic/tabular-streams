package test.ch.antonovic.tabularstream;

import ch.antonovic.tabularstream.FloatBinaryTabularStream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

class FloatBinaryTabularStreamTest {

	@Test
	void mapBinary() {
		final var sourceStream = FloatBinaryTabularStream.of(FloatTabularStreamTest.a, FloatTabularStreamTest.b);
		final var mappedStream = sourceStream.mapBinary(Float::sum);
		assertEquals(1, mappedStream.getNumberOfColumns());
		assertEquals(sourceStream.count(), mappedStream.count());
		assertEquals(sourceStream.isInfinite(), mappedStream.isInfinite());
		assertEquals(sourceStream.isFiltered(), mappedStream.isFiltered());
		assertEquals(sourceStream.numberOfLayers() + 1, mappedStream.numberOfLayers());
		final var mappedTable = mappedStream.toArray(float[][]::new);
		assertArrayEquals(new float[][] {{6}, {8}, {10}, {12}}, mappedTable);
		final var mappedTable2 = mappedStream.toArraysColumStored(float[][]::new, float[]::new);
		assertArrayEquals(new float[] {6, 8, 10, 12}, mappedTable2[0]);
		final var aggregationResult = mappedStream.aggregateRows(Float::max);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new float[] {12}, aggregationResult.get());
	}
}
