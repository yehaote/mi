package mi.practice.java.base.initialization;

public class MethodInit2 {
//	int j=g(i); //不能在i定义前, 进行调用
	int i=f();
	int j=g(i);
	
	int f(){
		return 11;
	}
	
	int g(int n){
		return n*10;
	}
}
