package cn.how2j.trend.config;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.how2j.trend.job.IndexDataSyncJob;

@Configuration
public class QuartzConfiguration {

	//定时器配置  每隔一分钟执行一次
	private static final int interval = 1;
	
	@Bean
	public JobDetail weatherDataSyncJobDetail() {
		return JobBuilder.newJob(IndexDataSyncJob.class).withIdentity("indexDataSyncJob").storeDurably().build();
	}
	
	
	@Bean
	public Trigger weatherDataSyncTrigger() {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(interval).repeatForever();
		
		return TriggerBuilder.newTrigger().forJob(weatherDataSyncJobDetail())
				.withIdentity("indexDataSyncTrigger").withSchedule(scheduleBuilder).build();
	}
}
