package pl.wat.wcy.snakefx.view.presenter;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import pl.wat.wcy.snakefx.core.Grid;
import pl.wat.wcy.snakefx.viewmodel.ViewModel;

import java.util.function.Consumer;

public class MainPresenter {

	@FXML
	private Pane gridContainer;

	private final Grid grid;

	private final ViewModel viewModel;

	private final Consumer<?> newGameFunction;


	public MainPresenter(final ViewModel viewModel, final Grid grid, final Consumer<?> newGameFunction) {
		this.viewModel = viewModel;
		this.grid = grid;
		this.newGameFunction = newGameFunction;
	}


	@FXML
	public void initialize() {
		grid.init();

        grid.getFields().forEach(field -> {
            gridContainer.getChildren().add(field.getRectangle());
        });

		newGameFunction.accept(null);
	}

}
