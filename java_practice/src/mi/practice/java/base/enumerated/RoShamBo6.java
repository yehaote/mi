package mi.practice.java.base.enumerated;

import static mi.practice.java.base.enumerated.Outcome.*;

/**
 * 也可以使用2维数组来实现
 */
public enum RoShamBo6 implements Competitor<RoShamBo6> {
	PAPER, SCISSORS, ROCK;
	private static Outcome[][] table = { { DRAW, LOSE, WIN },// Paper
			{ WIN, DRAW, LOSE },// SCISSORS
			{ LOSE, WIN, DRAW } // Rock
	};

	@Override
	public Outcome compete(RoShamBo6 competitor) {
		return table[this.ordinal()][competitor.ordinal()];
	}

	public static void main(String[] args) {
		RoShamBo.play(RoShamBo6.class, 20);
	}
}
