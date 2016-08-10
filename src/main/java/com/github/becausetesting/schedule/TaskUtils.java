package com.github.becausetesting.schedule;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskUtils {

	public static void runBackgroundTask(Runnable runnable, int intervalMills) {
		ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
		newSingleThreadScheduledExecutor.scheduleAtFixedRate(runnable, 0, intervalMills, TimeUnit.MILLISECONDS);
	}

	public static void runBackgroundTask(TimerTask task, int intervalMills) {
		Timer timer = new Timer(false);
		timer.scheduleAtFixedRate(task, 0, intervalMills);
	}
}
