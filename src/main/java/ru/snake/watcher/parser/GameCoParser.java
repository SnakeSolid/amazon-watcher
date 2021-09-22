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
		Element element = document.select("strong.btnPrice").first();

		if (element != null) {
			return element.text().strip();
		}

		return null;
	}

}
