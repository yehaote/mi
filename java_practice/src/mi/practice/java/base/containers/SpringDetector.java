package mi.practice.java.base.containers;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
/**
 * 如果没有覆盖hashCode的时候, 如以下例子,
 * 在hashMap中找不到我们主观上认为是一样的key.
 * */
public class SpringDetector {
	public static <T extends Groundhog>
		void detectSpring(Class<T> type) throws Exception{
		Constructor<T>	ghog = type.getConstructor(int.class);
		Map<Groundhog, Prediction> map = new HashMap<Groundhog, Prediction>();
		for(int i=0; i< 10; i++){
			map.put(ghog.newInstance(i),new Prediction());
		}
		System.out.println("map = "+map);
		Groundhog gh = ghog.newInstance(3);
		System.out.println("Looking up predicition for "+gh);
		if(map.containsKey(gh)){
			System.out.println(map.get(gh));
		}else{
			System.out.println("Key not found: "+gh);
		}
	}
	
	public static void main(String[] args) throws Exception{
		detectSpring(Groundhog.class);
	}
}
