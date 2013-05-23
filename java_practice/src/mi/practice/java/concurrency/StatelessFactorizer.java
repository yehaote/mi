package mi.practice.java.concurrency;

/**
 * 无状态的对象永远是线程安全的.
/**
@ThreadSafe
public class StatelessFactorizer implements Servlet{
	@Override
	public void service(ServletRequest req, ServletResponse resp){
		BigInteger i = extractFormRequest(req);
		BigInteger[] factors = factor(i);
		ecodeIntoResponse(resp, factors);
	}
}
**/
