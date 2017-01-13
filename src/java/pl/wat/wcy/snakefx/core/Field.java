package pl.wat.wcy.snakefx.core;

import javafx.scene.shape.Rectangle;
import pl.wat.wcy.snakefx.core.Field;
import pl.wat.wcy.snakefx.core.State;

import static org.assertj.core.api.Assertions.*;

public class Test {

	public void Initialization() {

		int x = 3;
		int y = 5;

		int sizeInPixel = 100;

		Field field = new Field(x, y, sizeInPixel);

		Rectangle rectangle = field.getRectangle();

		assertThat(rectangle.getWidth()).isEqualTo(sizeInPixel);
		assertThat(rectangle.getHeight()).isEqualTo(sizeInPixel);

		assertThat(rectangle.getX()).isEqualTo(300);
		assertThat(rectangle.getY()).isEqualTo(500);

		assertThat(field.getX()).isEqualTo(x);
		assertThat(field.getY()).isEqualTo(y);

		assertThat(field.getState()).isEqualTo(State.EMPTY);

		assertThat(rectangle.getFill()).isEqualTo(State.EMPTY.getColor());

	}


	public void ChangeState() {

		Field field = new Field(1, 1, 10);

		field.changeState(State.HEAD);

		assertThat(field.getState()).isEqualTo(State.HEAD);
		assertThat(field.getRectangle().getFill()).isEqualTo(
				State.HEAD.getColor());
	}

}
