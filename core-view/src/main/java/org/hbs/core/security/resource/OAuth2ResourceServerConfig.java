package org.hbs.core.security.resource;

import org.hbs.core.beans.path.IPathView.EPathView;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends OAuth2ResourceServerConfigBase
{

	private static final long serialVersionUID = -8570769847835325242L;

	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		configure(http, EPathView.values());
	}
}
