package org.hbs.core.security.resource;

import org.hbs.core.util.EnumInterface;

public interface EnumInterfaceRolePath extends EnumInterface
{
	public String getPath();

	public IERole[] getRoles(); // Map Only Roles

}
