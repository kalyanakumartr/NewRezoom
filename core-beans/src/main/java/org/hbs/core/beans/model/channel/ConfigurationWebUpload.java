package org.hbs.core.beans.model.channel;

public class ConfigurationWebUpload extends ConfigurationBase 
{

	private static final long serialVersionUID = -6102812339164812067L;

	private EExtension fileType;

	public ConfigurationWebUpload()
	{
		super();
	}

	public EExtension getFileType()
	{
		return fileType;
	}

	public void setFileType(EExtension fileType)
	{
		this.fileType = fileType;
	}

}
