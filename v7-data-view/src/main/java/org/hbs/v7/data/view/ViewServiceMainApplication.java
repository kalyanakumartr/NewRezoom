package org.hbs.v7.data.view;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "org.hbs" })
@EntityScan(basePackages = { "org.hbs" })
@ComponentScan({ "org.hbs" })
@PropertySources({ @PropertySource("classpath:application.properties") })
@EnableJpaRepositories(basePackages = { "org.hbs" })
@EnableDiscoveryClient
public class ViewServiceMainApplication extends SpringBootServletInitializer
{

	public static void main(String[] args) throws Exception
	{
		SpringApplication app = new SpringApplicationBuilder(ViewServiceMainApplication.class).sources(ViewServiceMainApplication.class).build();
		app.setWebApplicationType(WebApplicationType.SERVLET);
		app.run(args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.sources(ViewServiceMainApplication.class);
	}
}