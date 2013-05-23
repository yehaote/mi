package mi.practice.java.base.generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * 当在Java中, 已经实现的类没有实现同一的接口的时候,
 * 可是又不能增加接口, 比如iterator什么的, 特别是跟集合有关的.
 * 这个时候就没有很好的办法, 除了Latent typing.
 * @author waf
 */
public class Fill {
	public static <T>
		void fill(Collection<T> collection, Class<? extends T> classToken, int size){
		for(int i=0;i<size;i++){
			try{
				collection.add(classToken.newInstance());
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}
	}
	
	public static void main(String[] args){}
}

class Contract{
	private static long counter=0;
	private final long id = counter++;
	public String toString(){
		return getClass().getName() + id;
	}
}

class TitleTransfer extends Contract{}

class FillTest{
	public static void main(String[] args){
		List<Contract> contracts = new ArrayList<Contract>(); 
		Fill.fill(contracts, Contract.class, 3);
		Fill.fill(contracts, TitleTransfer.class, 2);
		for(Contract c : contracts){
			System.out.println(c);
		}
		
		@SuppressWarnings("unused")
		SimpleQueue<Contract> contractQueue = new SimpleQueue<Contract>();
		//不能编译, 因为fill()方法限制为Collection
//		Fill.fill(contractQueue, Contract.class, 5);
	}
}
