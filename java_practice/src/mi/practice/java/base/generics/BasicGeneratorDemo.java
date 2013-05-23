package mi.practice.java.base.generics;

import mi.practice.java.base.util.BasicGenerator;
import mi.practice.java.base.util.Generator;

public class BasicGeneratorDemo {
	public static void main(String[] args){
//		Generator<CountedObject> gen = BasicGenerator.create(CountedObject.class);
		Generator<CountedObject> gen=new BasicGenerator<CountedObject>(CountedObject.class);
		for(int i=0;i<5;i++){
			System.out.println(gen.next());
		}
	}
}
