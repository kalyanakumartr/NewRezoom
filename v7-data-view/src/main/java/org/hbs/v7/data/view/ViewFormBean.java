package org.hbs.v7.data.view;

import org.hbs.core.beans.APIStatus;
import org.hbs.core.util.EnumInterface;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ViewFormBean extends APIStatus
{

	private static final long serialVersionUID = -2243532009258748571L;

	public enum EViewAction implements EnumInterface
	{
		Start, End;
	}

	public String		incomingId;
	public String		dataId;
	public String		taskId;
	public String		status;
	public String		statusReason;
	public EViewAction	eAction	= EViewAction.Start;
	public String		roadTypeId;
	public String		remarks;

	@Override
	public void clearForm()
	{

	}

}
