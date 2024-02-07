package ch.antonovic.tabularstream.iterator;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;

public interface FloatTabularStreamIterator extends TabularStreamIterator<float[]> {
	float valueFromColumn(int index);

	FloatVector valueFromColumn(int column, VectorSpecies<Float> species);
}
