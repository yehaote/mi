package mi.practice.java.base.generics;
/**
 * 不能同时多次实现同个泛型接口的不同类型,
 * 因为擦写的缘故Playable<Player>和Playable<Hourly>
 * 是相同的接口.
 * 好玩的是如果这个接口不是泛型化的, 就没有问题
 * @author waf
 * @param <T>
 */
interface Playable<T>{}

class Player implements Playable<Player>{}

//class Hourly extends Player implements Playable<Hourly>{}