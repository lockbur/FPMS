package com.forms.prms.tool.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.springframework.cache.ehcache.EhCacheCacheManager;

import com.forms.platform.core.spring.util.SpringUtil;

public class EhcacheUtil {
	
	public static String prefix_key_sys = "sys_para_";

	public static Cache getBaseCache()
	{
		EhCacheCacheManager ehCacheCacheManager = (EhCacheCacheManager) SpringUtil.getBean("cacheManager");
		CacheManager cacheManager = ehCacheCacheManager.getCacheManager();
		Cache cache = cacheManager.getCache("base");
		return cache;
	}
}
