package ru.snake.watcher.cache;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import ru.snake.watcher.R;
import ru.snake.watcher.Util;

public final class PriceCache {

	private static Map<String, Float> prices;

	private static Date updated = null;

	public static float getPriceFactor(String currency) {
		if (prices == null)
			prices = new HashMap<String, Float>();

		boolean needUpdate = false;

		needUpdate |= !prices.containsKey(currency);

		if (updated == null) {
			needUpdate = true;
		} else {
			needUpdate |= (new Date().getTime() - updated.getTime()) > R.MILLIS_IN_DAY;
		}

		if (needUpdate) {
			String pageUrl;
			Document document;
			SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

			pageUrl = String.format(R.PRICE_PAGE, df.format(new Date()));

			try {
				document = Jsoup.connect(pageUrl).timeout(R.HTTP_TIMEOUT).get();
			} catch (IOException e) {
				return 0.0f;
			}

			Elements names = document.select(R.NAME_COLUMN);
			Elements mult = document.select(R.COUNT_COLUMN);
			Elements divid = document.select(R.PRICE_COLUMN);

			if (names.size() == mult.size() && names.size() == divid.size()) {
				for (int i = 0; i < names.size(); i++) {
					String name = names.get(i).text()
							.replaceAll(R.NOT_LETTER_REGEX, "");

					float multiplier = Util.parseFloat(mult.get(i).text());
					float divider = Util.parseFloat(divid.get(i).text());
					float price = multiplier * divider;

					prices.put(name, price);
				}
			} else {
				return 0.0f;
			}

			updated = new Date();
		}

		if (prices.containsKey(currency)) {
			return prices.get(currency);
		}

		return 0.0f;
	}

}
