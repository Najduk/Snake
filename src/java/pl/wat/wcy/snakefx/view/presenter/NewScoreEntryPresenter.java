package pl.wat.wcy.snakefx.view.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.wat.wcy.snakefx.highscore.HighscoreManager;
import pl.wat.wcy.snakefx.viewmodel.ViewModel;

public class NewScoreEntryPresenter {

	@FXML
	private TextField playername;

	@FXML
	private Label errorMessage;

	@FXML
	private Label points;

	private final HighscoreManager highScoreManager;
    private ViewModel viewModel;

	public NewScoreEntryPresenter(final HighscoreManager highScoreManager, ViewModel viewModel) {
		this.highScoreManager = highScoreManager;
        this.viewModel = viewModel;
	}

	@FXML
	public void initialize() {
		points.textProperty().bind(viewModel.points.asString());
	}

	@FXML
	public void addEntry() {
        String name = playername.getText();
		if (!isNameValid(name)) {
			errorMessage.setVisible(true);
			return;
		} else {
			errorMessage.setVisible(false);
		}

		highScoreManager.addScore(name, viewModel.points.get());
        viewModel.newHighscoreWindowOpen.set(false);
        viewModel.highscoreWindowOpen.set(true);
	}

	private boolean isNameValid(final String name) {
		if (name == null) {
			return false;
		}
		if (name.isEmpty()) {
			return false;
		}
		if (name.contains(",")) {
			return false;
		}

		if (name.contains(";")) {
			return false;
		}

		return true;
	}
}
