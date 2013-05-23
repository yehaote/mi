package mi.practice.java.base.holding.ex;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Ex17 {
	public static void main(String[] args){
		Map<String, Gerbil> gerbils = new HashMap<String, Gerbil>();
		gerbils.put("Fuzzy", new Gerbil(1));
		gerbils.put("Spot", new Gerbil(2));
		Iterator<String> it = gerbils.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			Gerbil gerbil = gerbils.get(key);
			System.out.println(gerbil);
			gerbil.hop();
		}
	}
}
