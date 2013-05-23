package mi.practice.java.base.typeinfo;

import java.util.ArrayList;
import java.util.List;
/**
 * 可以使用class的newInstance()方法来实例化对象,
 * 不过对象必须要有默认的构造函数, 不然会抛出执行期异常
 * @author waf
 */
class CountedInteger{
	private static long counter;
	private final long id= counter++;
	@Override
	public String toString(){
		return Long.toString(id);
	}
}

public class FilledList<T> {
	private Class<T> type;
	public FilledList(Class<T> type){
		this.type=type;
	}
	public List<T> create(int nElements){
		List<T> result=new ArrayList<T>();
		try{
			for(int i=0;i<nElements;i++){
				result.add(type.newInstance());
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	public static void main(String[] args){
		FilledList<CountedInteger> fl = new FilledList<CountedInteger>(CountedInteger.class);
		System.out.println(fl.create(15));
	}
}
