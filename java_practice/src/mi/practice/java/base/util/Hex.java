package mi.practice.java.base.util;

import java.io.IOException;

public class Hex {
	public static String format(byte[] data){
		StringBuilder result = new StringBuilder();
		int n=0;
		for(byte b:data){
			if(n %16==0){
				//打印行好
				result.append(String.format("%05X: ", n));
			}
			//打印数据
			result.append(String.format("%02X", b));
			n++;
			if(n%16 == 0){
				//打印换行
				result.append("\n");
			}
		}
		result.append("\n");
		return result.toString();
	}
	
	public static void main(String[] args) throws IOException{
		if(args.length==0){
			System.out.println(format(BinaryFile.read("/home/waf/tmp/dangdangV3.tar.gz")));
		}else{
			System.out.println(format(BinaryFile.read(args[0])));
		}
	}
}
