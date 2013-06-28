package ru.snake.watcher.parser;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import ru.snake.watcher.Util;
import ru.snake.watcher.model.PriceInfo;

public abstract class AbstractParser implements SiteParser {

	private static final int HTTP_TIMEOUT = 60 * 1000;

	private String name;

	protected PriceInfo price;

	public AbstractParser() {
		this.name = "";
		this.price = new PriceInfo("EUR");
	}

	public void parseUrl(String url) {
		Document document;
		String priceString;

		try {
			document = Jsoup.connect(url).timeout(HTTP_TIMEOUT).get();
		} catch (IOException e) {
			name = String.format("Ошибка: %s.", e.getLocalizedMessage());

			return;
		}

		priceString = parseDocument(document);

		if (priceString != null) {
			this.price.setPrice(Util.parseFloat(priceString));
		}
	}

	/**
	 * Returns: string with price value, or null, if price not found
	 */
	protected abstract String parseDocument(Document document);

	public final String getName() {
		return name;
	}

	public final PriceInfo getPrice() {
		return price;
	}

}
