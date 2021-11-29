package com.zendesk.viewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ViewerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ViewerApplication.class, args);
	}
}
