package com.github.becauseQA.schedule;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class ScheduleTaskerTest {

	@Test
	public void testLongJobThanintevalTime() {
		ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
		newSingleThreadScheduledExecutor.scheduleAtFixedRate(new Runnable() {
			
			//private int time=0;

			@Override
			public void run() {
				//time++;
				System.out.println("Run Time: "+1+",at "+LocalDateTime.now());
			}
		}, 0, 5, TimeUnit.SECONDS);
		
		
	}

}
