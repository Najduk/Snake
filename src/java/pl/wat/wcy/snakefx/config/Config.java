package pl.wat.wcy.snakefx.config;

public enum Config {

	ROW_AND_COLUMN_COUNT(20),

	GRID_SIZE_IN_PIXEL(500),

	SNAKE_START_X(10),

	SNAKE_START_Y(10),

	MAX_SCORE_COUNT(10)

	;

	private Integer value;

	private Config(final Integer value) {
		this.value = value;
	}

	public Integer get() {
		return value;
	}
}