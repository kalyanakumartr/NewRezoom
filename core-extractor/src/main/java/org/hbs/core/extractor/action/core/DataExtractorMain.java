package org.hbs.core.extractor.action.core;

import org.hbs.core.extractor.action.email.InBoxReaderEmailFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = { "org.hbs" })
@EntityScan(basePackages = { "org.hbs" })
@ComponentScan({ "org.hbs" })
@PropertySources({ @PropertySource("classpath:application.properties") })
@EnableJpaRepositories(basePackages = { "org.hbs" })
@EnableScheduling
public class DataExtractorMain implements CommandLineRunner
{

	public static void main(String[] args) throws Exception
	{
		SpringApplication app = new SpringApplication(DataExtractorMain.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.setWebApplicationType(WebApplicationType.NONE);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception
	{
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}

	@Bean
	public InBoxReaderEmailFactory getInBoxReaderEmailFactory()
	{
		return InBoxReaderEmailFactory.getInstance();
	}
}
