package mi.practice.java.base.generics;

import java.util.ArrayList;

import mi.practice.java.base.util.FourTuple;

@SuppressWarnings({ "serial", "hiding" })
public class TupleList <A, B, C, D> extends ArrayList<FourTuple<A, B, C, D>>{
	public static void main(String[] args){
		TupleList<Vehicle, Amphibian, String, Integer> tl = new TupleList<Vehicle, Amphibian, String, Integer>();
		tl.add(TupleTest.h());
		tl.add(TupleTest.h());
		for(FourTuple<Vehicle, Amphibian, String, Integer> i:tl){
			System.out.println(i);
		}
	}
}
