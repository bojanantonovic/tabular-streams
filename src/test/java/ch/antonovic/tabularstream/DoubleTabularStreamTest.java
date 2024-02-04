package ch.antonovic.tabularstream;

import ch.antonovic.tabularstream.function.DoubleTernaryOperator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class DoubleTabularStreamTest {

	final static double[] a = {1, 2, 3, 4};
	final static double[] b = {5, 6, 7, 8};

	private final static double[] c = {10, 11, 12};
	private final static double[] d = {13, 14, 15};

	@Test
	void of_noArrays() {
		final var stream = DoubleTabularStream.of();
		assertEquals(0, stream.getNumberOfColumns());
		assertEquals(0, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(1, stream.numberOfLayers());
		assertTrue(stream.aggregateRows().isEmpty());
	}

	@Test
	void of() {
		final var stream = DoubleTabularStream.of(a, b);
		assertEquals(2, stream.getNumberOfColumns());
		assertEquals(a.length, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(1, stream.numberOfLayers());
		final var aggregationResult = stream.aggregateRows(Double::max, Double::min);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new double[] {4, 5}, aggregationResult.get());
		final var aggregationResult2 = stream.aggregateRowsWithSameOperator(Double::sum);
		assertTrue(aggregationResult2.isPresent());
		assertArrayEquals(new double[] {10, 26}, aggregationResult2.get());
	}

	@Test
	void generate() {
		final var stream = DoubleTabularStream.generator(4, () -> a);
		assertEquals(4, stream.getNumberOfColumns());
		assertTrue(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(1, stream.numberOfLayers());
	}

	@Test
	void generateRecursiveStream_oneStepDoubling_limit() {
		final var stream = DoubleTabularStream.generateRecursiveStream(new double[] {1, 3}, x -> x * 2).limit(5);
		assertEquals(2, stream.getNumberOfColumns());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var table = stream.toArrayColumnStored();
		assertArrayEquals(new double[] {1, 2, 4, 8, 16}, table[0]);
		assertArrayEquals(new double[] {3, 6, 12, 24, 48}, table[1]);
	}

	@Test
	void generateRecursiveStream_twoStepsWithFibonacci_limit() {
		final var stream = DoubleTabularStream.generateRecursiveStream(new double[] {1, 2}, new double[] {1, 2}, Double::sum).limit(5);
		assertEquals(2, stream.getNumberOfColumns());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var table = stream.toArrayColumnStored();
		assertArrayEquals(new double[] {1, 1, 2, 3, 5}, table[0]);
		assertArrayEquals(new double[] {2, 2, 4, 6, 10}, table[1]);
	}

	@Test
	void generateRecursiveStream_threeStepsWithThreebonacci_limit() {
		final DoubleTernaryOperator ternaryOperator = (a, b, c) -> a + b + c;
		final var stream = DoubleTabularStream.generateRecursiveStream(new double[] {1, 2}, new double[] {1, 2}, new double[] {1, 2}, ternaryOperator).limit(6);
		assertEquals(2, stream.getNumberOfColumns());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var table = stream.toArrayColumnStored();
		assertArrayEquals(new double[] {1, 1, 1, 3, 5, 9}, table[0]);
		assertArrayEquals(new double[] {2, 2, 2, 6, 10, 18}, table[1]);
	}

	@Test
	void limit() {
		final var newLength = 3;
		final var stream = DoubleTabularStream.of(a, b).limit(newLength);
		assertEquals(2, stream.getNumberOfColumns());
		assertEquals(newLength, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var aggregationResult = stream.aggregateRows(Double::max, Double::min);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new double[] {3, 5}, aggregationResult.get());
	}

	@Test
	void limit_lengthZero() {
		final var newLength = 0;
		final var stream = DoubleTabularStream.of(a, b).limit(newLength);
		assertEquals(2, stream.getNumberOfColumns());
		assertEquals(newLength, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var aggregationResult = stream.aggregateRows(Double::max, Double::min);
		assertTrue(aggregationResult.isEmpty());
	}

	@Test
	void limit_infiniteStream() {
		final var newLength = 3;
		final var stream = DoubleTabularStream.generator(4, () -> a).limit(newLength);
		assertEquals(4, stream.getNumberOfColumns());
		assertEquals(newLength, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var aggregationResult = stream.aggregateRows(Double::sum, Double::max, Double::min, (x, y) -> x * y);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new double[] {newLength, 2, 3, (double) Math.pow(4, newLength)}, aggregationResult.get());
	}

	@Test
	void next_iterator() {
		final var stream = DoubleTabularStream.of(a, b);
		final var iterator = stream.iterator();
		final var firstRow = iterator.next();
		final var secondRow = iterator.next();
		// assert
		final double[] expected1 = new double[] {1, 5};
		assertArrayEquals(expected1, firstRow);
		final double[] expected2 = new double[] {2, 6};
		assertArrayEquals(expected2, secondRow);
	}
/*
	@Test
	void current_infiniteRandomStream() {
		final var random = new Random();
		final var stream = DoubleTabularStream.generator(2, () -> new double[] {random.nextDouble(), random.nextDouble()});
		final var iterator = stream.iterator();
		assertEquals(0, iterator.numberOfDeliveredElements());
		assertThrows(IllegalStateException.class, iterator::current, "");
		final var next = iterator.next();
		assertEquals(1, iterator.numberOfDeliveredElements());
		final var current1 = iterator.current();
		assertEquals(1, iterator.numberOfDeliveredElements());
		final var current2 = iterator.current();
		assertEquals(1, iterator.numberOfDeliveredElements());
		assertArrayEquals(next, current1);
		assertArrayEquals(current1, current2);
	}*/

	@Test
	void filter() {
		// act
		final var original = DoubleTabularStream.of(a, b);
		// act
		final var stream = original.filter(row -> row[0] + row[1] >= 10);
		// assert
		assertEquals(2, stream.getNumberOfColumns());
		assertEquals(2, stream.count());
		assertFalse(stream.isInfinite());
		assertTrue(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var array = stream.toArray(double[][]::new);
		assertArrayEquals(new double[][] {{3, 7}, {4, 8}}, array);
		final var aggregationResult = stream.aggregateRows(Double::max, Double::min);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new double[] {4, 7}, aggregationResult.get());
	}

	@Test
	void concat_ofEmpty() {
		// arrange
		final var stream1 = DoubleTabularStream.of();
		final var stream2 = DoubleTabularStream.of();
		final var concatenatedStream = DoubleTabularStream.concat(stream1, stream2);
		// act & assert
		assertEquals(0, concatenatedStream.count());
		assertFalse(concatenatedStream.isInfinite());
		assertFalse(concatenatedStream.isFiltered());
		assertEquals(2, concatenatedStream.numberOfLayers());
		final var aggregationResult = concatenatedStream.aggregateRows();
		assertTrue(aggregationResult.isEmpty());
	}

	@Test
	void concat() {
		// arrange
		final var stream1 = DoubleTabularStream.of(a, b);
		final var stream2 = DoubleTabularStream.of(c, d);
		final var concatenatedStream = DoubleTabularStream.concat(stream1, stream2);
		// act & assert
		assertEquals(7, concatenatedStream.count());
		assertFalse(concatenatedStream.isInfinite());
		assertFalse(concatenatedStream.isFiltered());
		assertEquals(2, concatenatedStream.numberOfLayers());
		final var aggregationResult = concatenatedStream.aggregateRows(Double::max, Double::min);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new double[] {12, 5}, aggregationResult.get());
	}

	@Test
	void mapAllValuesUnary() {
		// act
		final var stream = DoubleTabularStream.of(a, b).mapAllValuesUnary(x -> x * 2);
		// assert
		assertEquals(4, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var expected = new double[][] {{2, 4, 6, 8}, {10, 12, 14, 16}};
		assertArrayEquals(expected, stream.toArrayColumnStored());
	}

	@Test
	void mapColumnsUnary() {
		// act
		final var stream = DoubleTabularStream.of(a, b).mapColumnsUnary(x -> x * 2, y -> y + 1);
		// assert
		assertEquals(4, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var expected = new double[][] {{2, 4, 6, 8}, {6, 7, 8, 9}};
		assertArrayEquals(expected, stream.toArrayColumnStored());
	}

	@Test
	void mapToObject() throws IOException {
		// arrange
		final var sourceStream = DoubleTabularStream.of(DoubleTabularStreamTest.a);
		HtmlExport.toHtmlFile(Path.of("target", "mapToObject_sourceStream.html"), sourceStream);
		// act
		final var stream = sourceStream.mapToObject(x -> (int) x, Integer.class);
		HtmlExport.toHtmlFile(Path.of("target", "mapToObject_mappedStream.html"), stream);
		// assert
		assertEquals(1, stream.getNumberOfColumns());
		assertEquals(4, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var array = stream.toArray(Integer[][]::new);
		assertArrayEquals(new Integer[][] {{1}, {2}, {3}, {4}}, array);
	}
}
