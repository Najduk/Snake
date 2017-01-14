package pl.wat.wcy.snakefx.core;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.Duration;
import pl.wat.wcy.snakefx.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class GameLoop {

    private static final int ONE_SECOND = 1000;

    private Timeline timeline;


    private final List<Consumer<?>> actions = new ArrayList<>();

    private final ViewModel viewModel;

    public GameLoop(final ViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.collision.addListener(new CollisionListener());
        viewModel.speed.addListener(new SpeedChangeListener());
        viewModel.gameloopStatus.addListener(new StatusChangedListener());

        init();
    }

    public void addActions(final Consumer<?>... actions) {
        this.actions.addAll(Arrays.asList(actions));
    }

    private void init() {
        timeline = new Timeline(buildKeyFrame());
        timeline.setCycleCount(Animation.INDEFINITE);

        timeline.statusProperty().addListener((observable, oldStatus,
                                               newStatus) -> {
            viewModel.gameloopStatus.set(newStatus);
        });
    }

    private KeyFrame buildKeyFrame() {

        final int fps = viewModel.speed.get().getFps();
        final Duration duration = Duration.millis(ONE_SECOND / fps);

        final KeyFrame frame = new KeyFrame(duration, event -> {
            actions.forEach(consumer -> {
                consumer.accept(null);
            });
        });

        return frame;
    }

    private final class StatusChangedListener implements ChangeListener<Status> {
        @Override
        public void changed(final ObservableValue<? extends Status> arg0, final Status oldStatus,
                            final Status newStatus) {

            switch (newStatus) {
                case PAUSED:
                    pause();
                    break;
                case RUNNING:
                    play();
                    break;
                case STOPPED:
                    stop();
                    break;
            }
        }
    }

    private final class SpeedChangeListener implements ChangeListener<SpeedLevel> {
        @Override
        public void changed(final ObservableValue<? extends SpeedLevel> arg0, final SpeedLevel oldSpeed,
                            final SpeedLevel newSpeed) {

            final Status oldStatus = timeline.getStatus();

            if (Status.RUNNING.equals(oldStatus)) {
                pause();
            }

            init();

            if (Status.RUNNING.equals(oldStatus)) {
                play();
            }
        }
    }

    private final class CollisionListener implements ChangeListener<Boolean> {
        @Override
        public void changed(final ObservableValue<? extends Boolean> arg0, final Boolean oldValue,
                            final Boolean newCollision) {
            if (newCollision) {
                stop();
            }
        }
    }

    private void play() {
        timeline.play();
    }

    private void pause() {
        timeline.pause();
    }

    private void stop() {
        timeline.stop();
    }

}