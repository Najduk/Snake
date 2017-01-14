package pl.wat.wcy.snakefx.core;

import javafx.scene.paint.Color;

public enum State {

	EMPTY(Color.WHITE),

	HEAD(Color.DARKGREEN),

	TAIL(Color.FORESTGREEN),

	FOOD(Color.BLACK);

	private Color color;

	private State(final Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

}