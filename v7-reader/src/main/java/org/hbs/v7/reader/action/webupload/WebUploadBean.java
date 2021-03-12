package org.hbs.v7.reader.action.webupload;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.hbs.core.beans.model.IConfiguration;
import org.hbs.v7.beans.AttachmentInfoBean;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class WebUploadBean extends AttachmentInfoBean
{
	private static final long	serialVersionUID	= 5243226456713989817L;
	public String				producerId;
	@JsonIgnore
	public List<String>			failureList			= new ArrayList<String>();

	public WebUploadBean(IConfiguration config) throws MessagingException
	{
		super();
		this.producerId = config.getProducerId();
		this.setBaseFolderPath(config.getBaseFolderPath());
		this.setSubFolderPath(this.producerId); //Default First Sub Folder is Producer Id
	}

}
