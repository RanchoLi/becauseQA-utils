package com.github.becauseQA.thread;

public class JoinThreadTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Jointask jointask = new Jointask();
		jointask.start();

		try {
			jointask.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Waits for this thread to die.
			// An invocation of this method behaves in exactly the same way as
			// the
			// invocation
		System.out.println("Current is main thread: " + Thread.currentThread().hashCode());
	}

}

class Jointask extends Thread {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Current thread is" + Thread.currentThread().hashCode());
	}
}
