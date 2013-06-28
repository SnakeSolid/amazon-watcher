package ru.snake.watcher.parser;

import ru.snake.watcher.model.PriceInfo;

public interface SiteParser {

	public abstract void parseUrl(String url);

	public abstract String getName();

	public abstract PriceInfo getPrice();

}