package mi.practice.java.base.operators;

public class AllOps {
	// 测试返回值是否为boolean
	void f(boolean b) {
	};

	void boolTest(boolean x, boolean y) {
		// 算术操作
		// x=x*y;
		// x=x/y;
		// x=x%y;
		// x=x+y;
		// x=x-y;
		// x++;
		// x--;
		// x=+y;
		// x=-y;
		// 关系逻辑操作
		// f(x>y);
		// f(x>=y);
		// f(x<y);
		// f(x<=y);
		f(x == y);
		f(x != y);
		f(!y);
		x = x && y;
		x = x || y;
		// 位操作
		// x=~y;
		x = x & y;
		x = x | y;
		x = x ^ y;
		// x=x<<1;
		// x=x>>1;
		// x=x>>>1;
		// 符合赋值
		// x+=y;
		// x-=y;
		// x*=y;
		// x/=y;
		// x%=y;
		// x<<=1;
		// x>>=1;
		// x>>>=1;
		x &= y;
		x |= y;
		x ^= y;
		// 转化
		// char c=(char)x;
		// byte b=(byte)x;
		// short s=(short)x;
		// int i=(int)x;
		// long l=(long)x;
		// float f=(float)x;
		// double d=(double)x;
	}

	void charTest(char x, char y) {
		// 算术操作
		x = (char) (x * y);
		x = (char) (x / y);
		x = (char) (x % y);
		x = (char) (x + y);
		x = (char) (x - y);
		x++;
		x--;
		x = (char) +y;
		x = (char) -y;
		// 关系逻辑操作
		f(x > y);
		f(x >= y);
		f(x < y);
		f(x <= y);
		f(x == y);
		f(x != y);
		// f(!y);
		// x=x&&y;
		// x=x||y;
		// 位操作
		x = (char) ~y;
		x = (char) (x & y);
		x = (char) (x | y);
		x = (char) (x ^ y);
		x = (char) (x << 1);
		x = (char) (x >> 1);
		x = (char) (x >>> 1);
		// 符合赋值
		x += y;
		x -= y;
		x *= y;
		x /= y;
		x %= y;
		x <<= 1;
		x >>= 1;
		x >>>= 1;
		x &= y;
		x |= y;
		x ^= y;
		// 转化
		// boolean bl=(boolean)x;
		byte b = (byte) x;
		short s = (short) x;
		int i = (int) x;
		long l = (long) x;
		float f = (float) x;
		double d = (double) x;
		System.out.println(b + s + i + l + f + d);
	}

	void byteTest(byte x, byte y) {
		// 算术操作
		x = (byte) (x * y);
		x = (byte) (x / y);
		x = (byte) (x % y);
		x = (byte) (x + y);
		x = (byte) (x - y);
		x++;
		x--;
		x = (byte) +y;
		x = (byte) -y;
		// 关系逻辑操作
		f(x > y);
		f(x >= y);
		f(x < y);
		f(x <= y);
		f(x == y);
		f(x != y);
		// f(!y);
		// x=x&&y;
		// x=x||y;
		// 位操作
		x = (byte) ~y;
		x = (byte) (x & y);
		x = (byte) (x | y);
		x = (byte) (x ^ y);
		x = (byte) (x << 1);
		x = (byte) (x >> 1);
		x = (byte) (x >>> 1);
		// 符合赋值
		x += y;
		x -= y;
		x *= y;
		x /= y;
		x %= y;
		x <<= 1;
		x >>= 1;
		x >>>= 1;
		x &= y;
		x |= y;
		x ^= y;
		// 转化
		// boolean bl=(boolean)x;
		char c = (char) x;
		short s = (short) x;
		int i = (int) x;
		long l = (long) x;
		float f = (float) x;
		double d = (double) x;
		System.out.println(c + s + i + l + f + d);
	}

	void shortTest(short x, short y) {
		// 算术操作
		x = (short) (x * y);
		x = (short) (x / y);
		x = (short) (x % y);
		x = (short) (x + y);
		x = (short) (x - y);
		x++;
		x--;
		x = (short) +y;
		x = (short) -y;
		// 关系逻辑操作
		f(x > y);
		f(x >= y);
		f(x < y);
		f(x <= y);
		f(x == y);
		f(x != y);
		// f(!y);
		// x=x&&y;
		// x=x||y;
		// 位操作
		x = (short) ~y;
		x = (short) (x & y);
		x = (short) (x | y);
		x = (short) (x ^ y);
		x = (short) (x << 1);
		x = (short) (x >> 1);
		x = (short) (x >>> 1);
		// 符合赋值
		x += y;
		x -= y;
		x *= y;
		x /= y;
		x %= y;
		x <<= 1;
		x >>= 1;
		x >>>= 1;
		x &= y;
		x |= y;
		x ^= y;
		// 转化
		// boolean bl=(boolean)x;
		char c = (char) x;
		byte b = (byte) x;
		int i = (int) x;
		long l = (long) x;
		float f = (float) x;
		double d = (double) x;
		System.out.println(c + b + i + l + f + d);
	}

	void intTest(int x, int y) {
		// 算术操作
		x = x * y;
		x = x / y;
		x = x % y;
		x = x + y;
		x = x - y;
		x++;
		x--;
		x = +y;
		x = -y;
		// 关系逻辑操作
		f(x > y);
		f(x >= y);
		f(x < y);
		f(x <= y);
		f(x == y);
		f(x != y);
		// f(!y);
		// x=x&&y;
		// x=x||y;
		// 位操作
		x = ~y;
		x = x & y;
		x = x | y;
		x = x ^ y;
		x = x << 1;
		x = x >> 1;
		x = x >>> 1;
		// 符合赋值
		x += y;
		x -= y;
		x *= y;
		x /= y;
		x %= y;
		x <<= 1;
		x >>= 1;
		x >>>= 1;
		x &= y;
		x |= y;
		x ^= y;
		// 转化
		// boolean bl=(boolean)x;
		char c = (char) x;
		byte b = (byte) x;
		short s = (short) x;
		long l = (long) x;
		float f = (float) x;
		double d = (double) x;
		System.out.println(c + b + s + l + f + d);
	}

	void longTest(long x, long y) {
		// 算术操作
		x = x * y;
		x = x / y;
		x = x % y;
		x = x + y;
		x = x - y;
		x++;
		x--;
		x = +y;
		x = -y;
		// 关系逻辑操作
		f(x > y);
		f(x >= y);
		f(x < y);
		f(x <= y);
		f(x == y);
		f(x != y);
		// f(!y);
		// x=x&&y;
		// x=x||y;
		// 位操作
		x = ~y;
		x = x & y;
		x = x | y;
		x = x ^ y;
		x = x << 1;
		x = x >> 1;
		x = x >>> 1;
		// 符合赋值
		x += y;
		x -= y;
		x *= y;
		x /= y;
		x %= y;
		x <<= 1;
		x >>= 1;
		x >>>= 1;
		x &= y;
		x |= y;
		x ^= y;
		// 转化
		// boolean bl=(boolean)x;
		char c = (char) x;
		byte b = (byte) x;
		short s = (short) x;
		int i = (int) x;
		float f = (float) x;
		double d = (double) x;
		System.out.println(c + b + s + i + f + d);
	}

	void floatTest(float x, float y) {
		// 算术操作
		x = x * y;
		x = x / y;
		x = x % y;
		x = x + y;
		x = x - y;
		x++;
		x--;
		x = +y;
		x = -y;
		// 关系逻辑操作
		f(x > y);
		f(x >= y);
		f(x < y);
		f(x <= y);
		f(x == y);
		f(x != y);
		// f(!y);
		// x=x&&y;
		// x=x||y;
		// 位操作
		// x=~y;
		// x=x&y;
		// x=x|y;
		// x=x^y;
		// x=x<<1;
		// x=x>>1;
		// x=x>>>1;
		// 符合赋值
		x += y;
		x -= y;
		x *= y;
		x /= y;
		x %= y;
		// x<<=1;
		// x>>=1;
		// x>>>=1;
		// x&=y;
		// x|=y;
		// x^=y;
		// 转化
		// boolean bl=(boolean)x;
		char c = (char) x;
		byte b = (byte) x;
		short s = (short) x;
		int i = (int) x;
		long l = (long) x;
		double d = (double) x;
		System.out.println(c + b + s + i + l + d);
	}

	void doubleTest(double x, double y) {
		// 算术操作
		x = x * y;
		x = x / y;
		x = x % y;
		x = x + y;
		x = x - y;
		x++;
		x--;
		x = +y;
		x = -y;
		// 关系逻辑操作
		f(x > y);
		f(x >= y);
		f(x < y);
		f(x <= y);
		f(x == y);
		f(x != y);
		// f(!y);
		// x=x&&y;
		// x=x||y;
		// 位操作
		// x=~y;
		// x=x&y;
		// x=x|y;
		// x=x^y;
		// x=x<<1;
		// x=x>>1;
		// x=x>>>1;
		// 符合赋值
		x += y;
		x -= y;
		x *= y;
		x /= y;
		x %= y;
		// x<<=1;
		// x>>=1;
		// x>>>=1;
		// x&=y;
		// x|=y;
		// x^=y;
		// 转化
		// boolean bl=(boolean)x;
		char c = (char) x;
		byte b = (byte) x;
		short s = (short) x;
		int i = (int) x;
		long l = (long) x;
		float f = (float) x;
		System.out.println(c + b + s + i + l + f);
	}

}
