/**
 * 
 */
package com.cognizant.animalsearchapp.rest.config;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Syamala
 *
 */
@Configuration
@ComponentScan("com.cognizant.animalsearchapp.rest")
@EnableWebMvc
@PropertySource("classpath:config.properties")
public class AppConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment environment;

	@Override
	/*
	 * configureMessageConverters() : This method configures
	 * HttpMessageConverters that reads and writes request and response body.
	 * Jackson2ObjectMapperBuilder : This is a builder to create ObjectMapper
	 * instance. MappingJackson2XmlHttpMessageConverter: It is used to read and
	 * write XML.
	 * 
	 */
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.xml();
		builder.indentOutput(true);
		converters.add(new MappingJackson2XmlHttpMessageConverter(builder.build()));
	}

	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getProperty("db.driver"));
		dataSource.setUrl(environment.getProperty("db.url"));
		dataSource.setUsername(environment.getProperty("db.userid"));
		dataSource.setPassword(environment.getProperty("db.password"));
		return dataSource;
	}
}
