package mi.practice.java.base.concurrency;

import static mi.practice.java.base.util.Print.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 有的时候去关闭当前线程的IO阻塞,
 * 可以通过关闭连接来释放IO Block
 */
public class CloseResource {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException,
			InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		ServerSocket server = new ServerSocket(8080);
		InputStream socketInput = new Socket("localhost", 8080)
				.getInputStream();
		exec.execute(new IOBlocked(socketInput));
		exec.execute(new IOBlocked(System.in));
		TimeUnit.MILLISECONDS.sleep(100);
		print("Shutting down all threads");
		exec.shutdownNow();
		//如果不关闭io和socket的话, 线程是会一直执行的, 哪怕调用了exec.shutdownNow()
		TimeUnit.SECONDS.sleep(1);
		print("Closing " + socketInput.getClass().getName());
		socketInput.close(); // 释放SocketInput的IOBlocked
		TimeUnit.SECONDS.sleep(1);
		print("Closing " + System.in.getClass().getName());
		System.in.close();
	}
}
