package mi.practice.java.base.concurrency;
/**
 * javap -c Atomicity
 * 看源文件可以发现每个指令都有一个get和put组成
 */
public class Atomicity {
	int i;

	void f1() {
		i++;
	}

	void f2() {
		i += 3;
	}
//	void f3(){
//		i=3;
//	}
}
