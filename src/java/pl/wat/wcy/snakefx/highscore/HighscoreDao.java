package pl.wat.wcy.snakefx.highscore;

import java.util.List;

public interface HighscoreDao {

	void persist(List<HighScoreEntry> highscores);

	List<HighScoreEntry> load();
}
