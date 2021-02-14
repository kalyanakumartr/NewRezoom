package org.hbs.core.bean;

import java.io.Serializable;
import java.util.Map;

public interface IFormBean extends Serializable
{

	IFormBean getStatusBean();

	IFormBean getStatusBeanWithMessage(String messageCode);

	Map<String, String> updateObjectAsMap();
}