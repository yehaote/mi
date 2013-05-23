package mi.practice.java.base.innerclasses;

import static mi.practice.java.base.util.Print.*;

interface Service {
	void method1();

	void method2();
}

interface ServiceFactory {
	Service getService();
}

class Implementation1 implements Service {
	private Implementation1() {// 设置成private防止用构造函数初始化实例
	}

	@Override
	public void method1() {
		print("Implementation1 method1()");
	}

	@Override
	public void method2() {
		print("Implementation1 method2()");
	}

	public static ServiceFactory factory = new ServiceFactory() {
		@Override
		public Service getService() {
			return new Implementation1();
		}
	};
}

class Implementation2 implements Service {
	private Implementation2() {
	}

	@Override
	public void method1() {
		print("Implementation2 method1()");
	}

	@Override
	public void method2() {
		print("Implementation2 method2()");
	}

	public static ServiceFactory factory = new ServiceFactory() {
		@Override
		public Service getService() {
			return new Implementation2();
		}
	};
}

public class Factories {
	public static void serviceConsumer(ServiceFactory fact) {
		Service service = fact.getService();
		service.method1();
		service.method2();
	}

	public static void main(String[] args) {
		serviceConsumer(Implementation1.factory);
		serviceConsumer(Implementation2.factory);
	}
}
