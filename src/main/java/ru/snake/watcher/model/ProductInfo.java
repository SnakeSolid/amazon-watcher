package ru.snake.watcher.model;

import java.net.MalformedURLException;
import java.net.URL;

public final class ProductInfo {
	private String name;
	private String url;
	private String host;
	private PriceInfo price;
	private PriceDelta changed;

	public ProductInfo() {
		name = "";
		url = null;
		host = null;
		price = null;
		changed = PriceDelta.NONE;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public boolean setUrl(String url) {
		try {
			URL t = new URL(url);

			host = t.getHost();
		} catch (MalformedURLException e) {
			return false;
		}

		this.url = url;

		return true;
	}

	public void setPrice(String currency, float price) {
		this.price = new PriceInfo(currency);
		this.price.setPrice(price);

		this.changed = PriceDelta.NONE;
	}

	public String getHost() {
		return host;
	}

	public PriceInfo getPriceInfo() {
		return price;
	}

	public float getPrice() {
		if (price == null)
			return 0.0f;

		return price.getPrice();
	}

	public String getStringPrice() {
		return price.toString();
	}

	public PriceDelta getDelta() {
		return changed;
	}

	@Override
	public String toString() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrency() {
		if (price == null)
			return "RUR";

		return price.getCurrency();
	}

	public float getRealPrice() {
		if (price == null)
			return 0.0f;

		return price.getRealPrice();
	}

	public void setPriceInfo(PriceInfo price) {
		this.changed = PriceDelta.NONE;

		if (this.price != null) {
			if (this.price.getPrice() < price.getPrice())
				this.changed = PriceDelta.RAISED;

			if (this.price.getPrice() > price.getPrice())
				this.changed = PriceDelta.LOWERED;
		}

		this.price = price;
	}
}
