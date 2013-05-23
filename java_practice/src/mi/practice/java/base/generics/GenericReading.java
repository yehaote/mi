package mi.practice.java.base.generics;

import java.util.Arrays;
import java.util.List;
/**
 * 泛型边界读取多用extends,
 * 写入使用super
 * @author waf
 */
public class GenericReading {
	static <T> T readExact(List<T> list){
		return list.get(0);
	}
	static List<Apple> apples = Arrays.asList(new Apple());
	static List<Fruit> fruits = Arrays.asList(new Fruit());
	static void f1(){
		@SuppressWarnings("unused")
		Apple a = readExact(apples);
		@SuppressWarnings("unused")
		Fruit f = readExact(fruits);
		f = readExact(apples);
	}
	static class Reader<T>{
		T readExact(List<T> list){
			return list.get(0);
		}
	}
	static void f2(){
		Reader<Fruit> fruitReader = new Reader<Fruit>();
		@SuppressWarnings("unused")
		Fruit f = fruitReader.readExact(fruits);
//		Fruit a = fruitReader.readExact(apples); //Error
		// readExacrt(List<Fruit>)不能被用在List<Apple>上
	}
	static class CovariantReader<T>{
		T readCovariant(List<? extends T> list){
			return list.get(0);
		}
	}
	/**
	 * 只用实例化一个reader, 
	 * 并可以对apples, fruits进行读取
	 */
	static void f3(){
		CovariantReader<Fruit> fruitReader =  new CovariantReader<Fruit>();
		@SuppressWarnings("unused")
		Fruit f = fruitReader.readCovariant(fruits);
		@SuppressWarnings("unused")
		Fruit a = fruitReader.readCovariant(apples);
	}
	public static void main(String[] args){
		f1();
		f2();
		f3();
	}
}
