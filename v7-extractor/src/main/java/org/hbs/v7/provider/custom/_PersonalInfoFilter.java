package org.hbs.v7.provider.custom;

import java.io.Serializable;

import org.hbs.v7.beans.MediatorBean;
import org.hbs.v7.userdefined.model.ResumeData;
import org.hbs.v7.userdefined.model._PersonalInfo;

public class _PersonalInfoFilter implements Serializable
{
	/**
	 * 
	 */
	private static final long			serialVersionUID	= -3930496576019385191L;
	private static _PersonalInfoFilter	filterFactory		= null;

	private _PersonalInfoFilter()
	{

	}

	public static _PersonalInfoFilter getInstance()
	{
		if (filterFactory == null)
		{
			filterFactory = new _PersonalInfoFilter();
		}
		return filterFactory;
	}

	public void getPersonalInfo(MediatorBean mBean, ResumeData _RD)
	{
		switch ( mBean.extension )
		{
			case Docx :
			case Doc :
			case ODT :
			case PDF :
			{
				_PersonalInfo personal = new _PersonalInfo();
				System.out.println("getPersonalInfo :: ");
				_RD.setPersonal(personal);
				break;
			}
			case HTM :
			case HTML :
			{
				_PersonalInfo personal = new _PersonalInfo();
				_RD.setPersonal(personal);
				break;
			}
			case Json :
			{
				_PersonalInfo personal = new _PersonalInfo();
				_RD.setPersonal(personal);
				break;
			}
			case XLS :
			case XLSX :
			{
				_PersonalInfo personal = new _PersonalInfo();
				_RD.setPersonal(personal);
				break;
			}
			case Csv :
			{
				_PersonalInfo personal = new _PersonalInfo();
				_RD.setPersonal(personal);
				break;
			}
			default :
				break;

		}
	}
}
