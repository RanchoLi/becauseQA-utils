package com.github.becausetesting.schedule;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ScheduleTasker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3, new ThreadFactory() {
			
			@Override
			public Thread newThread(Runnable runnable) {
				// TODO Auto-generated method stub
				Thread thread = Executors.defaultThreadFactory().newThread(runnable);
				thread.setDaemon(false);
				return thread;
			}
		});
		 executorService.scheduleAtFixedRate(new Runnable() {
			
			public void run() {
				System.out.println("time: " + LocalDateTime.now());
				try {
					Thread.sleep(7*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 0, 5 * 1000, TimeUnit.MILLISECONDS);

	

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				executorService.shutdownNow();
			}
			
		});
	}

}
