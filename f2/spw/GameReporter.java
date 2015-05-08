package f2.spw;

public interface GameReporter {

	long getScoreP1();
	long getScoreP2();
	String getNameP1();
	String getNameP2();
	long getLiveP1();
	long getLiveP2();
	long getHighScore();
	long getLevel();
	double getPercent();
}
