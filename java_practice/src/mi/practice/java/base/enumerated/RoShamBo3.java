package mi.practice.java.base.enumerated;
import static mi.practice.java.base.enumerated.Outcome.*;
public enum RoShamBo3 implements Competitor<RoShamBo3>{
	PAPER{
		@Override
		public Outcome compete(RoShamBo3 it){
			switch(it){
				default:
				case PAPER: return DRAW;
				case SCISSORS: return LOSE;
				case ROCK : return WIN;
			}
		}
	},
	SCISSORS{
		@Override
		public Outcome compete(RoShamBo3 it){
			switch(it){
			default:
			case PAPER: return WIN;
			case SCISSORS : return DRAW;
			case ROCK : return LOSE;
			}
		}
	},
	ROCK{
		@Override
		public Outcome compete(RoShamBo3 it){
			switch(it){
			default:
			case PAPER : return LOSE;
			case SCISSORS : return WIN;
			case ROCK: return DRAW;
			}
		}
	}
	;

	@Override
	public abstract Outcome compete(RoShamBo3 competitor);
	public static void main(String[] args){
		RoShamBo.play(RoShamBo3.class, 20);
	}
	
}
