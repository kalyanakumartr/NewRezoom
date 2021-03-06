package org.hbs.v7.provider.custom;

import java.io.Serializable;

import org.hbs.v7.beans.MediatorBean;
import org.hbs.v7.userdefined.model.ResumeData;
import org.hbs.v7.userdefined.model._Media;

public class _MediaFilter implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4196566999415174151L;
	private static _MediaFilter	filterFactory		= null;

	private _MediaFilter()
	{

	}

	public static _MediaFilter getInstance()
	{
		if (filterFactory == null)
		{
			filterFactory = new _MediaFilter();
		}
		return filterFactory;
	}

	public void getMedia(MediatorBean mBean, ResumeData _RD)
	{
		switch ( mBean.extension )
		{
			case Docx :
			case Doc :
			case ODT :
			case PDF :
			{
				_Media media = new _Media(_RD);
				_RD.setMediaList(media);
				break;
			}
			case HTM :
			case HTML :
			{
				_Media media = new _Media(_RD);
				_RD.setMediaList(media);
				break;
			}
			case Json :
			{
				_Media media = new _Media(_RD);
				_RD.setMediaList(media);
				break;
			}
			case XLS :
			case XLSX :
			{
				_Media media = new _Media(_RD);
				_RD.setMediaList(media);
				break;
			}
			case Csv :
			{
				_Media media = new _Media(_RD);
				_RD.setMediaList(media);
				break;
			}
			default :
				break;

		}
	}
}
