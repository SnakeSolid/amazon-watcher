package ru.snake.watcher.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import ru.snake.watcher.model.PriceInfo;

public final class GameCoParser extends AbstractParser {

	protected static final String PRICE_CURR = "GBP";

	public GameCoParser() {
		super();

		price = new PriceInfo(PRICE_CURR);
	}

	@Override
	protected String parseDocument(Document document) {
		Element element = document.select("ul.mint > li.price").first();

		if (element != null) {
			String[] parts = element.text().split("\\s", 2);

			if (parts.length == 1) {
				return parts[0];
			}

			if (parts.length == 2) {
				return parts[1];
			}
		}

		return null;
	}

}
