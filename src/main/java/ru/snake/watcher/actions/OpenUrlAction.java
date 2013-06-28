package ru.snake.watcher.actions;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.AbstractAction;
import javax.swing.ListSelectionModel;

import ru.snake.watcher.cache.IconCache;
import ru.snake.watcher.model.AbstractProductModel;
import ru.snake.watcher.model.ProductInfo;
import ru.snake.watcher.model.ProductModel;

@SuppressWarnings("serial")
public final class OpenUrlAction extends AbstractAction {
	private final AbstractProductModel dataModel;
	private final ListSelectionModel selectionModel;

	public OpenUrlAction(ProductModel dataModel,
			ListSelectionModel selectionModel) {
		this.dataModel = dataModel;
		this.selectionModel = selectionModel;

		putValue(NAME, "Открыть ссылку");
		putValue(SHORT_DESCRIPTION,
				"<HTML><P>Открыть страницу товара в браузере.</HTML>");
		putValue(LARGE_ICON_KEY, IconCache.getImageIcon("browse_large.png"));
	}

	public void actionPerformed(ActionEvent arg0) {
		int selectedIntex;

		selectedIntex = selectionModel.getMinSelectionIndex();

		if (selectedIntex != -1) {
			URI uri;
			ProductInfo productInfo = this.dataModel.get(selectedIntex);

			if (!java.awt.Desktop.isDesktopSupported())
				return;

			Desktop desktop = java.awt.Desktop.getDesktop();

			if (!desktop.isSupported(Action.BROWSE))
				return;

			try {
				uri = new URI(productInfo.getUrl().toString());
			} catch (URISyntaxException e) {
				return;
			}

			try {
				desktop.browse(uri);
			} catch (IOException e) {
				return;
			}
		}
	}

}
