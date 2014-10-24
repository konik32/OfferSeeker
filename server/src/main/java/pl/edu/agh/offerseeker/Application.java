package pl.edu.agh.offerseeker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import pl.edu.agh.offerseeker.batch.Scheduler;
import pl.edu.agh.offerseeker.config.BatchConfig;
import pl.edu.agh.offerseeker.config.PageProcessorConfig;
/**
 * 
 * @author Szymon Konicki
 *
 */

@Configuration
@ComponentScan
@EnableAutoConfiguration
@Import({PageProcessorConfig.class,BatchConfig.class, Scheduler.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	
	
	
	

}
