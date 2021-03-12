package org.hbs.v7.provider.custom;

import java.util.LinkedHashMap;
import java.util.Map;

import org.hbs.v7.extractor.action.executor.DataProvider;
import org.hbs.v7.extractor.action.executor.DataProviderExecutor;

public class DataProviderExecutorImpl implements DataProviderExecutor
{

	private static final long			serialVersionUID	= 6411675993838025595L;
	private static DataProviderExecutorImpl	executorProvider		= null;

	private DataProviderExecutorImpl()
	{

	}

	public static DataProviderExecutorImpl getInstance()
	{
		if (executorProvider == null)
		{
			executorProvider = new DataProviderExecutorImpl();
		}
		return executorProvider;
	}

	@Override
	public Map<DataProvider, Class<?>[]> getProcessors()
	{
		Map<DataProvider, Class<?>[]> optionList = new LinkedHashMap<DataProvider, Class<?>[]>();

		for (EDataProviderImpl eOpt : EDataProviderImpl.values())
		{
			switch ( eOpt )
			{
				case RoadDataInfo :
				{
					optionList.put(eOpt, DataProviderExecutor.toArray(_PersonalInfoFilter.class)); // You_can_add_multiple_extractor
					break;
				}
			}
		}
		return optionList;
	}

}

enum EDataProviderImpl implements DataProvider
{
	RoadDataInfo("", "getRoadDataInfo"); // You_can_call_multiple_methods_using_COMMA separated

	String	clazz;
	String	methods;

	EDataProviderImpl(String clazz, String method)
	{
		this.clazz = clazz;
		this.methods = method;
	}

	EDataProviderImpl set(Class<?> clazz)
	{
		this.clazz = clazz.getCanonicalName();
		return this;
	}

	public String getMethods()
	{
		return methods;
	}

}
