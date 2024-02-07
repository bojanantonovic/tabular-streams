package test.ch.antonovic.tabularstream;

import ch.antonovic.tabularstream.DoubleUnaryTabularStream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoubleUnaryTabularStreamTest {

	@Test
	void mapUnary() {
		final var sourceStream = DoubleUnaryTabularStream.of(DoubleTabularStreamTest.a);
		final var mappedStream = sourceStream.mapUnary(x -> x * 3);
		assertEquals(1, mappedStream.getNumberOfColumns());
		assertEquals(sourceStream.count(), mappedStream.count());
		assertEquals(sourceStream.isInfinite(), mappedStream.isInfinite());
		assertEquals(sourceStream.isFiltered(), mappedStream.isFiltered());
		assertEquals(sourceStream.numberOfLayers() + 1, mappedStream.numberOfLayers());
		final var mappedTable = mappedStream.toArray(double[][]::new);
		assertArrayEquals(new double[][] {{3}, {6}, {9}, {12}}, mappedTable);
		final var mappedTable2 = mappedStream.toArrayColumnStored();
		assertArrayEquals(new double[] {3, 6, 9, 12}, mappedTable2[0]);
		final var aggregationResult = mappedStream.aggregateRows(Double::max);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new double[] {12}, aggregationResult.get());
	}

	@Test
	void fusedMapUnaryAndThenToArray() {
		final var sourceStream = DoubleUnaryTabularStream.of(DoubleTabularStreamTest.a);
		final var result = sourceStream.fusedMapUnaryAndThenToArray(f -> f.mul(3), x -> x * 3);
		assertArrayEquals(new double[] {3, 6, 9, 12}, result);
	}

	@Test
	void fusedMapUnaryAndThenToArray_concat() {
		final var sourceStream = DoubleUnaryTabularStream.concat( //
				DoubleUnaryTabularStream.of(DoubleTabularStreamTest.a), //
				DoubleUnaryTabularStream.of(DoubleTabularStreamTest.b));
		final var result = sourceStream.fusedMapUnaryAndThenToArray(f -> f.mul(3), x -> x * 3);
		assertArrayEquals(new double[] {3, 6, 9, 12, 15, 18, 21, 24}, result);
	}
}
