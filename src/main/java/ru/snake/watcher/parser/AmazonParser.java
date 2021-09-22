package ru.snake.watcher.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import ru.snake.watcher.model.PriceInfo;

public final class AmazonParser extends AbstractParser {

	protected static final String PRICE_REGEX = "<b class=\"priceLarge\">EUR ([0-9\\.,]+)</b>";
	protected static final String PRICE_CURR = "EUR";

	public AmazonParser() {
		super();

		price = new PriceInfo(PRICE_CURR);
	}

	@Override
	protected String parseDocument(Document document) {
		Element element = document.select("#priceblock_ourprice").first();

		if (element != null) {
			return element.text().strip();
		}

		return null;
	}
}
