package pl.wat.wcy.snakefx.core;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Field {

	private final Rectangle rectangle;

	private final int x;
	private final int y;

	private State state;

	public Field(final int x, final int y, final int sizeInPixel) {
		this.x = x;
		this.y = y;

		state = State.EMPTY;

		rectangle = new Rectangle(x * sizeInPixel, y * sizeInPixel,
				sizeInPixel, sizeInPixel);

		rectangle.setStroke(Color.LIGHTGRAY);
		rectangle.setFill(Color.WHITE);

	}

	public State getState() {
		return state;
	}

	public void changeState(final State newState) {
		state = newState;

		rectangle.setFill(newState.getColor());
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
