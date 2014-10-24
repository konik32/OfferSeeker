package pl.edu.agh.offerseeker.batch;

import java.util.Collections;
import java.util.Date;

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
/**
 * 
 * Simple class for job scheduling. To enable set offerseeker.schedule property to true
 * 
 * @author Szymon Konicki
 *
 */
@EnableScheduling
@ConditionalOnProperty(name="offerseeker.schedule")
public class Scheduler {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job processPossibleOffersJob;
	
	/**
	 * Schedules processPossibleOffersJob responsible for processing possible offers urls into offers
	 * 
	 * @throws JobExecutionAlreadyRunningException
	 * @throws JobRestartException
	 * @throws JobInstanceAlreadyCompleteException
	 * @throws JobParametersInvalidException
	 */
	@Scheduled(cron="${offerseeker.schedule.pageProccessing.cron}")
	public void schedulePageProcessing() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException{
		jobLauncher.run(processPossibleOffersJob, new JobParameters(Collections.singletonMap("lastJobDate", new JobParameter(new Date()))));
	}

}
