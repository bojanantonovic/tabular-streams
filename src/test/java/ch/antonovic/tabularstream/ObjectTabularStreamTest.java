package ch.antonovic.tabularstream;

import ch.antonovic.tabularstream.function.TernaryOperator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectTabularStreamTest {

	final static Integer[] a = {1, 2, 3, 4};
	final static Integer[] b = {5, 6, 7, 8};

	private final static Integer[] c = {10, 11, 12};
	private final static Integer[] d = {13, 14, 15};

	@Test
	void of_noArrays() {
		final var stream = ObjectTabularStream.of(Object.class);
		assertEquals(0, stream.getNumberOfColumns());
		assertEquals(0, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(1, stream.numberOfLayers());
		assertTrue(stream.aggregateRows().isEmpty());
	}

	@Test
	void of() {
		final var stream = ObjectTabularStream.of(Integer.class, a, b);
		assertEquals(2, stream.getNumberOfColumns());
		assertEquals(a.length, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(1, stream.numberOfLayers());
		final var aggregationResult = stream.aggregateRows(Integer::max, Integer::min);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new Integer[] {4, 5}, aggregationResult.get());
		final var aggregationResult2 = stream.aggregateRowsWithSameOperator(Integer::sum);
		assertTrue(aggregationResult2.isPresent());
		assertArrayEquals(new Integer[] {10, 26}, aggregationResult2.get());
	}

	@Test
	void generate() {
		final var stream = ObjectTabularStream.generator(4, Integer.class, () -> a);
		assertEquals(4, stream.getNumberOfColumns());
		assertTrue(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(1, stream.numberOfLayers());
	}

	@Test
	void generateRecursiveStream_oneStepDoubling_limit() {
		final var stream = ObjectTabularStream.generateRecursiveStream(new Integer[] {1, 3}, x -> x * 2).limit(5);
		assertEquals(2, stream.getNumberOfColumns());
		assertEquals(5, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var table = stream.toArrayColumnStored(Integer[][]::new, Integer[]::new);
		assertArrayEquals(new Integer[] {1, 2, 4, 8, 16}, table[0]);
		assertArrayEquals(new Integer[] {3, 6, 12, 24, 48}, table[1]);
	}

	@Test
	void generateRecursiveStream_twoStepsWithFibonacci_limit() {
		final var stream = ObjectTabularStream.generateRecursiveStream(Integer.class, new Integer[] {1, 2}, new Integer[] {1, 2}, Integer::sum).limit(5);
		assertEquals(2, stream.getNumberOfColumns());
		assertEquals(5, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var table = stream.toArrayColumnStored(Integer[][]::new, Integer[]::new);
		assertArrayEquals(new Integer[] {1, 1, 2, 3, 5}, table[0]);
		assertArrayEquals(new Integer[] {2, 2, 4, 6, 10}, table[1]);
	}

	@Test
	void generateRecursiveStream_threeStepsWithThreebonacci_limit() {
		final TernaryOperator<Integer> ternaryOperator = (a, b, c) -> a + b + c;
		final var limit = 6;
		final var stream = ObjectTabularStream.generateRecursiveStream(Integer.class, new Integer[] {1, 2}, new Integer[] {1, 2}, new Integer[] {1, 2}, ternaryOperator).limit(limit);
		assertEquals(2, stream.getNumberOfColumns());
		assertEquals(limit, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var table = stream.toArrayColumnStored(Integer[][]::new, Integer[]::new);
		assertArrayEquals(new Integer[] {1, 1, 1, 3, 5, 9}, table[0]);
		assertArrayEquals(new Integer[] {2, 2, 2, 6, 10, 18}, table[1]);
	}

	@Test
	void limit() {
		final var newLength = 3;
		final var stream = ObjectTabularStream.of(Integer.class, a, b).limit(newLength);
		assertEquals(2, stream.getNumberOfColumns());
		assertEquals(newLength, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var aggregationResult = stream.aggregateRows(Integer::max, Integer::min);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new Integer[] {3, 5}, aggregationResult.get());
	}

	@Test
	void limit_lengthZero() {
		final var newLength = 0;
		final var stream = ObjectTabularStream.of(Integer.class, a, b).limit(newLength);
		assertEquals(2, stream.getNumberOfColumns());
		assertEquals(newLength, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var aggregationResult = stream.aggregateRows(Integer::max, Integer::min);
		assertTrue(aggregationResult.isEmpty());
	}

	@Test
	void limit_infiniteStream() {
		final var newLength = 3;
		final var stream = ObjectTabularStream.generator(4, Integer.class, () -> a).limit(newLength);
		assertEquals(4, stream.getNumberOfColumns());
		assertEquals(newLength, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var aggregationResult = stream.aggregateRows(Integer::sum, Integer::max, Integer::min, (x, y) -> x * y);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new Integer[] {newLength, 2, 3, (int) Math.pow(4, newLength)}, aggregationResult.get());
	}

	@Test
	void next_iterator() {
		final var stream = ObjectTabularStream.of(Integer.class, a, b);
		final var iterator = stream.iterator();
		final var firstRow = iterator.next();
		final var secondRow = iterator.next();
		// assert
		final Integer[] expected1 = new Integer[] {1, 5};
		assertArrayEquals(expected1, firstRow);
		final Integer[] expected2 = new Integer[] {2, 6};
		assertArrayEquals(expected2, secondRow);
	}
/*
	@Test
	void current_infiniteRandomStream() {
		final var random = new Random();
		final var stream = ObjectTabularStream.generator(2, Integer.class, () -> new Integer[] {random.nextInt(), random.nextInt()});
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
		final var original = ObjectTabularStream.of(Integer.class, a, b);
		// act
		final var stream = original.filter(row -> row[0] + row[1] >= 10);
		// assert
		assertEquals(2, stream.getNumberOfColumns());
		assertEquals(2, stream.count());
		assertFalse(stream.isInfinite());
		assertTrue(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var array = stream.toArray(Integer[][]::new);
		assertArrayEquals(new Integer[][] {{3, 7}, {4, 8}}, array);
		final var aggregationResult = stream.aggregateRows(Integer::max, Integer::min);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new Integer[] {4, 7}, aggregationResult.get());
	}

	@Test
	void concat_ofEmpty() {
		// arrange
		final var stream1 = ObjectTabularStream.of(Integer.class);
		final var stream2 = ObjectTabularStream.of(Integer.class);
		final var concatenatedStream = ObjectTabularStream.concat(Integer.class, stream1, stream2);
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
		final var stream1 = ObjectTabularStream.of(Integer.class, a, b);
		final var stream2 = ObjectTabularStream.of(Integer.class, c, d);
		final var concatenatedStream = ObjectTabularStream.concat(Integer.class, stream1, stream2);
		// act & assert
		assertEquals(7, concatenatedStream.count());
		assertFalse(concatenatedStream.isInfinite());
		assertFalse(concatenatedStream.isFiltered());
		assertEquals(2, concatenatedStream.numberOfLayers());
		final var aggregationResult = concatenatedStream.aggregateRows(Integer::max, Integer::min);
		assertTrue(aggregationResult.isPresent());
		assertArrayEquals(new Integer[] {12, 5}, aggregationResult.get());
	}

	@Test
	void mapAllValuesUnary() {
		// act
		final var stream = ObjectTabularStream.of(Integer.class, a, b).mapAllValuesUnary(x -> x * 2);
		// assert
		assertEquals(4, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var expected = new Integer[][] {{2, 4, 6, 8}, {10, 12, 14, 16}};
		assertArrayEquals(expected, stream.toArrayColumnStored(Integer[][]::new, Integer[]::new));
	}

	@Test
	void mapColumnsUnary() {
		// act
		final var stream = ObjectTabularStream.of(Integer.class, a, b).mapColumnsUnary(x -> x * 2, y -> y + 1);
		// assert
		assertEquals(4, stream.count());
		assertFalse(stream.isInfinite());
		assertFalse(stream.isFiltered());
		assertEquals(2, stream.numberOfLayers());
		final var expected = new Integer[][] {{2, 4, 6, 8}, {6, 7, 8, 9}};
		assertArrayEquals(expected, stream.toArrayColumnStored(Integer[][]::new, Integer[]::new));
	}
}
