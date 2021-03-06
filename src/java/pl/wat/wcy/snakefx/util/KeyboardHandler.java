package pl.wat.wcy.snakefx.util;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import pl.wat.wcy.snakefx.core.Direction;
import pl.wat.wcy.snakefx.viewmodel.ViewModel;

public class KeyboardHandler implements EventHandler<KeyEvent> {

	private final ViewModel viewModel;

	public KeyboardHandler(final ViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void handle(final KeyEvent event) {
		final KeyCode code = event.getCode();

		switch (code) {
		case UP:
			viewModel.snakeDirection.set(Direction.UP);
			break;
		case DOWN:
			viewModel.snakeDirection.set(Direction.DOWN);
			break;
		case LEFT:
			viewModel.snakeDirection.set(Direction.LEFT);
			break;
		case RIGHT:
			viewModel.snakeDirection.set(Direction.RIGHT);
			break;
		}
	}
}