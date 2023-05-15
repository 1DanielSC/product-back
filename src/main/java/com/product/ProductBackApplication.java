package com.product;

import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//import brave.sampler.Sampler;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableFeignClients
public class ProductBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductBackApplication.class, args);
	}

	// @Bean
	// public Sampler defaultSampler(){
	// 	return Sampler.ALWAYS_SAMPLE;
	// }

	// @Bean
	// public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
	// 	return protocolHandler -> protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
	// }

	// @LoadBalanced
	// @Bean
	// public RestTemplate getRestTemplate(){
	// 	return new RestTemplate();
	// }
}
