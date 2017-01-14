package pl.wat.wcy.snakefx.core;

import com.sun.javafx.collections.ObservableListWrapper;

import javafx.collections.ObservableList;
import pl.wat.wcy.snakefx.viewmodel.ViewModel;

import static pl.wat.wcy.snakefx.config.Config.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Grid {

	private final Integer gridSizeInPixel;

	private final ObservableList<Field> fields = new ObservableListWrapper<>(new ArrayList<>());

	private final ViewModel viewModel;

	public Grid(final ViewModel viewModel) {
		this.viewModel = viewModel;
		gridSizeInPixel = GRID_SIZE_IN_PIXEL.get();
	}

	public void init() {
		final int gridSize = viewModel.gridSize.get();

		for (int y = 0; y < gridSize; y++) {
			for (int x = 0; x < gridSize; x++) {
                fields.add(new Field(x, y, (gridSizeInPixel / gridSize)));
			}
		}
	}

	public List<Field> getFields() {
		return Collections.unmodifiableList(fields);
	}

	public Field getXY(final int x, final int y) {
        return fields.stream()
                .filter(field -> (field.getX() == x && field.getY() == y))
                .findFirst()
                .orElse(null);
	}

	public Field getFromDirection(final Field field, final Direction direction) {
		int x = field.getX();
		int y = field.getY();

		switch (direction) {
		case DOWN:
			y += 1;
			break;
		case LEFT:
			x -= 1;
			break;
		case RIGHT:
			x += 1;
			break;
		case UP:
			y -= 1;
			break;
		}

		final int gridSize = viewModel.gridSize.get();

		x += gridSize;
		y += gridSize;
		x = x % gridSize;
		y = y % gridSize;

		return getXY(x, y);
	}

	public Field getRandomEmptyField() {

        List<Field> emptyFields = fields.stream()
                .filter(field -> field.getState().equals(State.EMPTY))
                .collect(Collectors.<Field>toList());

		if (emptyFields.isEmpty()) {
			return null;
		} else {
			final int nextInt = new Random().nextInt(emptyFields.size());
			return emptyFields.get(nextInt);
		}
	}

	public void newGame() {
        fields.forEach(field -> {
            field.changeState(State.EMPTY);
        });
	}
}
