package com.github.becauseQA.thread;

import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

public class ReentrantLockTest {

	ReentrantLock lock = new ReentrantLock(true);

	/*
	 * You can use reentrant locks with a fairness policy or timeout to avoid
	 * thread starvation. You can apply a thread fairness policy. it will help
	 * avoid a thread waiting forever to get to your resources. The
	 * "fairness policy" picks the next runnable thread to execute. It is based
	 * on priority, time since last run reentrantlock is high level than
	 * synchronized
	 */
	@Test
	public void test() {
		lock.lock();
		try {

		} finally {
			lock.unlock();
		}
	}

}
