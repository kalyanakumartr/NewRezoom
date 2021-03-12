package org.hbs.v7.extractor.action.executor;

import java.lang.reflect.Method;
import java.util.Map;

import org.hbs.core.kafka.IKafkaConstants;
import org.hbs.v7.beans.MediatorBean;
import org.hbs.v7.beans.model.ICoreBase;
import org.hbs.v7.beans.model.ICoreData;
import org.hbs.v7.dao.DynaRepo;
import org.hbs.v7.provider.custom.DataProviderExecutorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataExecutor implements IKafkaConstants
{
	private static final long	serialVersionUID	= 7157231262042487975L;

	private static DataExecutor	extractor			= null;

	@Autowired
	DynaRepo					dynaRepo;

	public void execute(MediatorBean mediatorBean)
	{
		try
		{
			Map<DataProvider, Class<?>[]> rMap = DataProviderExecutorImpl.getInstance().getProcessors();
			Object object = null;
			System.out.println(">>>>>Core Data>>STARTS>>>>>> >>>>>>>>>>> >>>>>>>>>>> >>>>>>>>> >>>>>>>>>>>>>> >>>>>>>>>>>>>");
			Method classMethod;
			for (DataProvider eOpt : rMap.keySet())
			{
				System.out.println(eOpt.name() + " >>>> " + eOpt.getMethods());
				for (Class<?> clazz : rMap.get(eOpt))
				{
					classMethod = clazz.getMethod("getInstance", new Class[0]);
					object = classMethod.invoke(null, new Object[0]);

					for (String $method : eOpt.getMethods().split(","))
					{
						try
						{
							System.out.println(object.getClass().getSimpleName() + " >>>> " + $method + " <<<< ");
							Method method = object.getClass().getMethod($method.trim(), new Class[] { MediatorBean.class });
							method.invoke(object, mediatorBean);
						}
						catch (NoSuchMethodException excep)
						{
							System.out.printf(">>>>>>>>>>> %s method NOT available in %s class<<<<<<<<<<<<<<<<<<<<<<<<<<\n", $method, clazz.getSimpleName());
						}
					}

				}
			}
			ICoreBase coreBase = (ICoreBase) dynaRepo.getCoreDao().findById(mediatorBean.dataURN).get();

			for (ICoreData coreData : mediatorBean.coreDataList)
			{
				if (coreData != null)
				{
					coreData.setCoreBase(coreBase);
					dynaRepo.getCoreDataDao().save(coreData);
				}
			}
			System.out.println(">>>>>Core Data>>END>>>>>> >>>>>>>>>>> >>>>>>>>>>> >>>>>>>>> >>>>>>>>>>>>>> >>>>>>>>>>>>>");
		}
		catch (Exception excep)
		{
			excep.printStackTrace();
		}
	}

	public static DataExecutor getInstance()
	{
		if (extractor == null)
		{
			extractor = new DataExecutor();
		}
		return extractor;
	}
}
