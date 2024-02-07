package test.ch.antonovic.tabularstream;

import ch.antonovic.tabularstream.FloatUnaryTabularStream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloatUnaryTabularStreamTest {

	@Test
	void mapUnary() {
		final var sourceStream = FloatUnaryTabularStream.of(FloatTabularStreamTest.a);
		final var mappedStream = sourceStream.mapUnary(x -> x * 3);
		assertEquals(1, mappedStream.getNumberOfColumns());
		assertEquals(sourceStream.count(), mappedStream.count());
		assertEquals(sourceStream.isInfinite(), mappedStream.isInfinite());
		assertEquals(sourceStream.isFiltered(), mappedStream.isFiltered());
		assertEquals(sourceStream.numberOfLayers() + 1, mappedStream.numberOfLayers());
		final var mappedTable = mappedStream.toArray(float[][]::new);
		assertArrayEquals(new float[][] {{3}, {6}, {9}, {12}}, mappedTable);
		final var mappedTable2 = mappedStream.toArrayColumnStored();
		assertArrayEquals(new float[] {3, 6, 9, 12}, mappedTable2[0]);
		final var aggregationResult = mappedStream.aggregateRows(Float::max);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new float[] {12}, aggregationResult.get());
	}

	@Test
	void fusedMapUnaryAndThenToArray() {
		final var sourceStream = FloatUnaryTabularStream.of(FloatTabularStreamTest.a);
		final var result = sourceStream.fusedMapUnaryAndThenToArray(f -> f.mul(3), x -> x * 3);
		assertArrayEquals(new float[] {3, 6, 9, 12}, result);
	}

	@Test
	void fusedMapUnaryAndThenToArray_concat() {
		final var sourceStream = FloatUnaryTabularStream.concat( //
				FloatUnaryTabularStream.of(FloatTabularStreamTest.a), //
				FloatUnaryTabularStream.of(FloatTabularStreamTest.b));
		final var result = sourceStream.fusedMapUnaryAndThenToArray(f -> f.mul(3), x -> x * 3);
		assertArrayEquals(new float[] {3, 6, 9, 12, 15, 18, 21, 24}, result);
	}
}
