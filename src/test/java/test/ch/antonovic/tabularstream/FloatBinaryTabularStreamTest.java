package test.ch.antonovic.tabularstream;

import ch.antonovic.tabularstream.FloatBinaryTabularStream;
import jdk.incubator.vector.FloatVector;
import org.junit.jupiter.api.Test;

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
		final var mappedTable2 = mappedStream.toArrayColumnStored();
		assertArrayEquals(new float[] {6, 8, 10, 12}, mappedTable2[0]);
		final var aggregationResult = mappedStream.aggregateRows(Float::max);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new float[] {12}, aggregationResult.get());
	}

	@Test
	void fusedMapBinaryAndThenToArray() {
		final var sourceStream = FloatBinaryTabularStream.of(FloatTabularStreamTest.a, FloatTabularStreamTest.b);
		final var result = sourceStream.fusedMapBinaryAndThenToArray(FloatVector::add, Float::sum);
		assertArrayEquals(new float[] {6, 8, 10, 12}, result);
	}

	@Test
	void fusedMapBinaryAndThenToArray_concat() {
		final var sourceStream = FloatBinaryTabularStream.concat( //
				FloatBinaryTabularStream.of(FloatTabularStreamTest.a, FloatTabularStreamTest.b), //
				FloatBinaryTabularStream.of(FloatTabularStreamTest.c, FloatTabularStreamTest.d));
		final var result = sourceStream.fusedMapBinaryAndThenToArray(FloatVector::add, Float::sum);
		assertArrayEquals(new float[] {6, 8, 10, 12, 23, 25, 27}, result);
	}
}
