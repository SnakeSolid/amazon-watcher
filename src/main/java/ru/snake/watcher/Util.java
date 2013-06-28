package ru.snake.watcher;

import ru.snake.watcher.dialog.PriceChangedDialog;
import ru.snake.watcher.model.AbstractProductModel;
import ru.snake.watcher.model.PriceInfo;
import ru.snake.watcher.model.ProductInfo;
import ru.snake.watcher.parser.ParserFactory;
import ru.snake.watcher.parser.SiteParser;

public final class Util {

	private Util() {
	}

	public static float parseFloat(String rawValue) {
		boolean separator;
		float priceMultiplier;
		float result;
		char charSeparator;
		int charLastIndex;

		String value = rawValue.replaceAll(R.NOT_NUMBER_REGEX, "");

		separator = false;
		result = 0.0f;
		priceMultiplier = 0.1f;

		charLastIndex = -1;
		charSeparator = '.';

		for (Character c : R.SEPARATORS.toCharArray()) {
			int charIndex = value.indexOf(c);

			if (charIndex > charLastIndex) {
				charSeparator = c;
				charLastIndex = charIndex;
			}
		}

		for (Character c : value.toCharArray()) {
			if (Character.isDigit(c))
				if (separator) {
					result = result + priceMultiplier * Character.digit(c, 10);
					priceMultiplier *= 0.1;
				} else {
					result = result * 10.0f + Character.digit(c, 10);
				}

			if (c.charValue() == charSeparator) {
				separator = true;
			}
		}

		return result;
	}

	public static void updateProductPrice(AbstractProductModel model, int index) {
		ProductInfo pi = model.get(index);
		SiteParser parser = ParserFactory.createParser(pi.getHost());

		if (parser != null) {
			PriceInfo newPrice;

			parser.parseUrl(pi.getUrl());

			newPrice = parser.getPrice();

			if (newPrice.getRealPrice() > 0.01f
					&& Math.abs(newPrice.getRealPrice() - pi.getRealPrice()) > 0.01f) {
				try {
					new PriceChangedDialog(pi, newPrice);
				} catch (Exception e) {
				}
			}

			pi.setPriceInfo(newPrice);

			model.set(index, pi);
		}
	}

}
