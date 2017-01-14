package pl.wat.wcy.snakefx.viewmodel;

import static pl.wat.wcy.snakefx.config.Config.*;

import javafx.animation.Animation.Status;
import javafx.beans.property.*;
import pl.wat.wcy.snakefx.core.Direction;
import pl.wat.wcy.snakefx.core.SpeedLevel;

public class ViewModel {

	public final IntegerProperty points = new SimpleIntegerProperty(0);

	public final ObjectProperty<SpeedLevel> speed = new SimpleObjectProperty<>(SpeedLevel.MEDIUM);

	public final BooleanProperty collision = new SimpleBooleanProperty(false);

	public final ObjectProperty<Status> gameloopStatus = new SimpleObjectProperty<>(Status.STOPPED);

	public final IntegerProperty gridSize = new SimpleIntegerProperty(ROW_AND_COLUMN_COUNT.get());

	public final BooleanProperty highscoreWindowOpen = new SimpleBooleanProperty(false);

	public final BooleanProperty newHighscoreWindowOpen = new SimpleBooleanProperty(false);

	public final BooleanProperty aboutWindowOpen = new SimpleBooleanProperty(false);

	public final ObjectProperty<Direction> snakeDirection = new SimpleObjectProperty<>(Direction.UP);
}