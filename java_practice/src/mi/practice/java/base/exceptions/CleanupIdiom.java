package mi.practice.java.base.exceptions;
class NeedsCleanup{
	private static long count=1;
	private final long id = count++;
	public void dispose(){
		System.out.println("NeedsCleanup "+id+" disposed");
	}
}

@SuppressWarnings("serial")
class ConstructionException extends Exception{}

class NeedsCleanup2 extends NeedsCleanup{
	public NeedsCleanup2() throws ConstructionException{}
}

public class CleanupIdiom {
	public static void main(String[] args){
		//构造器不会异常,执行异常的话,finally关闭就好
		NeedsCleanup nc1=new NeedsCleanup();
		try{
			///
		}finally{
			nc1.dispose();
		}
		//多个可以联合
		NeedsCleanup nc2 = new NeedsCleanup();
		NeedsCleanup nc3 = new NeedsCleanup();
		try{
			
		}finally{
			nc3.dispose();//跟构造的顺序相反
			nc2.dispose();
		}
		//如果是构造器也会异常就需要分层处理, 必须保证每一个类都正常初始化
		try{
			NeedsCleanup2 nc4 =new NeedsCleanup2();
			try{
				NeedsCleanup2 nc5 = new NeedsCleanup2();
				try{
					//
				}finally{
					nc5.dispose();
				}
			}catch(ConstructionException e){
				System.out.println(e);
			}finally{
				nc4.dispose();
			}
		}catch (ConstructionException e) {
			System.out.println(e);
		}
		
	}
}
