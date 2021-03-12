package org.hbs.v7.beans;

import org.hbs.v7.util.EMessagePriority;

public class DataInTopicBean extends AttachmentInfoBean implements IDataInTopicBean
{
	private static final long	serialVersionUID	= 3504792085335973094L;
	private long				attachmentAutoId	= 0L;
	private String				dataURN;
	private boolean				external			= false;					// Will_Be_Used_If_We_Expose_DataExtraction_As_Service
	private EMessagePriority	priority			= EMessagePriority.Normal;

	public DataInTopicBean()
	{
		super();
	}

	public long getAttachmentAutoId()
	{
		return attachmentAutoId;
	}

	public EMessagePriority getPriority()
	{
		return priority;
	}

	public boolean isExternal()
	{
		return external;
	}

	public void setAttachmentAutoId(long attachmentAutoId)
	{
		this.attachmentAutoId = attachmentAutoId;
	}

	public void setExternal(boolean external)
	{
		this.external = external;
	}

	public void setPriority(EMessagePriority priority)
	{
		this.priority = priority;
	}

	public String getDataURN()
	{
		return dataURN;
	}

	public void setDataURN(String dataURN)
	{
		this.dataURN = dataURN;
	}
	
}
