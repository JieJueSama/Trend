package cn.how2j.trend.job;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.how2j.trend.pojo.Index;
import cn.how2j.trend.service.IndexDataService;
import cn.how2j.trend.service.IndexService;
import cn.hutool.core.date.DateUtil;

public class IndexDataSyncJob extends QuartzJobBean {
	
	@Autowired
	private IndexService indexService;
	
	@Autowired
	private IndexDataService indexDataService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("定时启动：" + DateUtil.now());
		//同时刷新指数代码和指数数据
		List<Index> indexes = indexService.fresh();
		for(Index index : indexes) {
			indexDataService.fresh(index.getCode());
		}
		System.out.println("定时结束");
		
	}

}
