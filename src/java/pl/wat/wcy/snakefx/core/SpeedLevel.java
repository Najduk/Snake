package pl.wat.wcy.snakefx.core;

public enum SpeedLevel {
	SLOW(2),

	MEDIUM(5),

	FAST(15),

	EXTRA(30);

	private int fps;

	private SpeedLevel(final int fps) {
		this.fps = fps;
	}

	public int getFps() {
		return fps;
	}

}