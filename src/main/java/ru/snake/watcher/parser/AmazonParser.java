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
		Element element = document.select(
				"table.product > tbody > tr > td > b.priceLarge").first();

		if (element != null) {
			String[] parts = element.text().split("\\s", 2);

			if (parts.length == 1) {
				return parts[0];
			}

			if (parts.length == 2) {
				price = new PriceInfo(parts[0]);

				return parts[1];
			}
		}

		return null;
	}
}
