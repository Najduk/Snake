package pl.wat.wcy.snakefx.core;


import pl.wat.wcy.snakefx.core.Direction;
import pl.wat.wcy.snakefx.core.Field;
import pl.wat.wcy.snakefx.core.Grid;
import pl.wat.wcy.snakefx.core.State;
import pl.wat.wcy.snakefx.viewmodel.ViewModel;

import org.mockito.internal.util.reflection.Whitebox;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class Grid {

	private static final int ROW_AND_COLUMN_COUNT = 4;
	private static final int GRID_SIZE_IN_PIXEL = 300;

	private Grid grid;
	private ViewModel viewModel;

	public void setUp() {
		viewModel = new ViewModel();
		viewModel.gridSize.set(ROW_AND_COLUMN_COUNT);
		grid = new Grid(viewModel);
		Whitebox.setInternalState(grid, "gridSizeInPixel", GRID_SIZE_IN_PIXEL);

		grid.init();
	}

	public void Initialization() {

		final List<Field> fields = grid.getFields();

		final int fieldCount = ROW_AND_COLUMN_COUNT * ROW_AND_COLUMN_COUNT;
		assertThat(fields).hasSize(fieldCount);

        fields.forEach(field->{
			assertThat(field.getRectangle().getWidth()).isEqualTo(GRID_SIZE_IN_PIXEL / ROW_AND_COLUMN_COUNT);
			assertThat(field.getRectangle().getHeight()).isEqualTo(GRID_SIZE_IN_PIXEL / ROW_AND_COLUMN_COUNT);
        });

		final Field x0y0 = fields.get(0);
		assertThat(x0y0.getX()).isEqualTo(0);
		assertThat(x0y0.getY()).isEqualTo(0);

		final Field x1y2 = fields.get(9);
		assertThat(x1y2.getX()).isEqualTo(1);
		assertThat(x1y2.getY()).isEqualTo(2);

		final Field x3y3 = fields.get(fieldCount - 1);
		assertThat(x3y3.getX()).isEqualTo(3);
		assertThat(x3y3.getY()).isEqualTo(3);
	}

	public void GetXY() {
		final Field x2y1 = grid.getXY(2, 1);

		assertThat(x2y1.getX()).isEqualTo(2);
		assertThat(x2y1.getY()).isEqualTo(1);
	}

	public void GetXYFail() {
		final Field x2y5 = grid.getXY(2, 5);

		assertThat(x2y5).isNull();
	}


	public void GetFromDirection() {
		final Field x2y2 = grid.getXY(2, 2);

		final Field x2y3 = grid.getFromDirection(x2y2, Direction.DOWN);

		assertThat(x2y3.getX()).isEqualTo(2);
		assertThat(x2y3.getY()).isEqualTo(3);

		final Field x3y3 = grid.getFromDirection(x2y3, Direction.RIGHT);

		assertThat(x3y3.getX()).isEqualTo(3);
		assertThat(x3y3.getY()).isEqualTo(3);
	}


	public void GetFromDirectionOtherSideOfTheGrid() {

		Field x0y3 = grid.getXY(0, 3);
		final Field x3y3 = grid.getFromDirection(x0y3, Direction.LEFT);

		assertThat(x3y3.getX()).isEqualTo(3);
		assertThat(x3y3.getY()).isEqualTo(3);

		x0y3 = grid.getFromDirection(x3y3, Direction.RIGHT);
		assertThat(x0y3.getX()).isEqualTo(0);
		assertThat(x0y3.getY()).isEqualTo(3);

		Field x2y0 = grid.getXY(2, 0);
		final Field x2y3 = grid.getFromDirection(x2y0, Direction.UP);

		assertThat(x2y3.getX()).isEqualTo(2);
		assertThat(x2y3.getY()).isEqualTo(3);

		x2y0 = grid.getFromDirection(x2y3, Direction.DOWN);
		assertThat(x2y0.getX()).isEqualTo(2);
		assertThat(x2y0.getY()).isEqualTo(0);
	}

	public void NewGame() {

		final Field x2y1 = grid.getXY(2, 1);
		x2y1.changeState(State.FOOD);

		final Field x3y3 = grid.getXY(3, 3);

		x3y3.changeState(State.HEAD);

		final Field x0y2 = grid.getXY(0, 2);
		x0y2.changeState(State.TAIL);

		grid.newGame();

		assertThat(x2y1.getState()).isEqualTo(State.EMPTY);
		assertThat(x3y3.getState()).isEqualTo(State.EMPTY);
		assertThat(x0y2.getState()).isEqualTo(State.EMPTY);

		final List<Field> fields = grid.getFields();

        fields.forEach(field -> {
            assertThat(field.getState()).isEqualTo(State.EMPTY);
        });
	}
}
