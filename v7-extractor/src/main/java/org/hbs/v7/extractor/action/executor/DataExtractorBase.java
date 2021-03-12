package org.hbs.v7.extractor.action.executor;

import org.hbs.v7.beans.DataInTopicBean;
import org.hbs.v7.beans.MediatorBean;
import org.hbs.v7.dao.DataAttachmentDao;
import org.hbs.v7.dao.DynaRepo;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DataExtractorBase implements IDataExtractor
{

	private static final long	serialVersionUID	= 650213083715426205L;

	protected DataInTopicBean	inBean;

	protected MediatorBean		mediatorBean;

	@Autowired
	protected DataExecutor		dataExecutor;

	@Autowired
	protected DataAttachmentDao	datDao;

	@Autowired
	protected DynaRepo			coreRepoDao;

	public DataExtractorBase()
	{
		super();
	}

	public IDataExtractor setInBean(DataInTopicBean inBean)
	{
		this.inBean = inBean;
		this.mediatorBean = new MediatorBean(inBean.getExtension(), inBean.getDataURN(), coreRepoDao);
		return this;
	}

	protected abstract MediatorBean read();

	@Override
	public void execute()
	{
		dataExecutor.execute(read());
	}

}