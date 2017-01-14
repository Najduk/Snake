package pl.wat.wcy.snakefx.view.presenter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import pl.wat.wcy.snakefx.view.FXMLFile;
import pl.wat.wcy.snakefx.viewmodel.ViewModel;

import java.util.function.Consumer;

public class MenuPresenter {

	private final ViewModel viewModel;
	private final Consumer<?> newGameFunction;

	public MenuPresenter(final ViewModel viewModel, final Consumer<?> newGameFunction) {
		this.viewModel = viewModel;
		this.newGameFunction = newGameFunction;
	}

	@FXML
	public void newGame() {
		newGameFunction.accept(null);
	}

	@FXML
	public void showHighscores() {
		viewModel.highscoreWindowOpen.set(true);
	}

	@FXML
	public void about() {
		viewModel.aboutWindowOpen.set(true);
	}

	@FXML
	public void exit() {
		Platform.exit();
	}

}
