package pl.wat.wcy.snakefx.core;


import javafx.animation.Animation.Status;
import pl.wat.wcy.snakefx.core.GameLoop;
import pl.wat.wcy.snakefx.core.SpeedLevel;
import pl.wat.wcy.snakefx.viewmodel.ViewModel;
import javafx.animation.Timeline;
import org.mockito.internal.util.reflection.Whitebox;

import static org.assertj.core.api.Assertions.*;

public class GameLoop {

	private GameLoop gameLoop;
	private ViewModel viewModel;


	public void setup() {
		viewModel = new ViewModel();
		viewModel.speed.set(SpeedLevel.SLOW);
		gameLoop = new GameLoop(viewModel);
	}


	public void StoppedTimelineStaysStoppedAfterSpeedChange() {
		assertThat(getTimeline().getStatus()).isEqualTo(Status.STOPPED);

		viewModel.speed.set(SpeedLevel.FAST);

		assertThat(getTimeline().getStatus()).isEqualTo(Status.STOPPED);
	}

	public void PlayingTimelineStaysPlayingAfterSpeedChange() {
		getTimeline().play();
		assertThat(getTimeline().getStatus()).isEqualTo(Status.RUNNING);

		viewModel.speed.set(SpeedLevel.FAST);


		assertThat(getTimeline().getStatus()).isEqualTo(Status.RUNNING);
	}

	public void TimelineIsPlayingAfterChangeInViewModel() {
		assertThat(viewModel.gameloopStatus.get()).isEqualTo(Status.STOPPED);
		assertThat(getTimeline().getStatus()).isEqualTo(Status.STOPPED);

		viewModel.gameloopStatus.set(Status.RUNNING);

		assertThat(getTimeline().getStatus()).isEqualTo(Status.RUNNING);
	}

	public void PlayingTimelineIsStoppedAfterChangeInViewModel() {
		assertThat(viewModel.gameloopStatus.get()).isEqualTo(Status.STOPPED);

		getTimeline().play();
		assertThat(getTimeline().getStatus()).isEqualTo(Status.RUNNING);
		assertThat(viewModel.gameloopStatus.get()).isEqualTo(Status.RUNNING);

		viewModel.gameloopStatus.set(Status.PAUSED);

		assertThat(getTimeline().getStatus()).isEqualTo(Status.PAUSED);
	}

	private Timeline getTimeline() {
		return (Timeline) Whitebox.getInternalState(gameLoop, "timeline");
	}
}
