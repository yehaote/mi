package mi.practice.java.base.util;

import java.util.HashMap;
import java.util.Map;
/**
 * 根据基类去计数, 
 * 整个计数器包含基类及其子类的type为key, 
 * value是其数值
 * @author waf
 */
@SuppressWarnings("serial")
public class TypeCounter extends HashMap<Class<?>, Integer> {
	private Class<?> baseType;
	public TypeCounter(Class<?> baseType){
		this.baseType=baseType;
	}
	public void count(Object obj){
		Class<?> type= obj.getClass();
		if(!baseType.isAssignableFrom(type)){
			throw new RuntimeException(obj +" incorrect type: "+type+", should be type or subtype of "+baseType);
		}
		countClass(type);
	}
	
	/**
	 * 对type进行计数, 
	 * 并获取自己的父类Class进行递归调用
	 * @param type
	 */
	private void countClass(Class<?> type){
		Integer quantity =get(type);
		put(type, quantity == null ? 1 : quantity+1);
		Class<?> superClass = type.getSuperclass();
		if(superClass !=null && baseType.isAssignableFrom(superClass)){
			countClass(superClass);
		}
	}
	
	@Override
	public String toString(){
		StringBuilder result = new StringBuilder("{");
		for(Map.Entry<Class<?>, Integer> pair:entrySet()){
			result.append(pair.getKey().getSimpleName());
			result.append("=");
			result.append(pair.getValue());
			result.append(", ");
		}
		result.delete(result.length()-2, result.length());
		result.append("}");
		return result.toString();
	}
}
