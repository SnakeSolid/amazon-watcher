package ru.snake.watcher.actions;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;

public final class TableDoubleClick extends MouseAdapter {
	private final Action action;

	public TableDoubleClick(Action action) {
		this.action = action;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2)
			this.action.actionPerformed(null);
	}
}
