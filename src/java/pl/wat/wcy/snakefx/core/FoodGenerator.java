package pl.wat.wcy.snakefx.core;


import pl.wat.wcy.snakefx.core.Field;
import pl.wat.wcy.snakefx.core.FoodGenerator;
import pl.wat.wcy.snakefx.core.Grid;
import pl.wat.wcy.snakefx.core.State;
import pl.wat.wcy.snakefx.viewmodel.ViewModel;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FoodGenerator {

	private FoodGenerator foodGenerator;

	private Grid gridMock;

	private ViewModel viewModel;

	public void setup() {
		gridMock = mock(Grid.class);
		viewModel = new ViewModel();
		foodGenerator = new FoodGenerator(viewModel, gridMock);
	}

	public void GenerateFood() {
		final Field field = new Field(0, 0, 10);

		when(gridMock.getRandomEmptyField()).thenReturn(field);

		foodGenerator.generateFood();

		assertThat(field.getState()).isEqualTo(State.FOOD);
	}


	public void GenerationWhenPointsAreAddedToProperty() {
		final Field field = new Field(0, 0, 10);
		field.changeState(State.EMPTY);
		when(gridMock.getRandomEmptyField()).thenReturn(field);

		viewModel.points.set(1);

		assertThat(field.getState()).isEqualTo(State.FOOD);
	}

	public void NoFoodIsGeneratedWhenPointsPropertyIsResetToZero() {
		final Field field = new Field(0, 0, 10);
		field.changeState(State.EMPTY);

		final Field secondField = new Field(0, 1, 10);
		secondField.changeState(State.EMPTY);
		when(gridMock.getRandomEmptyField()).thenReturn(field).thenReturn(secondField);

		viewModel.points.set(10);

		viewModel.points.set(0);

		assertThat(secondField.getState()).isEqualTo(State.EMPTY);
	}
}
