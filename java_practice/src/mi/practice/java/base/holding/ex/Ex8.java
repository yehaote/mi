package mi.practice.java.base.holding.ex;

import java.util.ArrayList;
import java.util.Iterator;

class Ex8Gerbil{
	private int gerbilNumber;
	public Ex8Gerbil(int i){
		gerbilNumber=i;
	}
	public void hop(){
		System.out.println(this+" gerbilNumber = "+gerbilNumber);
	}
}
public class Ex8 {
	public static void main(String[] args){
		ArrayList<Ex8Gerbil> gerbils = new ArrayList<Ex8Gerbil>();
		for(int i=0;i<5;i++){
			gerbils.add(new Ex8Gerbil(i));
		}
//		for(int i=0;i<gerbils.size();i++){
//			gerbils.get(i).hop();
//		}
		Iterator<Ex8Gerbil> it = gerbils.iterator();
		while(it.hasNext()){
			Ex8Gerbil gerbil = it.next();
			gerbil.hop();
		}
	}
}