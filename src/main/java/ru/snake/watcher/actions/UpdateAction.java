package ru.snake.watcher.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JLabel;

import ru.snake.watcher.cache.IconCache;
import ru.snake.watcher.model.AbstractProductModel;
import ru.snake.watcher.model.PriceInfo;
import ru.snake.watcher.model.ProductInfo;
import ru.snake.watcher.parser.ParserFactory;
import ru.snake.watcher.parser.SiteParser;

@SuppressWarnings("serial")
public final class UpdateAction extends AbstractAction {
	private final JLabel statusBar;
	private final AbstractProductModel dataModel;

	public UpdateAction(AbstractProductModel dataModel, JLabel statusBar) {
		this.statusBar = statusBar;
		this.dataModel = dataModel;

		putValue(NAME, "Обновить цены");
		putValue(SHORT_DESCRIPTION,
				"<HTML><P>Обновить цены всех товаров.</HTML>");
		putValue(LARGE_ICON_KEY, IconCache.getImageIcon("update_large.png"));
	}

	public void actionPerformed(ActionEvent arg0) {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < dataModel.size(); i++) {
					ProductInfo pi = dataModel.get(i);
					SiteParser parser = ParserFactory.createParser(pi.getHost());

					setStatus(pi.getName());

					if (parser != null) {
						PriceInfo newPrice;

						parser.parseUrl(pi.getUrl());
						newPrice = parser.getPrice();

						pi.setPriceInfo(newPrice);

						dataModel.set(i, pi);
					}
				}

				clearStatus();
			}
		});

		thread.start();
	}

	private void setStatus(String productName) {
		StringBuilder sb = new StringBuilder();

		sb.append("Обновление цены \"");
		sb.append(productName);
		sb.append("\"...");

		statusBar.setVisible(true);
		statusBar.setText(sb.toString());
	}

	private void clearStatus() {
		statusBar.setVisible(false);
	}
}
