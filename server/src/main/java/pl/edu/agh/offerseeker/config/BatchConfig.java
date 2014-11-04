package pl.edu.agh.offerseeker.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.domain.Sort.Direction;

import pl.edu.agh.offerseeker.commons.model.PossibleOfferLink;
import pl.edu.agh.offerseeker.domain.Offer;
import pl.edu.agh.offerseeker.job.PossibleOfferProcessor;
import pl.edu.agh.offerseeker.repository.OfferRepository;
import pl.edu.agh.offerseeker.repository.PossibleOfferLinkRepository;

/**
 * 
 * @author Szymon Konicki
 *
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {

	@Autowired
	private PossibleOfferLinkRepository possibleOfferLinkRepository;

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Bean
	public JobLauncher JobLauncher(){
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
		return jobLauncher;
	}

	/**
	 * 
	 * Retrieves {@link PossibleOfferLink}s by chunks. Chunk size - 100
	 * 
	 * @param lastJobDate
	 * @return
	 */
			
	@Bean(name="possibleOfferLinkItemReader")
	@JobScope
	public ItemReader<PossibleOfferLink> possibleOfferLinkItemReader(@Value("#{jobParameters[lastJobDate]}") Date lastJobDate) {
		RepositoryItemReader<PossibleOfferLink> reader = new RepositoryItemReader<PossibleOfferLink>();
		reader.setRepository(possibleOfferLinkRepository);
		reader.setMethodName("findByTimestampAfter");
		reader.setArguments(Arrays.asList(lastJobDate));
		reader.setSort(Collections.singletonMap("timestamp", Direction.DESC));
		reader.setPageSize(100);
		return reader;
	}
	
	/**
	 * 
	 * Writes {@link Offer}s by chunks.
	 * 
	 * @param lastJobDate
	 * @return
	 */

	@Bean(name="offerWriter")
	public ItemWriter<Offer> offerWriter() {
		RepositoryItemWriter<Offer> writer = new RepositoryItemWriter<Offer>();
		writer.setRepository(offerRepository);
		writer.setMethodName("save");
		return writer;
	}
	
	@Bean
	public PossibleOfferProcessor processor(){
		return new PossibleOfferProcessor();
	}

	@Bean(name="processPossibleOffersJob")
	public Job processPossibleOffersJob(Step possibleOfferProcessingStep) {
		return jobs.get("processPossibleOffers").start(possibleOfferProcessingStep).build();
	}

	@Bean(name="possibleOfferProcessingStep")
	public Step possibleOfferProcessingStep(StepBuilderFactory stepBuilderFactory, ItemReader<PossibleOfferLink> possibleOfferLinkItemReader, ItemWriter<Offer> offerWriter,
			PossibleOfferProcessor processor) {
		return steps.get("possibleOfferProcessingStep").<PossibleOfferLink, Offer> chunk(100).reader(possibleOfferLinkItemReader).processor(processor).writer(offerWriter)
				.build();
	}

}
