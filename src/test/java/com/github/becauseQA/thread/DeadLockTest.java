package com.github.becauseQA.thread;

public class DeadLockTest {

	/*
	 * https://crunchify.com/how-to-generate-java-deadlock-programmatically-and-
	 * how-to-analyze-deadlock/
	 * 
	 * 
	 * https://crunchify.com/how-to-generate-java-thread-dump-programmatically/
	 * 
	 * https://helpx.adobe.com/experience-manager/kb/TakeThreadDump.html
	 *  export the thread dump file:
	 *  
	 *  1. from task manager to get javaw.exe the pid number;
	 *  2. run command jstack pid >> threaddump.log
	 * 
	 */
	public static void main(String[] args) throws InterruptedException {
		Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
 
        Thread crunchifyThread1 = new Thread(new CrunchifySynchronizeThread(obj1, obj2), "crunchifyThread1");
        Thread crunchifyThread2 = new Thread(new CrunchifySynchronizeThread(obj2, obj3), "crunchifyThread2");
        Thread crunchifyThread3 = new Thread(new CrunchifySynchronizeThread(obj3, obj1), "crunchifyThread3");
 
        crunchifyThread1.start();
        Thread.sleep(3000);
        crunchifyThread2.start();
        Thread.sleep(3000);
        crunchifyThread3.start();
	}

}

class CrunchifySynchronizeThread implements Runnable {
	private Object obj1;
	private Object obj2;

	public CrunchifySynchronizeThread(Object obj1, Object obj2) {
		this.obj1 = obj1;
		this.obj2 = obj2;
	}

	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		synchronized (obj1) {
			System.out.println(name + " acquired lock on Object1: " + obj1);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			synchronized (obj2) {
				System.out.println(name + " acquired lock on Object2: " + obj2);
			}
			System.out.println(name + " released lock on Object2: " + obj2);
		}
		System.out.println(name + " released lock on Object1: " + obj1);
		System.out.println(name + " Finished Crunchify Deadlock Test.");
	}
}