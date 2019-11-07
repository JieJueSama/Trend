package cn.how2j.trend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import cn.how2j.trend.pojo.Index;
import cn.how2j.trend.util.SpringContextUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;

@Service
//表示缓存的名称是indexes
@CacheConfig(cacheNames="indexes")
public class IndexService {
	
	private List<Index> indexes;
	
	//使用RestTemplate获取地址
	@Autowired
	RestTemplate restTemplate;
	
	

	//表示如果fetch_indexes_from_third_part获取失败，就自动调用third_part_not_connected
	@HystrixCommand(fallbackMethod = "third_part_not_connected")
	public List<Index> fetch_indexes_from_third_part(){
		List<Map> temp = restTemplate.getForObject("http://127.0.0.1:8090/indexes/codes.json", List.class);
		return map2Index(temp);
	}
	
	public List<Index> third_part_not_connected(){
		System.out.println("third_part_not_connected()");
		Index index = new Index();
		index.setCode("000000");
		index.setName("无效指数代码");
		return CollectionUtil.toList(index);
	}
	
	
	//获取出来的内容是Map类型  把这个Map转换为Index
	private List<Index> map2Index(List<Map> temp){
		List<Index> indexes = new ArrayList<Index>();
		for(Map map : temp) {
			String code = map.get("code").toString();
			String name = map.get("name").toString();
			Index index = new Index();
			index.setCode(code);
			index.setName(name);
			indexes.add(index);
		}
		
		return indexes;
	}
	
	//清空数据
	@CacheEvict(allEntries = true)
	public void remove() {
		
	}
	
	//获取数据，这个专门用来从redis中获取数据
	@Cacheable(key="'all_codes'")
	public List<Index> get(){
		return CollUtil.toList();
	}
	
	//保存数据  这个专门用来往redis里保存数据
	//注意  indexes是一个成员变量
	@Cacheable(key="'all_codes'")
	public List<Index> store(){
		return indexes;
	}
	
	
	//刷新数据
	//刷新的思路是：先运行fetch_indexes_from_third_part()获取数据
	//				删除数据
	//				保存数据
	//从而达到刷新的效果	
	@HystrixCommand(fallbackMethod = "third_part_not_connected")
	public List<Index> fresh(){
		indexes = fetch_indexes_from_third_part();
		IndexService indexService = SpringContextUtil.getBean(IndexService.class);
		indexService.remove();
		return indexService.store();
	}
	
	
	
	

}
