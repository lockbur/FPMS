package com.forms.prms.web.cluster;

import java.lang.reflect.Method;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.web.cluster.lock.service.ClusterLockService;

@Aspect
@Component
public class LockAspect {
	@Autowired
	private ClusterLockService cService;

	@Around("within(com.forms.prms.tool.fms..*||com.forms.prms.web.amortization..*||com.forms.dealdata.download..*) && @annotation(com.forms.prms.web.cluster.Lock)")
	public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
		/*************** 1、获取方法 *************************/
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		Lock lock = method.getAnnotation(Lock.class);

		/*************** 2、转换SpEL表达式 *************************/
		ExpressionParser parser = new SpelExpressionParser();
		EvaluationContext context = new StandardEvaluationContext();
		if (ArrayUtils.isNotEmpty(pjp.getArgs())) {
			for (int i = 0; i < pjp.getArgs().length; i++) {
				context.setVariable("p" + i, pjp.getArgs()[i]);
			}
			// 获取形式参数名称
			ParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
			String[] strs = discoverer.getParameterNames(method);
			if (ArrayUtils.isNotEmpty(strs)) {
				for (int i = 0; i < strs.length; i++) {
					context.setVariable(strs[i], pjp.getArgs()[i]);
				}
			}
		}
		if (StringUtils.isEmpty(lock.taskType()) || StringUtils.isEmpty(lock.taskSubType())
				|| StringUtils.isEmpty(lock.instOper())) {
			throw new Exception("参数有误:" + lock);
		}
		String taskType = parser.parseExpression(lock.taskType()).getValue(context, String.class);
		String taskSubType = parser.parseExpression(lock.taskSubType()).getValue(context, String.class);
		String instOper = parser.parseExpression(lock.instOper()).getValue(context, String.class);
		String memo = "";
		if (StringUtils.isNotEmpty(lock.memo())) {
			memo = parser.parseExpression(lock.memo()).getValue(context, String.class);
		}

		/*************** 3、拦截方法，进行锁管理 *************************/
		Object ob = null;
		boolean flag = false; // 是否加锁成功
		try {
			if(!cService.isLock(taskType, taskSubType))
			{
				flag = cService.addLock(taskType, taskSubType, instOper, memo);
				if(flag){
					ob = pjp.proceed();
				}
			}
			if (!flag) 
			{
				CommonLogger.info("其他节点已获取同步锁");
				if(!"SYSTEM".equals(instOper))//页面触发，如果加锁失败，抛出异常
				{
					throw new Exception(memo + "加锁失败");
				}
			}
		} finally {
			if (flag) {
				cService.removeLock(taskType, taskSubType, instOper);
			}
		}
		return ob;
	}
}
