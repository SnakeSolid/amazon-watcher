package ru.snake.watcher.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import ru.snake.watcher.cache.IconCache;
import ru.snake.watcher.dialog.AddProductDialog;
import ru.snake.watcher.model.AbstractProductModel;

@SuppressWarnings("serial")
public final class AddItemAction extends AbstractAction {
	private final JFrame parent;
	private final AbstractProductModel dataModel;

	public AddItemAction(JFrame parent, AbstractProductModel dataModel) {
		this.parent = parent;
		this.dataModel = dataModel;

		putValue(NAME, "Добавить товар");
		putValue(SHORT_DESCRIPTION, "<HTML><P>Добавить товар в список.</HTML>");
		putValue(LARGE_ICON_KEY, IconCache.getImageIcon("add_large.png"));
	}

	public void actionPerformed(ActionEvent arg0) {
		new AddProductDialog(parent, dataModel);
	}

}
