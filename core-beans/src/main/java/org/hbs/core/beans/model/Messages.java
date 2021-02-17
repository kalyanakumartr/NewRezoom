package org.hbs.core.beans.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hbs.core.security.resource.IPath.ETemplate;
import org.hbs.core.security.resource.IPathBase.EMediaMode;

@Entity
@Table(name = "messages")
public class Messages extends MessagesBase
{

	private static final long serialVersionUID = 684883062828693721L;

	public Messages()
	{
		super();
	}

	public Messages(ETemplate eTemplate)
	{
		this.messageId = eTemplate.name();
	}

	@Override
	public void generateConfigurationFromProducerProperty(EMediaMode mediaMode) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}

}