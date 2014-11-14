package pl.edu.agh.offerseeker.job;

import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import pl.edu.agh.offerseeker.repository.PossibleOfferLinkRepository;
import scala.sys.process.processInternal;

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
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private JobExplorer jobExplorer;
	
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
//	@Scheduled(fixedRate=10000)
	public void schedulePageProcessing() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException,
			JobParametersInvalidException {
		List<JobInstance> jobInstance = jobExplorer.findJobInstancesByJobName(processPossibleOffersJob.getName(), 0, 1);
		Date startTime = new Date();
		if(jobInstance != null && jobInstance.size() !=0){
			List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstance.get(0));
			if(jobExecutions != null && jobExecutions.size() !=0){
				startTime = jobExecutions.get(0).getStartTime();
			}
		}
		Calendar cal = Calendar.getInstance();
		cal.set(2012, 11, 11);
		startTime = cal.getTime();
		jobLauncher.run(processPossibleOffersJob, new JobParameters(Collections.singletonMap("lastJobDate", new JobParameter(startTime))));
	}

	/**
	 * Schedules {@link CrawlerJob}
	 * @throws MalformedURLException
	 */
	@Scheduled(cron = "${offerseeker.schedule.crawlerJob.cron}")
//	@Scheduled(fixedRate=10000)
	public void scheduleCrawlerJob() throws MalformedURLException {
		crawlerJob.init();
	}

}
