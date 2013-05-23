package mi.practice.java.concurrency;
/**
@NotThreadSafe
public class UnsafeCountingFactorizer  implements Servlet{
	private long count = 0;
	public long getCount(){
		return count;
	}
	@Override
	public void service(ServletRequest req, ServletResponse resp){
		BigInteger i = extractFormRequest(req);
		BigInteger[] factors = factor(i);
		++count;
		ecodeIntoResponse(resp, factors);
	}
}
**/