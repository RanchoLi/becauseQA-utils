package com.github.becausetesting.schedule;

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

			public void run() {
				//time++;
				System.out.println("Run Time: "+1+",at "+LocalDateTime.now());
			}
		}, 0, 5, TimeUnit.SECONDS);
		
		
	}

}
