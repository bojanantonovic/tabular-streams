package ch.antonovic.tabularstream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoubleBinaryTabularStreamTest {

	@Test
	void mapBinary() {
		final var sourceStream = DoubleBinaryTabularStream.of(DoubleTabularStreamTest.a, DoubleTabularStreamTest.b);
		final var mappedStream = sourceStream.mapBinary(Double::sum);
		assertEquals(1, mappedStream.getNumberOfColumns());
		assertEquals(sourceStream.count(), mappedStream.count());
		assertEquals(sourceStream.isInfinite(), mappedStream.isInfinite());
		assertEquals(sourceStream.isFiltered(), mappedStream.isFiltered());
		assertEquals(sourceStream.numberOfLayers() + 1, mappedStream.numberOfLayers());
		final var mappedTable = mappedStream.toArray(double[][]::new);
		assertArrayEquals(new double[][] {{6}, {8}, {10}, {12}}, mappedTable);
		final var mappedTable2 = mappedStream.toArrayColumnStored();
		assertArrayEquals(new double[] {6, 8, 10, 12}, mappedTable2[0]);
		final var aggregationResult = mappedStream.aggregateRows(Double::max);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new double[] {12}, aggregationResult.get());
	}
}
