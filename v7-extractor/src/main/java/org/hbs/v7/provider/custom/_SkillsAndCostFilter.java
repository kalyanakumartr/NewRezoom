package org.hbs.v7.provider.custom;

import java.io.Serializable;

import org.hbs.v7.beans.MediatorBean;
import org.hbs.v7.userdefined.model.ResumeData;
import org.hbs.v7.userdefined.model._SkillsAndCost;

public class _SkillsAndCostFilter implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2248374214422579936L;
	private static _SkillsAndCostFilter	filterFactory		= null;

	private _SkillsAndCostFilter()
	{

	}

	public static _SkillsAndCostFilter getInstance()
	{
		if (filterFactory == null)
		{
			filterFactory = new _SkillsAndCostFilter();
		}
		return filterFactory;
	}

	public void getSkillsAndCost(MediatorBean mBean, ResumeData _RD)
	{
		switch ( mBean.extension )
		{
			case Docx :
			case Doc :
			case ODT :
			case PDF :
			{
				_SkillsAndCost skillsCost = new _SkillsAndCost();
				_RD.setSkillsCost(skillsCost);
				break;
			}
			case HTM :
			case HTML :
			{
				_SkillsAndCost skillsCost = new _SkillsAndCost();
				_RD.setSkillsCost(skillsCost);
				break;
			}
			case Json :
			{
				_SkillsAndCost skillsCost = new _SkillsAndCost();
				_RD.setSkillsCost(skillsCost);
				break;
			}
			case XLS :
			case XLSX :
			{
				_SkillsAndCost skillsCost = new _SkillsAndCost();
				_RD.setSkillsCost(skillsCost);
				break;
			}
			case Csv :
			{
				_SkillsAndCost skillsCost = new _SkillsAndCost();
				_RD.setSkillsCost(skillsCost);
				break;
			}
			default :
				break;

		}
	}
}
