package mi.practice.java.base.initialization;

class StringCall{
	public StringCall(String arg) {
		System.out.println("StringCall initialization");
		System.out.println(arg);
	}
}
public class Ex17 {
	public static void main(String[] args){
		StringCall[] calls = new StringCall[10];
		calls.toString();
	}
}
