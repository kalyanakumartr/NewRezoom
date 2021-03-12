package org.hbs.core.beans.model.channel;

import org.hbs.core.util.EnumInterface;
import org.hbs.core.util.IConstProperty;
import org.hbs.core.util.IConstProperty.EWrap;

public enum EExtension implements EnumInterface
{
	// RAR we are not supporting due to 3rd party license
	Invalid, Zip, _7z, Doc, Docx, ODT, XLS, XLSX, ODS, PDF, HTML, HTM, Json, Csv;

	public static EExtension isValid(String fileName)
	{
		if (fileName.indexOf(IConstProperty.DOT) > 0)
		{
			for (EExtension EE : EExtension.values())
			{
				if (EE.name().toLowerCase().endsWith(fileName.substring(fileName.lastIndexOf(IConstProperty.DOT) + 1).toLowerCase()))
					return EE;
			}
		}
		return EExtension.Invalid;
	}

	public static String format()
	{
		return EWrap.Brace.enclose((Object[]) EExtension.values());
	}
}