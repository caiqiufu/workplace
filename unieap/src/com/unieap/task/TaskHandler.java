package com.unieap.task;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TaskHandler {
	@Pointcut("execution(* taskExecute())")
	public void proxyAspect() {
	}
	@Around("proxyAspect()")
    public Object doInvoke(ProceedingJoinPoint joinPoint) throws Throwable{
		Object target = joinPoint.getTarget();
		if (target instanceof TaskService) {
			TaskService taskService = (TaskService) target;
			String taskCode = taskService.getTaskCode();
			int taskTimeDuration = taskService.getTaskTimeDuration();
			taskService.lockTask(taskCode, taskTimeDuration);
			Object result = joinPoint.proceed();
			taskService.unLockTask(taskCode);
			return result;
		}else{
			return null;
		}
	}
	/*@Before("proxyAspect()")
	public void beforeExecute() throws Throwable {
		System.out.println("睡觉前要脱衣服!");
	}

	@AfterReturning("proxyAspect()")
	public void afterExecute() throws Throwable {
		System.out.println("睡醒了要穿衣服！");
	}*/
}
