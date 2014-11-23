package pl.edu.agh.offerseeker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.agh.offerseeker.service.IPageProcessor;
import pl.edu.agh.offerseeker.service.SVMPageProcessor;

import java.io.IOException;

@Configuration
public class PageProcessorConfig {
	
	@Value("${offerseeker.pageprocessor.dbpath}")
	private String pageProcessorDbPath;
    @Value("${offerseeker.pageprocessor.usingsvm}")
    private Boolean pageProcessorUsingSvm;

    @Bean
    public IPageProcessor pageProcessor() throws IOException{
        return new SVMPageProcessor(pageProcessorDbPath, pageProcessorUsingSvm);
    }
}
