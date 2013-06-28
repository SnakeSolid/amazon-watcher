package ru.snake.watcher.view;
import java.awt.AWTException;


public interface AbstractTrayWindow {
	public abstract void addTrayIcon() throws AWTException;
	public abstract void removeTrayIcon() throws AWTException;

	public abstract void closeWindow();
}