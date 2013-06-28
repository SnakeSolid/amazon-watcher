package ru.snake.watcher.thread;

import javax.swing.JLabel;

import ru.snake.watcher.Util;
import ru.snake.watcher.model.AbstractProductModel;
import ru.snake.watcher.model.ProductInfo;

public final class UpdateProductThread extends Thread {
	private final JLabel statusBar;
	private final AbstractProductModel dataModel;

	public UpdateProductThread(AbstractProductModel dataModel, JLabel statusBar) {
		this.statusBar = statusBar;
		this.dataModel = dataModel;
	}

	@Override
	public void run() {
		int i = 0;

		while (!isInterrupted()) {
			if (dataModel.size() > 0) {
				i = i % dataModel.size();

				ProductInfo pi = dataModel.get(i);
				setStatus(pi.getName());

				Util.updateProductPrice(dataModel, i);

				clearStatus();

				i++;
			}

			try {
				int size;

				size = dataModel.size() + 1;

				sleep(3600 * 1000 / size);
			} catch (InterruptedException e) {
				break;
			}
		}
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
