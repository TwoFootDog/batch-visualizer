//package edu.project.config.schedule;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class BatchVisualizerSchedule {
//
//    private final JobLauncher jobLauncher;
//    private final Job job;
//
//    @Autowired
//    public BatchVisualizerSchedule(JobLauncher jobLauncher, @Qualifier("BatchVisualizerJob") Job job) {
//        this.jobLauncher = jobLauncher;
//        this.job = job;
//    }
//
//    //    @Scheduled(cron = "0 * * * * *")
//    //    @Scheduled(fixedDelay = 2000)
//    //    @Scheduled(cron = "0 * * * * *")
////    @Scheduled(fixedDelayString = "${job.cron.delay}")
//    public void runJob() throws Exception{
//        JobParameters param = new JobParametersBuilder()
//                .addDate("requestDate", new Date())
//                .toJobParameters();
//        JobExecution execution = jobLauncher.run(job, param);
//    }
//}
