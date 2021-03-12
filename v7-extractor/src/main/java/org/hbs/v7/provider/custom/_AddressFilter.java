package org.hbs.v7.provider.custom;

import java.io.Serializable;

import org.hbs.v7.beans.MediatorBean;
import org.hbs.v7.userdefined.model.ResumeData;
import org.hbs.v7.userdefined.model._Address;

public class _AddressFilter implements Serializable
{
	/**
	 * 
	 */
	private static final long			serialVersionUID	= -3930496576019385191L;
	private static _AddressFilter	filterFactory		= null;

	private _AddressFilter()
	{

	}

	public static _AddressFilter getInstance()
	{
		if (filterFactory == null)
		{
			filterFactory = new _AddressFilter();
		}
		return filterFactory;
	}

	public void getAddress(MediatorBean mBean, ResumeData _RD)
	{
		switch ( mBean.extension )
		{
			case Docx :
			case Doc :
			case ODT :
			case PDF :
			{
				_Address address = new _Address(_RD);
				_RD.setAddressList(address);
				break;
			}
			case HTM :
			case HTML :
			{
				_Address address = new _Address(_RD);
				_RD.setAddressList(address);
				break;
			}
			case Json :
			{
				_Address address = new _Address(_RD);
				_RD.setAddressList(address);
				break;
			}
			case XLS :
			case XLSX :
			{
				_Address address = new _Address(_RD);
				_RD.setAddressList(address);
				break;
			}
			case Csv :
			{
				_Address address = new _Address(_RD);
				_RD.setAddressList(address);
				break;
			}
			default :
				break;

		}
	}
}
