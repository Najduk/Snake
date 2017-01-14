package pl.wat.wcy.snakefx.highscore;


import org.mockito.internal.util.reflection.Whitebox;

import pl.wat.wcy.snakefx.highscore.HighScoreEntry;
import pl.wat.wcy.snakefx.highscore.HighscoreJsonDao;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class HighscoreJsonDao {

	private final static String filename = "highscore.json";

	private Path filepath;

	private HighscoreJsonDao dao;

	public void setup() {
		filepath = Paths.get("build/tmp", filename);

		dao = new HighscoreJsonDao();

		Whitebox.setInternalState(dao, "filepath", filepath);

	}

	public void after() {
		final File file = filepath.toFile();

		if (file.exists()) {
			file.delete();
		}
	}

	public void PersistAndLoad() {
		final List<HighScoreEntry> entries = new ArrayList<>();

		entries.add(new HighScoreEntry(1, "yoda", 402));
		entries.add(new HighScoreEntry(2, "luke", 212));
		entries.add(new HighScoreEntry(3, "jabba", 123));

		final File file = filepath.toFile();

		assertThat(file).doesNotExist();

		dao.persist(entries);

		assertThat(file).exists().isFile();


		final List<HighScoreEntry> loadedEntries = dao.load();

		assertThat(loadedEntries).hasSize(3);
		assertThat(loadedEntries.get(0)).isEqualsToByComparingFields(new HighScoreEntry(1, "yoda", 402));
		assertThat(loadedEntries.get(1)).isEqualsToByComparingFields(new HighScoreEntry(2, "luke", 212));
		assertThat(loadedEntries.get(2)).isEqualsToByComparingFields(new HighScoreEntry(3, "jabba", 123));
	}
}
