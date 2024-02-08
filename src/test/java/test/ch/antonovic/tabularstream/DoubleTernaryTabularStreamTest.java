package test.ch.antonovic.tabularstream;

import ch.antonovic.tabularstream.DoubleTabularStream;
import jdk.incubator.vector.DoubleVector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DoubleTernaryTabularStreamTest {

	final static double[] a = {1, 2, 3, 4, 5, 6};
	final static double[] b = {7, 8, 9, 10, 11, 12};
	final static double[] c = {13, 14, 15, 16, 17, 18};

	@Test
	void mapTernary() {
		final var sourceStream = DoubleTabularStream.of(a, b, c);
		final var mappedStream = sourceStream.mapTernary(Math::fma);
		assertEquals(1, mappedStream.getNumberOfColumns());
		assertEquals(sourceStream.count(), mappedStream.count());
		assertEquals(sourceStream.isInfinite(), mappedStream.isInfinite());
		assertEquals(sourceStream.isFiltered(), mappedStream.isFiltered());
		assertEquals(sourceStream.numberOfLayers() + 1, mappedStream.numberOfLayers());
		final var mappedTable = mappedStream.toArray(double[][]::new);
		assertArrayEquals(new double[][] {{1 * 7 + 13}, {2 * 8 + 14}, {3 * 9 + 15}, {4 * 10 + 16}, {5 * 11 + 17}, {6 * 12 + 18}}, mappedTable);
		final var mappedTable2 = mappedStream.toArrayColumnStored();
		assertArrayEquals(new double[] {1 * 7 + 13, 2 * 8 + 14, 3 * 9 + 15, 4 * 10 + 16, 5 * 11 + 17, 6 * 12 + 18}, mappedTable2[0]);
	}

	@Test
	void fusedMapTernaryAndThenToArray() {
		final var sourceStream = DoubleTabularStream.of(a, b, c);
		final var result = sourceStream.fusedMapTernaryAndThenToArray(DoubleVector::fma, Math::fma);
		assertArrayEquals(new double[] {1 * 7 + 13, 2 * 8 + 14, 3 * 9 + 15, 4 * 10 + 16, 5 * 11 + 17, 6 * 12 + 18}, result);
	}
/*
	@Test
	void fusedMapTernaryAndThenToArray_concat() {
		final var sourceStream = DoubleTernaryTabularStream.concat( //
				DoubleTernaryTabularStream.of(DoubleTabularStreamTest.a, DoubleTabularStreamTest.b), //
				DoubleTernaryTabularStream.of(DoubleTabularStreamTest.c, DoubleTabularStreamTest.d));
		final var result = sourceStream.fusedMapTernaryAndThenToArray(DoubleVector::add, Double::sum);
		assertArrayEquals(new double[] {6, 8, 10, 12, 23, 25, 27}, result);
	}*/
}
