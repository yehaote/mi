package mi.practice.java.base.concurrency;

import java.io.IOException;

/**
 * 使用线程一个最主要的原因是让程序变成可响应
 */
class UnresponsiveUI {
	private volatile double d = 1;

	public UnresponsiveUI() throws IOException {
		while (d > 0) {
			d = d + (Math.PI + Math.E) / d;
		}
		System.in.read(); // Never get here
	}
}

public class ResponsiveUI extends Thread {
	private static volatile double d = 1;

	public ResponsiveUI() {
		setDaemon(true);
		start();
	}

	@Override
	public void run() {
		while (true) {
			d = d + (Math.PI + Math.E) / d;
		}
	}

	public static void main(String[] args) throws IOException {
//		new UnresponsiveUI(); //Must kill this process
		new ResponsiveUI();
		System.in.read();
		System.out.println(d);
	}
}
