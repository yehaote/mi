package mi.practice.java.base.reusing;

class Amphibian{
	public void sayHello(){
		System.out.println("Amphibian sayHello()");
	}
}

class Forg extends Amphibian{
	
}

public class Ex16 {
	public static void main(String[] args){
		Forg forg = new Forg();
		Amphibian amphibian = forg;
		amphibian.sayHello();
	}
}
