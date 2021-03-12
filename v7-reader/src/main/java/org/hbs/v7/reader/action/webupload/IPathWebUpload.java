package org.hbs.v7.reader.action.webupload;

import org.hbs.core.security.resource.EnumInterfaceRolePath;
import org.hbs.core.security.resource.IPath;

public interface IPathWebUpload extends IPath
{
	//uploadFileType = Word/Excel/CSV/PDF/ZIP/7z etc
	public String	WEB_UPLOAD				= "/webUpload/{uploadFileType}";

	public enum EPathWebUpload implements EnumInterfaceRolePath
	{
		WebUpload(WEB_UPLOAD, ERole.Administrator, ERole.Employee); 

		String	path;

		ERole	roles[];

		EPathWebUpload(String path, ERole... roles)
		{
			this.path = path;
			this.roles = roles;
		}

		@Override
		public String getPath()
		{
			return this.path;
		}

		@Override
		public ERole[] getRoles()
		{
			return this.roles;
		}
	}

}
