package com.github.becausetesting.thread;

/**
 * Daemo thread will exit when the main thread exit. we often use it's
 * server for main thread ,if main thread user thread exit then it will exit
 * 
 * daemo almost not used sometimes
 * @author ahu
 *
 */
public class DaemonThreadTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WorkerThread workerThread = new WorkerThread();
		workerThread.setName("backgroud thread");
		workerThread.setDaemon(true);
		workerThread.start();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// handle here exception
		}

		System.out.println("Main Thread ending");
	}
}

class WorkerThread extends Thread {

	public WorkerThread() {
		// When false, (i.e. when it's a user thread),
		// the Worker thread continues to run.
		// When true, (i.e. when it's a daemon thread),
		// the Worker thread terminates when the main
		// thread terminates.
		// setDaemon(t);
	}

	public void run() {
		int count = 0;
		while (true) {
			System.out.println("Hello from Worker " + count++);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// handle exception here
			}
		}

	}
}
