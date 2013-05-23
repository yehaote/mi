package mi.practice.java.base.strings;

import java.util.Formatter;
/**
 * Formatter
 * 宽度控制 如%10前面补到10位, %-10后面补到10位
 * 精度控制 %.2 对字符串来说是最大长度限制, 对浮点数来说是控制小数点后面位数
 * @author waf
 *
 */
public class Receipt {
	private double total=0;
	private Formatter f = new Formatter(System.out);
	
	public void printTitle(){
		f.format("%-15s %5s %10s\n","Item", "Qty", "Price");
		f.format("%-15s %5s %10s\n","----", "---", "-----");
	}
	
	public void print(String name, int qty, double price){
		f.format("%-15.15s %5d %10.2f\n",name, qty, price);
		total+=price;
	}
	
	public void printTotal(){
		f.format("%-15s %5s %10.2f\n", "Tax", "", total*0.06);
		f.format("%-15s %5s %10s\n", "","","-----");
		f.format("%-15s %5s %10.2f\n", "Total", "", total*1.06);
	}
	
	public static void main(String[] args){
		Receipt receipt =new Receipt();
		receipt.printTitle();
		receipt.print("Jack's Magic Beans", 4, 4.25);
		receipt.print("Princess Peas", 3, 5.1);
		receipt.print("Three Bears Porridge", 1, 14.29);
//		receipt.print("12345678901234567890", 1234567, 1234567890.12);
		receipt.printTotal();
	}
	
	
}
