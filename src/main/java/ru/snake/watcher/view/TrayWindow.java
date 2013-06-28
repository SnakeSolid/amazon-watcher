package ru.snake.watcher.view;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;

import ru.snake.watcher.actions.AddItemAction;
import ru.snake.watcher.actions.CloseAction;
import ru.snake.watcher.actions.EditItemAction;
import ru.snake.watcher.actions.OpenUrlAction;
import ru.snake.watcher.actions.RemoveItemAction;
import ru.snake.watcher.actions.TableDoubleClick;
import ru.snake.watcher.actions.UpdateAction;
import ru.snake.watcher.cache.IconCache;
import ru.snake.watcher.model.ProductModel;
import ru.snake.watcher.thread.UpdateProductThread;

@SuppressWarnings("serial")
public final class TrayWindow extends JFrame implements AbstractTrayWindow {

	private SystemTray systemTray;
	private TrayIcon trayIcon;
	private JToolBar toolBar;
	private JLabel statusBar;

	private UpdateProductThread updater;

	private AddItemAction addItemAction;
	private EditItemAction editItemAction;
	private RemoveItemAction removeItemAction;
	private OpenUrlAction openUrlAction;
	private UpdateAction updateAction;
	private CloseAction closeAction;

	private ProductModel productModel;
	private JTable productTable;

	public TrayWindow() throws AWTException, IOException {
		super("Слежение за стоимостью тваров");

		initConponents();
		initTrayIcon();

		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
	}

	private void initTrayIcon() throws AWTException, IOException {
		Image iconImage;
		PopupMenu popupMenu;
		MenuItem itemAdd;
		MenuItem itemUpdate;
		MenuItem itemClose;

		iconImage = IconCache.getImage("normal.png");

		popupMenu = new PopupMenu();
		itemAdd = new MenuItem("Добавить товар");
		itemUpdate = new MenuItem("Обновить цены");
		itemClose = new MenuItem("Закрыть приложение");

		itemAdd.addActionListener(addItemAction);
		itemUpdate.addActionListener(updateAction);
		itemClose.addActionListener(closeAction);

		popupMenu.add(itemAdd);
		popupMenu.add(itemUpdate);
		popupMenu.addSeparator();
		popupMenu.add(itemClose);

		trayIcon = new TrayIcon(iconImage,
				"Приложение Amazon watcher.\nНажмите чтобы открыть.", popupMenu);

		trayIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isVisible())
					setVisible(false);
				else
					setVisible(true);
			}
		});

		addTrayIcon();
	}

	/**
	 * @see AbstractTrayWindow#addTrayIcon()
	 */
	public void addTrayIcon() throws AWTException {
		if (systemTray.getTrayIcons().length == 0) {
			systemTray.add(trayIcon);
		}
	}

	/**
	 * @see AbstractTrayWindow#removeTrayIcon()
	 */
	public void removeTrayIcon() throws AWTException {
		if (systemTray.getTrayIcons().length > 0) {
			systemTray.remove(trayIcon);
		}
	}

	/**
	 * @see AbstractTrayWindow#closeWindow()
	 */
	public void closeWindow() {
		setVisible(false);

		updater.interrupt();

		try {
			updater.join();
		} catch (InterruptedException e1) {
		}

		dispose();
	}

	private void initConponents() {
		systemTray = SystemTray.getSystemTray();
		toolBar = new JToolBar();
		statusBar = new JLabel();

		productModel = new ProductModel();
		productTable = new JTable(productModel);

		statusBar.setBorder(BorderFactory
				.createBevelBorder(BevelBorder.LOWERED));
		statusBar.setVisible(false);

		ListSelectionModel selectionModel = productTable.getSelectionModel();
		selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		addItemAction = new AddItemAction(this, productModel);
		editItemAction = new EditItemAction(this, productModel, selectionModel);
		removeItemAction = new RemoveItemAction(this, productModel,
				selectionModel);
		openUrlAction = new OpenUrlAction(productModel, selectionModel);
		updateAction = new UpdateAction(productModel, statusBar);
		closeAction = new CloseAction(this);

		TableDoubleClick tableDoubleClick = new TableDoubleClick(openUrlAction);
		productTable.addMouseListener(tableDoubleClick);

		toolBar.setFloatable(false);
		toolBar.add(addItemAction);
		toolBar.add(editItemAction);
		toolBar.add(removeItemAction);
		toolBar.addSeparator();
		toolBar.add(openUrlAction);
		toolBar.add(updateAction);
		toolBar.addSeparator();
		toolBar.add(closeAction);

		updater = new UpdateProductThread(productModel, statusBar);
		updater.start();

		JScrollPane tableScrollPane = new JScrollPane(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tableScrollPane.setViewportView(productTable);

		getContentPane().add(toolBar, BorderLayout.NORTH);
		getContentPane().add(tableScrollPane, BorderLayout.CENTER);
		getContentPane().add(statusBar, BorderLayout.SOUTH);

		pack();
	}
}
