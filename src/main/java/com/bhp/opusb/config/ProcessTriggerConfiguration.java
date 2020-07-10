package com.bhp.opusb.config;

import com.bhp.opusb.config.factory.ProcessTriggerFactory;

import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessTriggerConfiguration {

  @Bean
  public ServiceLocatorFactoryBean processTriggerFactory() {
    ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
    factoryBean.setServiceLocatorInterface(ProcessTriggerFactory.class);
    return factoryBean;
  }
  
}