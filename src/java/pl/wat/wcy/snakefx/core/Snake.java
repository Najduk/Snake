package pl.wat.wcy.snakefx.core;

import pl.wat.wcy.snakefx.core.Direction;
import pl.wat.wcy.snakefx.core.Field;
import pl.wat.wcy.snakefx.core.GameLoop;
import pl.wat.wcy.snakefx.core.Grid;
import pl.wat.wcy.snakefx.core.Snake;
import pl.wat.wcy.snakefx.core.State;
import pl.wat.wcy.snakefx.viewmodel.ViewModel;


import org.mockito.internal.util.reflection.Whitebox;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class Snake {
	private Snake snake;

	private Grid gridMock;

	private GameLoop gameLoopMock;

	private static final int X = 4;
	private static final int Y = 2;

	private ViewModel viewModel;

	public void setUp() {
		gridMock = mock(Grid.class);
		gameLoopMock = mock(GameLoop.class);

		viewModel = new ViewModel();

		snake = new Snake(viewModel, gridMock, gameLoopMock);
		Whitebox.setInternalState(snake, "x", X);
		Whitebox.setInternalState(snake, "y", Y);

	}

	public void Initialization() {
		final Field field = mock(Field.class);

		when(gridMock.getXY(X, Y)).thenReturn(field);

		snake.init();

		verify(gridMock, times(1)).getXY(X, Y);
		verify(field, times(1)).changeState(State.HEAD);

		assertThat(getHead()).isEqualTo(field);

		final Direction direction = currentDirectionFromSnake();
		assertThat(direction).isEqualTo(Direction.UP);
	}

	public void ChangeDirection() {
		viewModel.snakeDirection.set(Direction.LEFT);
		final Direction direction = nextDirectionFromSnake();

		assertThat(direction).isEqualTo(Direction.LEFT);
	}

	public void ChangeDirectionNewHasSameOrientationAsOld() {
		final Field head = mock(Field.class);
		when(gridMock.getXY(X, Y)).thenReturn(head);

		final Field newHead = mock(Field.class);
		when(newHead.getState()).thenReturn(State.EMPTY);
		when(gridMock.getFromDirection(head, Direction.LEFT)).thenReturn(newHead);

		snake.init();

		viewModel.snakeDirection.set(Direction.DOWN);

		assertThat(nextDirectionFromSnake()).isEqualTo(Direction.UP);
		assertThat(currentDirectionFromSnake()).isEqualTo(Direction.UP);

		viewModel.snakeDirection.set(Direction.LEFT);

		assertThat(nextDirectionFromSnake()).isEqualsToByComparingFields(Direction.LEFT);
		
		assertThat(currentDirectionFromSnake()).isEqualTo(Direction.UP);

		viewModel.snakeDirection.set(Direction.DOWN);

		assertThat(nextDirectionFromSnake()).isEqualTo(Direction.LEFT);
		assertThat(currentDirectionFromSnake()).isEqualTo(Direction.UP);

		snake.move();

		assertThat(nextDirectionFromSnake()).isEqualTo(Direction.LEFT);

		assertThat(currentDirectionFromSnake()).isEqualTo(Direction.LEFT);
	}

	public void Move() {
		final Field oldHead = mock(Field.class);
		when(oldHead.getState()).thenReturn(State.EMPTY);
		when(gridMock.getXY(X, Y)).thenReturn(oldHead);

		snake.init();

		final Field newHead = mock(Field.class);
		when(newHead.getState()).thenReturn(State.EMPTY);
		when(gridMock.getFromDirection(oldHead, Direction.UP)).thenReturn(newHead);

		snake.move();

		assertThat(getHead()).isEqualTo(newHead);

		verify(oldHead).changeState(State.EMPTY);
	}


	public void Grow() {
		final Field field1 = new Field(0, 3, 10);

		when(gridMock.getXY(X, Y)).thenReturn(field1);

		final Field field2 = new Field(0, 2, 10);
		field2.changeState(State.FOOD);
		when(gridMock.getFromDirection(field1, Direction.UP)).thenReturn(field2);

		final Field field3 = new Field(0, 1, 10);
		when(gridMock.getFromDirection(field2, Direction.UP)).thenReturn(field3);

		snake.init();

		snake.move();

		assertThat(getHead()).isEqualTo(field2);

		assertThat(field1.getState()).isEqualTo(State.TAIL);

		assertThat(viewModel.points.get()).isEqualTo(1);

		snake.move();

		assertThat(getHead()).isEqualTo(field3);

		assertThat(field2.getState()).isEqualTo(State.TAIL);

		assertThat(field1.getState()).isEqualTo(State.EMPTY);
	}

	public void Collision() {

		final Field oldHead = mock(Field.class);
		when(oldHead.getState()).thenReturn(State.EMPTY);
		when(gridMock.getXY(X, Y)).thenReturn(oldHead);

		snake.init();

		final Field tail = mock(Field.class);
		when(tail.getState()).thenReturn(State.TAIL);
		when(gridMock.getFromDirection(oldHead, Direction.UP)).thenReturn(tail);

		snake.move();

		assertThat(viewModel.collision.get()).isTrue();
	}

	public void NewGame() {
		final Field head = mock(Field.class);
		when(head.getState()).thenReturn(State.EMPTY);
		when(gridMock.getXY(X, Y)).thenReturn(head);

		final Field food = mock(Field.class);
		when(food.getState()).thenReturn(State.FOOD);
		when(gridMock.getFromDirection(head, Direction.UP)).thenReturn(food);

		snake.init();
		snake.move();

		assertThat(getHead()).isEqualTo(food);
		assertThat(getTail()).hasSize(1);
		assertThat(getTail().contains(head));

		snake.newGame();

		assertThat(getHead()).isEqualTo(head);
		assertThat(getTail()).isEmpty();

	}

	@SuppressWarnings("unchecked")
	private List<Field> getTail() {
		return (List<Field>) Whitebox.getInternalState(snake, "tail");
	}

	private Field getHead() {
		return (Field) Whitebox.getInternalState(snake, "head");
	}

	private Direction nextDirectionFromSnake() {
		return (Direction) Whitebox.getInternalState(snake, "nextDirection");
	}

	private Direction currentDirectionFromSnake() {
		return (Direction) Whitebox.getInternalState(snake, "currentDirection");
	}
}
