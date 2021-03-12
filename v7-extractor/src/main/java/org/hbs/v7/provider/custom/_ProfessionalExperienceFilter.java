package org.hbs.v7.provider.custom;

import java.io.Serializable;

import org.hbs.v7.beans.MediatorBean;
import org.hbs.v7.userdefined.model.ResumeData;
import org.hbs.v7.userdefined.model._ProfessionalExperience;

public class _ProfessionalExperienceFilter implements Serializable
{
	private static final long						serialVersionUID	= 3501477198627339L;
	private static _ProfessionalExperienceFilter	filterFactory		= null;

	private _ProfessionalExperienceFilter()
	{

	}

	public static _ProfessionalExperienceFilter getInstance()
	{
		if (filterFactory == null)
		{
			filterFactory = new _ProfessionalExperienceFilter();
		}
		return filterFactory;
	}

	public void getProfessionalExperience(MediatorBean mBean, ResumeData _RD)
	{
		switch ( mBean.extension )
		{
			case Docx :
			case Doc :
			case ODT :
			case PDF :
			{
				_ProfessionalExperience professional = new _ProfessionalExperience();
				_RD.setProfessionalList(professional);
				break;
			}
			case HTM :
			case HTML :
			{
				_ProfessionalExperience professional = new _ProfessionalExperience();
				_RD.setProfessionalList(professional);
				break;
			}
			case Json :
			{
				_ProfessionalExperience professional = new _ProfessionalExperience();
				_RD.setProfessionalList(professional);
				break;
			}
			case XLS :
			case XLSX :
			{
				_ProfessionalExperience professional = new _ProfessionalExperience();
				_RD.setProfessionalList(professional);
				break;
			}
			case Csv :
			{
				_ProfessionalExperience professional = new _ProfessionalExperience();
				_RD.setProfessionalList(professional);
				break;
			}
			default :
				break;

		}
	}
}
