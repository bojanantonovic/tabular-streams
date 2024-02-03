package ch.antonovic.tabularstream.internal;

import ch.antonovic.tabularstream.Resetable;

public abstract class AbstractStream implements Resetable {

	public abstract long numberOfDeliveredElements();
}
