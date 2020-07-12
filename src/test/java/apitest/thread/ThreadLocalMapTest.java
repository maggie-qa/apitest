package com.testfan.thread;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalMapTest {
	
	static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>(){

		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<String, Object>();
		}
	};
	public static void main(String[] args) {
		
		threadLocal.get().put("1","11");
		dosomethings();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				threadLocal.get().put("1","22");
				dosomethings();
			}
		}).start();
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				threadLocal.get().put("1","33");
				dosomethings();
			}
		}).start();
	}
	
	private static void dosomethings() {
		System.out.println(Thread.currentThread().getName() + "  " +threadLocal.get());
	}

}
