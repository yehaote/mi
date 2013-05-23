package mi.practice.java.base.innerclasses.ex.ex6;

import mi.practice.java.base.innerclasses.ex.ex6.i.Ex6Interface;

public class Ex6Demo {
	
	protected class Ex6Inner implements Ex6Interface{
		public Ex6Inner() {
		}

		@Override
		public void say() {
			System.out.println("Ex6Demo.Inner.say()");
		}
		
	}
}
