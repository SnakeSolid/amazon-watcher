package ru.snake.watcher.actions;

import java.awt.AWTException;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ru.snake.watcher.cache.IconCache;
import ru.snake.watcher.view.AbstractTrayWindow;

@SuppressWarnings("serial")
public final class CloseAction extends AbstractAction {
	AbstractTrayWindow trayWindow;

	public CloseAction(AbstractTrayWindow trayWindow) {
		this.trayWindow = trayWindow;

		putValue(NAME, "Закрыть приложение");
		putValue(
				SHORT_DESCRIPTION,
				"<HTML><P>Закрыть приложение и останавливить слежение за изменением цен.</HTML>");
		putValue(LARGE_ICON_KEY, IconCache.getImageIcon("close_large.png"));
	}

	public void actionPerformed(ActionEvent e) {
		try {
			trayWindow.removeTrayIcon();
		} catch (AWTException e1) {
		}

		trayWindow.closeWindow();
	}

}
