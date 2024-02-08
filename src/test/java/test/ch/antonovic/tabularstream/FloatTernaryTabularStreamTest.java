package test.ch.antonovic.tabularstream;

import ch.antonovic.tabularstream.FloatTabularStream;
import jdk.incubator.vector.FloatVector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FloatTernaryTabularStreamTest {

	final static float[] a = {1, 2, 3, 4, 5, 6};
	final static float[] b = {7, 8, 9, 10, 11, 12};
	final static float[] c = {13, 14, 15, 16, 17, 18};

	@Test
	void mapTernary() {
		final var sourceStream = FloatTabularStream.of(a, b, c);
		final var mappedStream = sourceStream.mapTernary(Math::fma);
		assertEquals(1, mappedStream.getNumberOfColumns());
		assertEquals(sourceStream.count(), mappedStream.count());
		assertEquals(sourceStream.isInfinite(), mappedStream.isInfinite());
		assertEquals(sourceStream.isFiltered(), mappedStream.isFiltered());
		assertEquals(sourceStream.numberOfLayers() + 1, mappedStream.numberOfLayers());
		final var mappedTable = mappedStream.toArray(float[][]::new);
		assertArrayEquals(new float[][] {{1 * 7 + 13}, {2 * 8 + 14}, {3 * 9 + 15}, {4 * 10 + 16}, {5 * 11 + 17}, {6 * 12 + 18}}, mappedTable);
		final var mappedTable2 = mappedStream.toArrayColumnStored();
		assertArrayEquals(new float[] {1 * 7 + 13, 2 * 8 + 14, 3 * 9 + 15, 4 * 10 + 16, 5 * 11 + 17, 6 * 12 + 18}, mappedTable2[0]);
	}

	@Test
	void fusedMapTernaryAndThenToArray() {
		final var sourceStream = FloatTabularStream.of(a, b, c);
		final var result = sourceStream.fusedMapTernaryAndThenToArray(FloatVector::fma, Math::fma);
		assertArrayEquals(new float[] {1 * 7 + 13, 2 * 8 + 14, 3 * 9 + 15, 4 * 10 + 16, 5 * 11 + 17, 6 * 12 + 18}, result);
	}
/*
	@Test
	void fusedMapTernaryAndThenToArray_concat() {
		final var sourceStream = FloatTernaryTabularStream.concat( //
				FloatTernaryTabularStream.of(FloatTabularStreamTest.a, FloatTabularStreamTest.b), //
				FloatTernaryTabularStream.of(FloatTabularStreamTest.c, FloatTabularStreamTest.d));
		final var result = sourceStream.fusedMapTernaryAndThenToArray(FloatVector::add, Float::sum);
		assertArrayEquals(new float[] {6, 8, 10, 12, 23, 25, 27}, result);
	}*/
}
