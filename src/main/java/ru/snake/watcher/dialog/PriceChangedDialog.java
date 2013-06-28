package ru.snake.watcher.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.Timer;

import ru.snake.watcher.model.PriceInfo;
import ru.snake.watcher.model.ProductInfo;

@SuppressWarnings("serial")
public final class PriceChangedDialog extends JDialog {
	private Timer timer;

	public PriceChangedDialog(ProductInfo pi, PriceInfo newPrice) {
		super();

		setUndecorated(true);

		initComponents(pi, newPrice);
		initPosition();

		addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {
				dispose();
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}
		});

		setAlwaysOnTop(true);
		setVisible(true);
	}

	private void initPosition() {
		GraphicsConfiguration gc = getGraphicsConfiguration();

		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension windowSize = getSize();
		Rectangle windowRect = new Rectangle();

		windowRect.x = screenSize.width - windowSize.width - screenInsets.right;
		windowRect.y = screenSize.height - windowSize.height
				- screenInsets.bottom;
		windowRect.width = windowSize.width;
		windowRect.height = windowSize.height;

		setBounds(windowRect);
	}

	private void initComponents(ProductInfo pi, PriceInfo newPrice) {
		StringBuilder sb = new StringBuilder();
		JLabel statusText = new JLabel();

		timer = new Timer(10 * 1000, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		timer.start();

		sb.append("<HTML>");

		if (pi.getPrice() < newPrice.getPrice())
			sb.append("<P><H3>Цена на товар поднялась</H3>");
		else
			sb.append("<P><H3>Цена на товар снизилась</H3>");

		float diff = Math.abs(pi.getRealPrice() - newPrice.getRealPrice());

		sb.append("<P>Наименование товара: <A href=\"");
		sb.append(pi.getUrl());
		sb.append("\">");
		sb.append(pi.getName());
		sb.append("</A>");
		sb.append("<HR>");
		sb.append("<TABLE><TR><TH>Старая цена (руб.):&nbsp;<TD>");
		sb.append(pi.getPriceInfo().asRealPrice());
		sb.append("<TR><TH>Новая цена (руб.):&nbsp;<TD>");
		sb.append(newPrice.asRealPrice());
		sb.append("<TR><TH>Разница в ценах (руб.):&nbsp;<TD>");
		sb.append(String.format("%.2f RUR", diff));
		sb.append("</TABLE></HTML>");

		statusText.setText(sb.toString());
		statusText.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		statusText.setBackground(Color.LIGHT_GRAY);

		getContentPane().add(statusText, BorderLayout.CENTER);

		pack();
	}

}
