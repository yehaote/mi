package mi.practice.java.base.generics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import mi.practice.java.base.util.Generator;

/**
 * 匿名类跟泛型也能很好的结合在一起使用
 * 私有的构造函数是强制类使用generator进行初始化
 * 
 * @author waf
 */
class Customer {
	private static long counter = 1;
	private final long id = counter++;

	private Customer() {
	}

	@Override
	public String toString() {
		return "Customer " + id;
	}

	// 多个匿名类实例
	public static Generator<Customer> generator() {
		return new Generator<Customer>() {
			@Override
			public Customer next() {
				return new Customer();
			}
		};
	}
}

class Teller {
	private static long counter = 1;
	private final long id = counter++;

	private Teller() {
	}

	@Override
	public String toString() {
		return "Teller " + id;
	}

	// 单个匿名类实例
	public static Generator<Teller> generator = new Generator<Teller>() {
		@Override
		public Teller next() {
			return new Teller();
		}
	};
}

public class BankTeller {
	public static void server(Teller t, Customer c) {
		System.out.println(t + " serves " + c);
	}

	public static void main(String[] args) {
		Random rand = new Random(47);
		Queue<Customer> line = new LinkedList<Customer>();
		Generators.fill(line, Customer.generator(), 15);
		List<Teller> tellers = new ArrayList<Teller>();
		Generators.fill(tellers, Teller.generator, 4);
		for (Customer c : line) {
			server(tellers.get(rand.nextInt(tellers.size())), c);
		}
	}
}
