package mi.practice.java.base.concurrency;

/**
 * 抽象类IntGenerator,
 * 有一个抽象的next()方法返回一个int,
 * 还有一个状态canceled,
 * 代表对象的当前在状态.
 */
public abstract class IntGenerator {
	// 注意volatile, boolean is atomic?
	private volatile boolean canceled = false;

	public abstract int next();

	public void cancel() {
		canceled = true;
	}

	public boolean isCanceled() {
		return canceled;
	}

}
