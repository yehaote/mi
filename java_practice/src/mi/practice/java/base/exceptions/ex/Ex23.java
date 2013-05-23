package mi.practice.java.base.exceptions.ex;
class FailingConstructorEx23{
	@SuppressWarnings("unused")
	private ShouldDisposeEx23 dispose1;
	@SuppressWarnings("unused")
	private ShouldDisposeEx23 dispose2;
	int[] ints=new int[2];
	public FailingConstructorEx23() throws ConstructionException{
		dispose1 = new ShouldDisposeEx23();
		try{
			ints[2]=1;
			dispose2 = new ShouldDisposeEx23();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

class ShouldDisposeEx23{
	public void dispose(){
		System.out.println("ShouldDispose is dispose()");
	}
}

@SuppressWarnings("serial")
class ConstructionExceptionEx23 extends Exception{}

public class Ex23 {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		try{
			FailingConstructorEx23 fc = new FailingConstructorEx23();
		}catch(ConstructionException e){
			System.out.println(e);
//			throw e;
		}
	}
}