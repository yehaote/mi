package mi.practice.java.base.enumerated;

import static mi.practice.java.base.enumerated.Outcome.*;

public enum RoShamBo2 implements Competitor<RoShamBo2> {
	PAPER(DRAW, LOSE, WIN), SCISSORS(WIN, DRAW, LOSE), ROCK(LOSE, WIN, DRAW), ;
	private Outcome vPAPER, vSCISSORS, vROCK;

	// 初始化, 每一种枚举类型, 定义三个不同的Outcome,
	// 分别对应Paper, scissors, rock三个类型
	private RoShamBo2(Outcome paper, Outcome scissors, Outcome rock) {
		this.vPAPER = paper;
		this.vSCISSORS = scissors;
		this.vROCK = rock;
	}

	@Override
	public Outcome compete(RoShamBo2 competitor) {
		switch (competitor) {
		default:
		case PAPER:
			return vPAPER;
		case SCISSORS:
			return vSCISSORS;
		case ROCK:
			return vROCK;
		}
	}

	public static void main(String[] args) {
		RoShamBo.play(RoShamBo2.class, 10);
	}
}