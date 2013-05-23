package mi.practice.java.base.interfaces;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Random;
import java.util.Scanner;
/**
 * 接口的主要用法是, 方法接收接口
 * 我们放入具体的实现类进行扩展
 * Java的Readable接口是为Scanner单独准备的,
 * 再扩展的时候我们想让一个对象可以对Scanner可读, 实现Readable接口就好
 */
public class RandomWords implements Readable{
	private static Random rand = new Random(47);
	private static final char[] capitals = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private static final char[] lowers =   "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private static final char[] vowels ="aeiou".toCharArray();
	private int count;
	public RandomWords(int count){
		this.count=count;
	}
	@Override
	public int read(CharBuffer cb) throws IOException {
		if(count-- == 0){ // equals count == 0 , and count--
			return -1;//指出是输入的尾端
		}
		cb.append(capitals[rand.nextInt(capitals.length)]);
		for(int i=0;i<4;i++){
			cb.append(vowels[rand.nextInt(vowels.length)]);
			cb.append(lowers[rand.nextInt(lowers.length)]);
		}
		cb.append(" ");
		return 10;//返回新增的大小
	}
	public static void main(String[] args){
		Scanner scanner = new Scanner(new RandomWords(10));
		while(scanner.hasNext()){
			System.out.println(scanner.next());
		}
	}
}