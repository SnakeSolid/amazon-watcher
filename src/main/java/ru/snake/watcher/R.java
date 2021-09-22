package ru.snake.watcher;

public final class R {

	public static final String PRICE_COLUMN = "table.data > tbody > tr:nth-child(n+2) > td:nth-child(5)";
	public static final String COUNT_COLUMN = "table.data > tbody > tr:nth-child(n+2) > td:nth-child(3)";
	public static final String NAME_COLUMN = "table.data > tbody > tr:nth-child(n+2) > td:nth-child(2)";

	public static final String PRICE_PAGE = "https://www.cbr.ru/currency_base/daily/";

	public static final String SEPARATORS = ",.";

	public static final String NOT_NUMBER_REGEX = "[^0-9,\\.]";
	public static final String NOT_LETTER_REGEX = "[^A-Z]";

	public static final int HTTP_TIMEOUT = 60 * 1000;
	public static final long MILLIS_IN_DAY = 24 * 60 * 60 * 1000;

}
