package cn.how2j.trend.service;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.how2j.trend.pojo.Index;
import cn.hutool.core.collection.CollUtil;

//服务类  直接从redis获取数据
@Service
@CacheConfig(cacheNames="indexes")
public class IndexService {
	
	private List<Index> indexes;
	
	//如果没有数据，则会返回“无效代码指数”
	@Cacheable(key="'all_codes'")
	public List<Index> get(){
		Index index = new Index();
		index.setName("无效代码指数");
		index.setCode("000000");
		return CollUtil.toList(index);
	}

}
