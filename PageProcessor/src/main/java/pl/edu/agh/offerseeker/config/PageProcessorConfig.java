package pl.edu.agh.offerseeker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.edu.agh.offerseeker.service.IPageProcessor;
import pl.edu.agh.offerseeker.service.PageProcessor;

@Configuration
public class PageProcessorConfig {

	@Bean
	public IPageProcessor pageProcessor(){
		return new PageProcessor();
	}
}
