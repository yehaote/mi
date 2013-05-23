package mi.practice.java.base.exceptions.ex;
/**
 * 可以捕获到ExC
 * 哪怕把C upcast to A进行调用
 * @author waf
 *
 */
@SuppressWarnings("serial")
class ExA extends Exception{}
@SuppressWarnings("serial")
class ExB extends ExA{}
@SuppressWarnings("serial")
class ExC extends ExB{}

class A{
	void say()throws ExA{
		throw new ExA();
	}
}

class B extends A{
	@Override
	void say() throws ExB{
		throw new ExB();
	}
}
class C extends B{
	@Override
	void say() throws ExC{
		throw new ExC();
	}
}

public class Ex25 {
	public static void main(String[] args){
		A a = new C();
		try {
			a.say();
		}catch(ExC e){
			e.printStackTrace();
		}catch(ExB e){
			e.printStackTrace();
		}catch (ExA e) {
			e.printStackTrace();
		}
	}
}
