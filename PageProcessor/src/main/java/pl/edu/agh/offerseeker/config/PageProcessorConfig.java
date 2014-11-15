package pl.edu.agh.offerseeker.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.edu.agh.offerseeker.service.GumtreePageProcessor;
import pl.edu.agh.offerseeker.service.IPageProcessor;
import pl.edu.agh.offerseeker.service.SVMPageProcessor;

@Configuration
public class PageProcessorConfig {
	
	@Value("${offerseeker.pageprocessor.dbpath}")
	private String pageProcessorDbPath;
	
	@Bean
	public IPageProcessor pageProcessor() throws IOException{
        return new SVMPageProcessor(pageProcessorDbPath);
    }
}
