package pl.wat.wcy.snakefx.highscore;

import pl.wat.wcy.snakefx.config.Config;
import pl.wat.wcy.snakefx.highscore.HighScoreEntry;
import pl.wat.wcy.snakefx.highscore.HighscoreDao;
import pl.wat.wcy.snakefx.highscore.HighscoreManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HighScoreManager {
	private HighscoreManager scoreManager;

	private HighscoreDao daoMock;

	public void setup() {
		daoMock = mock(HighscoreDao.class);

		scoreManager = new HighscoreManager(daoMock);

	}

	public void Constructor() {

		final List<HighScoreEntry> existingEntries = new ArrayList<HighScoreEntry>();
		final HighScoreEntry highScoreEntry = new HighScoreEntry(1, "yoda,", 14);
		existingEntries.add(highScoreEntry);

		when(daoMock.load()).thenReturn(existingEntries);

		scoreManager = new HighscoreManager(daoMock);

		assertThat(scoreManager.highScoreEntries()).hasSize(1);
		assertThat(scoreManager.highScoreEntries()).contains(highScoreEntry);
	}

	public void Scores() {
		scoreManager.addScore("yoda", 100);
		scoreManager.addScore("yoda", 213);
		scoreManager.addScore("luke", 143);

		assertThat(scoreManager.highScoreEntries()).hasSize(3);

		assertThat(extractProperty("points", Integer.class).from(scoreManager.highScoreEntries()))
				.containsSequence(213, 143, 100);
		assertThat(extractProperty("playername", String.class).from(scoreManager.highScoreEntries()))
				.containsSequence("yoda", "luke", "yoda");

		scoreManager.addScore("jabba", 215);

		assertThat(scoreManager.highScoreEntries()).hasSize(3);
		assertThat(extractProperty("points", Integer.class).from(scoreManager.highScoreEntries()))
				.containsSequence(215, 213, 143).doesNotContain(100);
		assertThat(extractProperty("playername", String.class).from(scoreManager.highScoreEntries()))
				.containsSequence("jabba", "yoda", "luke");

		verify(daoMock, times(4)).persist(scoreManager.highScoreEntries());
	}
}
