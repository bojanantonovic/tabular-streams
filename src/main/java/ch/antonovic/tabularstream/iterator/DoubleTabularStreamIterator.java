package ch.antonovic.tabularstream.iterator;

import jdk.incubator.vector.DoubleVector;
import jdk.incubator.vector.VectorSpecies;

public interface DoubleTabularStreamIterator extends TabularStreamIterator<double[]> {

	double valueFromColumn(int index);

	DoubleVector valueFromColumn(int column, VectorSpecies<Double> species);
}
