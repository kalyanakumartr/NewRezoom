package org.hbs.security.resource;

import org.hbs.core.util.CommonValidator;
import org.hbs.core.util.IConstProperty;
import org.hbs.v7.reader.action.webupload.IPathWebUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer, IConstProperty, IPathWebUpload
{

	private static final long	serialVersionUID	= -7514925071729240560L;

	@Value("${application.physical.paths}")
	private String				applicationPhysicalPaths;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		if (CommonValidator.isNotNullNotEmpty(applicationPhysicalPaths) && applicationPhysicalPaths.contains(HASH))
		{
			String[] resource = null;
			for (String resources : applicationPhysicalPaths.split(COMMA_SPACE.trim()))
			{
				resource = resources.split(HASH);
				registry.addResourceHandler(SLASH + resource[0].trim() + SLASH_STARS).addResourceLocations(resource[1].trim());
				System.out.println(">>>>>>>V7 Reader >>>resource[0].trim() >>>>>>>>>>>>>>>>>>>> " + resource[0].trim());
				System.out.println(">>>>>>>V7 Reader >>>resource[1].trim() >>>>>>>>>>>>>>>>>>>> " + resource[1].trim());
				
			}
			
		}
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
	{
		configurer.enable();
	}
}
