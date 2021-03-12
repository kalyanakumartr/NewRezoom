package org.hbs.security.resource;

import org.hbs.core.security.resource.OAuth2ResourceServerConfigBase;
import org.hbs.v7.reader.action.webupload.IPathWebUpload;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends OAuth2ResourceServerConfigBase implements IPathWebUpload
{

	private static final long serialVersionUID = -209852098747872677L;

	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		configure(http, EPathWebUpload.values());
	}
}
