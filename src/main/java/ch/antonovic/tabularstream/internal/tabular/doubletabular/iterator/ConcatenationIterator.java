package ch.antonovic.tabularstream.internal.tabular.doubletabular.iterator;

import ch.antonovic.tabularstream.TabularStream;
import ch.antonovic.tabularstream.internal.tabular.AbstractConcatenationIterator;
import ch.antonovic.tabularstream.iterator.DoubleTabularStreamIterator;
import jdk.incubator.vector.DoubleVector;
import jdk.incubator.vector.VectorSpecies;

public class ConcatenationIterator extends AbstractConcatenationIterator<double[], DoubleTabularStreamIterator> implements DoubleTabularStreamIterator {

	public ConcatenationIterator(final TabularStream<double[], DoubleTabularStreamIterator>[] streams) {
		super(streams);
	}

	@Override
	public double valueFromColumn(final int index) {
		return getCurrentStream().valueFromColumn(index);
	}

	@Override
	public DoubleVector valueFromColumn(final int column, final VectorSpecies<Double> species) {
		return getCurrentStream().valueFromColumn(column, species);
	}
}
