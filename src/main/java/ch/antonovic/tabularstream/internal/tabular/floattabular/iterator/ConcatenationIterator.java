package ch.antonovic.tabularstream.internal.tabular.floattabular.iterator;

import ch.antonovic.tabularstream.TabularStream;
import ch.antonovic.tabularstream.internal.tabular.AbstractConcatenationIterator;
import ch.antonovic.tabularstream.iterator.FloatTabularStreamIterator;
import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;

public class ConcatenationIterator extends AbstractConcatenationIterator<float[], FloatTabularStreamIterator> implements FloatTabularStreamIterator {

	public ConcatenationIterator(final TabularStream<float[], FloatTabularStreamIterator>[] streams) {
		super(streams);
	}

	@Override
	public float valueFromColumn(final int index) {
		return getCurrentStream().valueFromColumn(index);
	}

	@Override
	public FloatVector valueFromColumn(final int column, final VectorSpecies<Float> species) {
		return getCurrentStream().valueFromColumn(column, species);
	}
}
