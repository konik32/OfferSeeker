package pl.edu.agh.offerseeker.job;

import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Date;
import javax.persistence.EntityManager;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.edu.agh.offerseeker.repository.PossibleOfferLinkRepository;

/**
 * 
 * Simple class for job scheduling. To enable set offerseeker.schedule property
 * to true
 * 
 * @author Szymon Konicki
 *
 */
@EnableScheduling
@ConditionalOnProperty(value = "offerseeker.schedule")
public class Scheduler {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job processPossibleOffersJob;

	@Autowired
	private CrawlerJob crawlerJob;
	
	@Autowired
	private PossibleOfferLinkRepository repo;
	
	@Autowired
	private EntityManager entityManager;
	
	/**
	 * Schedules processPossibleOffersJob responsible for processing possible
	 * offers urls into offers
	 * 
	 * @throws JobExecutionAlreadyRunningException
	 * @throws JobRestartException
	 * @throws JobInstanceAlreadyCompleteException
	 * @throws JobParametersInvalidException
	 */
	@Scheduled(cron = "${offerseeker.schedule.pageProccessing.cron}")
	public void schedulePageProcessing() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException,
			JobParametersInvalidException {
		jobLauncher.run(processPossibleOffersJob, new JobParameters(Collections.singletonMap("lastJobDate", new JobParameter(new Date()))));
	}

	@Scheduled(cron = "${offerseeker.schedule.crawlerJob.cron}")
	public void scheduleCrawlerJob() throws MalformedURLException {
		crawlerJob.init();
	}

}
