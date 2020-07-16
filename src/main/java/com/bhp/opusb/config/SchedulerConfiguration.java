package com.bhp.opusb.config;

import java.util.Properties;

import javax.sql.DataSource;

import com.bhp.opusb.config.factory.AutowiringSpringBeanJobFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class SchedulerConfiguration {

	private final DataSource dataSource;
	private final ApplicationContext applicationContext;
	private final QuartzProperties quartzProperties;

	@Autowired
	public SchedulerConfiguration(DataSource dataSource, ApplicationContext applicationContext,
			QuartzProperties quartzProperties) {
		this.dataSource = dataSource;
		this.applicationContext = applicationContext;
		this.quartzProperties = quartzProperties;
	}
  
	@Bean
	public SchedulerFactoryBean schedulerFactory() {
		SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);

		Properties props = new Properties();
		props.putAll(quartzProperties.getProperties());
		factoryBean.setDataSource(dataSource);
		factoryBean.setJobFactory(jobFactory);
		factoryBean.setQuartzProperties(props);
		factoryBean.setApplicationContextSchedulerContextKey("applicationContext");
		return factoryBean;
	}
}