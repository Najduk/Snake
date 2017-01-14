package pl.wat.wcy.snakefx.view.presenter;

import static pl.wat.wcy.snakefx.config.Config.*;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import pl.wat.wcy.snakefx.highscore.HighScoreEntry;
import pl.wat.wcy.snakefx.highscore.HighscoreManager;
import pl.wat.wcy.snakefx.viewmodel.ViewModel;

public class HighscorePresenter {

    @FXML
    private TableView<HighScoreEntry> tableView;

    private final ListProperty<HighScoreEntry> highScoreEntries = new SimpleListProperty<>();

    private ViewModel viewModel;

    public HighscorePresenter(ViewModel viewModel, HighscoreManager highscoreManager) {
        this.viewModel = viewModel;
        viewModel.collision.addListener((observable, oldValue, collisionHappend) -> {
            if (collisionHappend) {
                gameFinished();
            }
        });

        this.highScoreEntries.bind(highscoreManager.highScoreEntries());
    }

    public ListProperty<HighScoreEntry> highScoreEntries() {
        return highScoreEntries;
    }

    public void gameFinished() {
        final int points = viewModel.points.get();

        final int size = highScoreEntries.size();

        if (size < MAX_SCORE_COUNT.get()) {
            viewModel.newHighscoreWindowOpen.set(true);
        } else {

            if (highScoreEntries.get(size - 1).getPoints() < points) {
                viewModel.newHighscoreWindowOpen.set(true);
            }
        }
    }

    @FXML
    public void initialize() {
        tableView.setItems(highScoreEntries);
    }


}
