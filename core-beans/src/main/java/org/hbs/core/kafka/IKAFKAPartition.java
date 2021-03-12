package org.hbs.core.kafka;

import java.io.Serializable;

public interface IKAFKAPartition extends Serializable
{
	public int getPartition();
}
