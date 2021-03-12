package org.hbs.v7.extractor.action.executor;

import java.io.Serializable;
import java.util.Map;

public interface DataProviderExecutor extends Serializable
{
	static Class<?>[] toArray(Class<?>... clazz)
	{
		return clazz;
	}
	
	Map<DataProvider, Class<?>[]> getProcessors();

}