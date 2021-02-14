package org.hbs.core.bean.model.application;

import javax.persistence.Embeddable;

import org.hbs.core.bean.model.CommunicationMediaBase;
import org.hbs.core.util.ICRUDBean;

@Embeddable
public class BillingMedia extends CommunicationMediaBase implements ICRUDBean
{

	private static final long serialVersionUID = -5811387771852247610L;
}
