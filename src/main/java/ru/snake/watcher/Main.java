package ru.snake.watcher;

import java.awt.AWTException;
import java.io.IOException;

import ru.snake.watcher.view.TrayWindow;

public final class Main {

	/**
	 * @param args
	 * @throws IOException
	 * @throws AWTException
	 */
	public static void main(String[] args) throws AWTException, IOException {
		TrayWindow trayWindow = new TrayWindow();

		trayWindow.setVisible(true);
	}

}
