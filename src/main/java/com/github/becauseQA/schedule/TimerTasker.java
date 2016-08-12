package com.github.becauseQA.schedule;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTasker extends TimerTask {

	private int time = 0;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		time++;
		try {
			Thread.sleep(6*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("schedule to run it time: " +LocalDateTime.now()+": "+ time);
		//cancel();
	}

	public static void main(String[] args) {
		
		Timer timer=new Timer(false);
		TimerTasker task=new TimerTasker();
		timer.schedule(task, 0,5*1000);
		
		// long job will block the next job execution
		// if the job met exception then next job will not run
	}
}
