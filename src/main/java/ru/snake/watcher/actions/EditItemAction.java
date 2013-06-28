package ru.snake.watcher.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;

import ru.snake.watcher.cache.IconCache;
import ru.snake.watcher.dialog.EditProductDialog;
import ru.snake.watcher.model.AbstractProductModel;

@SuppressWarnings("serial")
public final class EditItemAction extends AbstractAction {
	private final JFrame parent;
	private final AbstractProductModel dataModel;
	private final ListSelectionModel selectionModel;

	public EditItemAction(JFrame parent, AbstractProductModel dataModel,
			ListSelectionModel selectionModel) {
		this.parent = parent;
		this.dataModel = dataModel;
		this.selectionModel = selectionModel;

		putValue(NAME, "Изменить товар");
		putValue(SHORT_DESCRIPTION,
				"<HTML><P>Изменить информацию об активном товаре.</HTML>");
		putValue(LARGE_ICON_KEY, IconCache.getImageIcon("edit_large.png"));
	}

	public void actionPerformed(ActionEvent arg0) {
		int selectedIndex = selectionModel.getMinSelectionIndex();

		if (selectedIndex != -1)
			new EditProductDialog(parent, dataModel, selectedIndex);
	}

}
