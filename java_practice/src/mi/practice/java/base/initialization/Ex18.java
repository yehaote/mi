package mi.practice.java.base.initialization;

class StringOut {
	public StringOut(String arg) {
		System.out.println("StringOut initialization");
		System.out.println(arg);
	}
}

public class Ex18 {
	public static void main(String[] args){
		StringOut[] outs = new StringOut[10];
		for(int i=0;i<outs.length;i++){
			outs[i]=new StringOut(Integer.toString(i));
		}
	}

}
