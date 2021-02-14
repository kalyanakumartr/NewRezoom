package org.hbs.core.bean.path;

import org.hbs.core.security.resource.IPath.ERole;
import org.hbs.core.util.EnumInterface;

public interface EnumResourceInterface extends EnumInterface
{
	public String getPath();

	public ERole[] getRoles();

}
