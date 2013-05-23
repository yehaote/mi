package mi.practice.java.base.generics.coffee;

import java.util.Iterator;
import java.util.Random;

import mi.practice.java.base.util.Generator;
/**
 * 所有的咖啡扩展类使用同一个id生成器,
 * Coffee.counter
 * @author waf
 */
public class CoffeeGenerator implements Generator<Coffee>, Iterable<Coffee>{
	@SuppressWarnings("rawtypes")
	private Class[] types={Americano.class, Breve.class, Cappuccino.class, Latte.class, Mocha.class};
	private Random rand = new Random(47);
	public CoffeeGenerator(){
	}
	private int size=0;
	public CoffeeGenerator(int sz){
		size=sz;
	}
	
	@Override
	public Coffee next() {
		//反射生成实例
		try {
			return (Coffee) types[rand.nextInt(types.length)].newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	class CoffeeIterator implements Iterator<Coffee>{
		int count=size;
		
		@Override
		public boolean hasNext() {
			return count>0;
		}

		@Override
		public Coffee next() {
			count--;
			//.this 的用法
			//调用外部方法
			return CoffeeGenerator.this.next();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Iterator<Coffee> iterator() {
		return new CoffeeIterator();
	}
	
	public static void main(String[] args){
		CoffeeGenerator gen = new CoffeeGenerator();
		for(int i=0;i<5;i++){
			System.out.println(gen.next());
		}
		System.out.println();
		for(Coffee c : new CoffeeGenerator(5)){
			System.out.println(c);
		}
	}

}
