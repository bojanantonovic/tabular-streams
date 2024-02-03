package ch.antonovic.tabularstream;

import ch.antonovic.tabularstream.function.FloatTernaryOperator;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FloatTabularStreamTest {

	final static float[] a = {1, 2, 3, 4};
	final static float[] b = {5, 6, 7, 8};

	private final static float[] c = {10, 11, 12};
	private final static float[] d = {13, 14, 15};

	@Test
	void of_noArrays() {
		final var stream = FloatTabularStream.of();
		assertEquals(0, stream.getNumberOfColumns());
		assertEquals(0, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(1, stream.numberOfLayers());
		assertTrue(stream.aggregateRows().isEmpty());
	}

	@Test
	void of() {
		final var stream = FloatTabularStream.of(a, b);
		assertEquals(2, stream.getNumberOfColumns());
		assertEquals(a.length, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(1, stream.numberOfLayers());
		final var aggregationResult = stream.aggregateRows(Float::max, Float::min);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new float[] {4, 5}, aggregationResult.get());
		final var aggregationResult2 = stream.aggregateRowsWithSameOperator(Float::sum);
		assertTrue(aggregationResult2.isPresent());
		assertArrayEquals(new float[] {10, 26}, aggregationResult2.get());
	}

	@Test
	void generate() {
		final var stream = FloatTabularStream.generator(4, () -> a);
		assertEquals(4, stream.getNumberOfColumns());
		assertTrue(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(1, stream.numberOfLayers());
	}

	@Test
	void generateRecursiveStream_OneStepDoubling_limit() {
		final var stream = FloatTabularStream.generateRecursiveStream(new float[] {1, 3}, x -> x * 2).limit(5);
		assertEquals(2, stream.getNumberOfColumns());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var table = stream.toArraysColumStored(float[][]::new, float[]::new);
		assertArrayEquals(new float[] {1, 2, 4, 8, 16}, table[0]);
		assertArrayEquals(new float[] {3, 6, 12, 24, 48}, table[1]);
	}

	@Test
	void generateRecursiveStream_twoStepsWithFibonacci_limit() {
		final var stream = FloatTabularStream.generateRecursiveStream(new float[] {1, 2}, new float[] {1, 2}, Float::sum).limit(5);
		assertEquals(2, stream.getNumberOfColumns());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var table = stream.toArraysColumStored(float[][]::new, float[]::new);
		assertArrayEquals(new float[] {1, 1, 2, 3, 5}, table[0]);
		assertArrayEquals(new float[] {2, 2, 4, 6, 10}, table[1]);
	}

	@Test
	void generateRecursiveStream_threeStepsWithThreebonacci_limit() {
		final FloatTernaryOperator ternaryOperator = (a, b, c) -> a + b + c;
		final var stream = FloatTabularStream.generateRecursiveStream(new float[] {1, 2}, new float[] {1, 2}, new float[] {1, 2}, ternaryOperator).limit(6);
		assertEquals(2, stream.getNumberOfColumns());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var table = stream.toArraysColumStored(float[][]::new, float[]::new);
		assertArrayEquals(new float[] {1, 1, 1, 3, 5, 9}, table[0]);
		assertArrayEquals(new float[] {2, 2, 2, 6, 10, 18}, table[1]);
	}

	@Test
	void limit() {
		final var newLength = 3;
		final var stream = FloatTabularStream.of(a, b).limit(newLength);
		assertEquals(2, stream.getNumberOfColumns());
		assertEquals(newLength, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var aggregationResult = stream.aggregateRows(Float::max, Float::min);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new float[] {3, 5}, aggregationResult.get());
	}

	@Test
	void limit_lengthZero() {
		final var newLength = 0;
		final var stream = FloatTabularStream.of(a, b).limit(newLength);
		assertEquals(2, stream.getNumberOfColumns());
		assertEquals(newLength, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var aggregationResult = stream.aggregateRows(Float::max, Float::min);
		assertTrue(aggregationResult.isEmpty());
	}

	@Test
	void limit_infiniteStream() {
		final var newLength = 3;
		final var stream = FloatTabularStream.generator(4, () -> a).limit(newLength);
		assertEquals(4, stream.getNumberOfColumns());
		assertEquals(newLength, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var aggregationResult = stream.aggregateRows(Float::sum, Float::max, Float::min, (x, y) -> x * y);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new float[] {newLength, 2, 3, (float) Math.pow(4, newLength)}, aggregationResult.get());
	}

	@Test
	void next_iterator() {
		final var stream = FloatTabularStream.of(a, b);
		final var iterator = stream.iterator();
		final var firstRow = iterator.next();
		final var secondRow = iterator.next();
		// assert
		final float[] expected1 = new float[] {1, 5};
		assertArrayEquals(expected1, firstRow);
		final float[] expected2 = new float[] {2, 6};
		assertArrayEquals(expected2, secondRow);
	}

	@Test
	void current_infiniteRandomStream() {
		final var random = new Random();
		final var stream = FloatTabularStream.generator(2, () -> new float[] {random.nextFloat(), random.nextFloat()});
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
	}

	@Test
	void filter() {
		// act
		final var original = FloatTabularStream.of(a, b);
		// act
		final var stream = original.filter(row -> row[0] + row[1] >= 10);
		// assert
		assertEquals(2, stream.getNumberOfColumns());
		assertEquals(2, stream.count());
		assertFalse(stream.isInfinite());
		assertTrue(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var array = stream.toArray(float[][]::new);
		assertArrayEquals(new float[][] {{3, 7}, {4, 8}}, array);
		final var aggregationResult = stream.aggregateRows(Float::max, Float::min);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new float[] {4, 7}, aggregationResult.get());
	}

	@Test
	void concat_ofEmpty() {
		// arrange
		final var stream1 = FloatTabularStream.of();
		final var stream2 = FloatTabularStream.of();
		final var concatenatedStream = FloatTabularStream.concat(stream1, stream2);
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
		final var stream1 = FloatTabularStream.of(a, b);
		final var stream2 = FloatTabularStream.of(c, d);
		final var concatenatedStream = FloatTabularStream.concat(stream1, stream2);
		// act & assert
		assertEquals(7, concatenatedStream.count());
		assertFalse(concatenatedStream.isInfinite());
		assertFalse(concatenatedStream.isFiltered());
		assertEquals(2, concatenatedStream.numberOfLayers());
		final var aggregationResult = concatenatedStream.aggregateRows(Float::max, Float::min);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new float[] {12, 5}, aggregationResult.get());
	}
}
