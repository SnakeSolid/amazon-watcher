package ru.snake.watcher.parser;

public final class ParserFactory {

	private final static String AMAZON_DE = "www.amazon.de";
	private final static String GAME_KO_UK = "www.game.co.uk";
	private final static String SHOPTO_NET = "www.shopto.net";

	public static SiteParser createParser(String domain) {
		if (domain.equals(AMAZON_DE))
			return new AmazonParser();

		if (domain.equals(GAME_KO_UK))
			return new GameCoParser();

		if (domain.equals(SHOPTO_NET))
			return new ShoptoParser();

		return null;
	}
}
