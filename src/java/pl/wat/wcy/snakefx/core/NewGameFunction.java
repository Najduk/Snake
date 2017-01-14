package pl.wat.wcy.snakefx.core;

import javafx.animation.Animation.Status;
import pl.wat.wcy.snakefx.viewmodel.ViewModel;

import java.util.function.Consumer;

public class NewGameFunction implements Consumer<Void> {
	private final ViewModel viewModel;
	private final Grid grid;
	private final Snake snake;
	private final FoodGenerator foodGenerator;

	public NewGameFunction(final ViewModel viewModel, final Grid grid, final Snake snake,
			final FoodGenerator foodGenerator) {
		this.viewModel = viewModel;
		this.grid = grid;
		this.snake = snake;
		this.foodGenerator = foodGenerator;
	}

    @Override
    public void accept(Void aVoid) {
        viewModel.gameloopStatus.set(Status.STOPPED);

        grid.newGame();

        snake.newGame();

        foodGenerator.generateFood();

        viewModel.gameloopStatus.set(Status.RUNNING);
        viewModel.gameloopStatus.set(Status.PAUSED);
    }
}
