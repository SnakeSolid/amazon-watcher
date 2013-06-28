package ru.snake.watcher.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import ru.snake.watcher.model.PriceInfo;

public class ShoptoParser extends AbstractParser {

	protected static final String PRICE_CURR = "EUR";

	public ShoptoParser() {
	}

	@Override
	protected String parseDocument(Document document) {
		Element element = document.select("div.our-price").first();
		Element currency = document.select("div.our-price > meta").first();

		if (currency != null) {
			price = new PriceInfo(currency.attr("content"));
		}

		if (element != null) {
			return element.text().trim();
		}

		return null;
	}

}
