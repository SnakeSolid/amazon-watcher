package ru.snake.watcher.model;

import ru.snake.watcher.cache.PriceCache;

public final class PriceInfo {
	private String currency; // наименование валюты
	private float factor; // коэффициент перевода в рубли
	private float price; // стоимость в валюте

	public PriceInfo(String currency) {
		this.currency = currency;
		this.factor = PriceCache.getPriceFactor(currency);
		this.price = 0.0f;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
		this.factor = PriceCache.getPriceFactor(currency);
	}

	public float getRealPrice() {
		return factor * price;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String asRealPrice() {
		return String.format("%.2f RUR", factor * price);
	}

	@Override
	public String toString() {
		return String.format("%.2f %s", price, currency);
	}

	public String getCurrency() {
		return currency;
	}

	public String getHTMLCurrency() {
		if (currency.equals("GBP"))
			return "<HTML>&pound; %.2f</HTML>";

		if (currency.equals("USD"))
			return "<HTML>$ %.2f</HTML>";

		if (currency.equals("EUR"))
			return "<HTML>&euro; %.2f</HTML>";

		return "%.2f " + currency;
	}
}
