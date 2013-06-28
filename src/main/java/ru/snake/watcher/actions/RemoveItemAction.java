package ru.snake.watcher.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import ru.snake.watcher.cache.IconCache;
import ru.snake.watcher.model.AbstractProductModel;

@SuppressWarnings("serial")
public final class RemoveItemAction extends AbstractAction {
	private final JFrame parent;
	private final AbstractProductModel dataModel;
	private final ListSelectionModel selectionModel;

	public RemoveItemAction(JFrame parent, AbstractProductModel dataModel,
			ListSelectionModel selectionModel) {
		this.parent = parent;
		this.dataModel = dataModel;
		this.selectionModel = selectionModel;

		putValue(NAME, "Удалить товар");
		putValue(SHORT_DESCRIPTION,
				"<HTML><P>Удалить активный товар из списка.</HTML>");
		putValue(LARGE_ICON_KEY, IconCache.getImageIcon("remove_large.png"));
	}

	public void actionPerformed(ActionEvent e) {
		int selectedIntex;

		selectedIntex = selectionModel.getMinSelectionIndex();

		if (selectedIntex != -1) {
			int choose = JOptionPane.showConfirmDialog(parent,
					"Удалить выбранный товар?", "Подтверждение...",
					JOptionPane.YES_NO_OPTION);

			if (choose == JOptionPane.YES_OPTION)
				dataModel.remove(selectedIntex);
		}
	}
}
