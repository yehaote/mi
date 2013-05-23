package mi.practice.java.base.typeinfo.ex;

import static mi.practice.java.base.util.Print.print;

class Candy{
	static {
		print("Loading Candy");
	}
}
class Gum{
	static{
		print("Loading Gum");
	}
}
class Cookie{
	static{
		print("Loading Cookie");
	}
}

class SweetShop {
	public static void main(String[] args) throws ClassNotFoundException{
		if(args !=null && args.length>0){
			Class.forName(args[0]);
		}else{
			System.out.println("Arguments is not  valuable!");
			return;
		}
	}
}
public class Ex7 {
	public static void main(String[] args) throws ClassNotFoundException{
		SweetShop.main(args);
	}
}
