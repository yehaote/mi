package mi.practice.java.base.generics;

import mi.practice.java.base.util.Generator;

public class Fibonacci implements Generator<Integer>{
	private int count=0;
	@Override
	public Integer next() {
		return fib(count++);
	}
	
	//递归调用生成斐波那契数列
	private int fib(int n){
		if(n<2){
			return 1;
		}
		return fib(n-2)+fib(n-1);
	}
	
	public static void main(String[] args){
		Fibonacci gen = new Fibonacci();
		for(int i=0;i<18;i++){
			System.out.println(gen.next());
		}
	}

}
