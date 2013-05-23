package mi.practice.java.base.exceptions;

public class Cleanup {
	public static void main(String[] args) {
		try {
			InputFile in = new InputFile("/home/waf/tmp/test");
			try {
				String s;
				int i = 1;
				while ((s = in.getLine()) != null) {
					// 执行每行的操作
					System.out.println(i+": "+s);
					i++;
				}
			} catch (Exception e) {
				System.out.println("Caught Exception in main");
				e.printStackTrace();
			} finally {
				in.dispose();
			}
		} catch (Exception e) {
			System.out.println("InputFile construction failed");
		}
	}
}
