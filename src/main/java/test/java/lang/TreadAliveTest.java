package test.java.lang;

import java.util.concurrent.TimeUnit;

public class TreadAliveTest {

	public static void main(String[] args) throws InterruptedException {
		MyThread myThread = new MyThread();
		myThread.start();
		TimeUnit.SECONDS.sleep(5);
		System.out.println(myThread);
		System.out.println(myThread.isAlive());
		TimeUnit.SECONDS.sleep(10);
		System.out.println(myThread);
		System.out.println(myThread.isAlive());
		TimeUnit.SECONDS.sleep(30);
	}
	
	
	
	private static class MyThread extends Thread {
		@Override
		public void run() {
			int i = 1;
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("这是主线程第 " + (i++) + " 次打印");
				try {
					throw new OutOfMemoryError("123");
				} catch (OutOfMemoryError e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static class DemonThread extends Thread {
		private Thread thread;

		public void setThread(Thread thread) {
			this.thread = thread;
		}
		
		@Override
		public void run() {
			int i = 1;
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (!thread.isAlive()) {
					
				}
				System.out.println("这是守护线程第 " + (i++) + " 次检测");
			}
		}
	}

}
