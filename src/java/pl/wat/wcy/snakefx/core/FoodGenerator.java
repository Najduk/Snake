package pl.wat.wcy.snakefx.core;

import pl.wat.wcy.snakefx.viewmodel.ViewModel;

	public void generateFood() {
		final Field field = grid.getRandomEmptyField();

		field.changeState(State.FOOD);
	}
}