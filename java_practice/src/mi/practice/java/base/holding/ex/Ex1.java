package mi.practice.java.base.holding.ex;

import java.util.ArrayList;

class Gerbil{
	private int gerbilNumber;
	public Gerbil(int i){
		gerbilNumber=i;
	}
	public void hop(){
		System.out.println(this+" gerbilNumber = "+gerbilNumber);
	}
}
public class Ex1 {
	public static void main(String[] args){
		ArrayList<Gerbil> gerbils = new ArrayList<Gerbil>();
		for(int i=0;i<5;i++){
			gerbils.add(new Gerbil(i));
		}
		for(int i=0;i<gerbils.size();i++){
			gerbils.get(i).hop();
		}
	}
}
