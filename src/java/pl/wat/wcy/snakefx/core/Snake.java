package pl.wat.wcy.snakefx.core;

import static pl.wat.wcy.snakefx.config.Config.*;

import java.util.ArrayList;
import java.util.List;

import pl.wat.wcy.snakefx.viewmodel.ViewModel;


public class Snake {

    private Field head;

    private final Grid grid;

    private final int x;
    private final int y;

    private Direction currentDirection;

    private Direction nextDirection;

    private final List<Field> tail;


    private final ViewModel viewModel;

    public Snake(final ViewModel viewModel, final Grid grid, final GameLoop gameLoop) {
        this.viewModel = viewModel;
        this.grid = grid;
        x = SNAKE_START_X.get();
        y = SNAKE_START_Y.get();

        tail = new ArrayList<Field>();

        gameLoop.addActions(x -> {
            move();
        });

        viewModel.snakeDirection.addListener( (observable,oldDirection,newDirection) -> {
            Snake.this.changeDirection(newDirection);
        });
    }


    public void init() {
        setHead(grid.getXY(x, y));

        viewModel.collision.set(false);

        viewModel.points.set(0);

        currentDirection = Direction.UP;
        nextDirection = Direction.UP;
    }


    private void changeDirection(final Direction newDirection) {
        if (!newDirection.hasSameOrientation(currentDirection)) {
            nextDirection = newDirection;
        }
    }


    void move() {
        currentDirection = nextDirection;

        final Field newHead = grid.getFromDirection(head, currentDirection);

        if (newHead.getState().equals(State.TAIL)) {
            viewModel.collision.set(true);
            return;
        }

        boolean grow = false;
        if (newHead.getState().equals(State.FOOD)) {
            grow = true;
        }

        Field lastField = head;

        for (int i = 0; i < tail.size(); i++) {
            final Field f = tail.get(i);

            lastField.changeState(State.TAIL);
            tail.set(i, lastField);

            lastField = f;
        }

        if (grow) {
            grow(lastField);
            addPoints();
        } else {
            lastField.changeState(State.EMPTY);
        }

        setHead(newHead);
    }

    public void newGame() {
        tail.clear();
        init();
    }

    private void setHead(final Field head) {
        this.head = head;
        head.changeState(State.HEAD);
    }

    private void grow(final Field field) {
        field.changeState(State.TAIL);
        tail.add(field);
    }

    private void addPoints() {
        final int current = viewModel.points.get();
        viewModel.points.set(current + 1);
    }


}