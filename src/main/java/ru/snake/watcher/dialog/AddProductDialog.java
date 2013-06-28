package ru.snake.watcher.dialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ru.snake.watcher.model.AbstractProductModel;
import ru.snake.watcher.model.ProductInfo;

@SuppressWarnings("serial")
public final class AddProductDialog extends JDialog {
	private final AbstractProductModel dataModel;

	private JTextField productName;
	private JTextField productUrl;
	private JButton okButton;
	private JButton cancelButton;

	public AddProductDialog(JFrame parent, AbstractProductModel dataModel) {
		super(parent);

		this.dataModel = dataModel;

		initComponents();

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setModal(true);
		setVisible(true);
	}

	private void initComponents() {
		productName = new JTextField();
		productUrl = new JTextField(60);
		okButton = new JButton("Сохранить");
		cancelButton = new JButton("Закрыть");

		Box productBox = Box.createVerticalBox();
		productBox.add(new JLabel("Наименование товара:"));
		productBox.add(productName);
		productBox.add(new JLabel("Ссылка на страницу товара:"));
		productBox.add(productUrl);

		Box buttonsBox = Box.createHorizontalBox();
		buttonsBox.add(okButton);
		buttonsBox.add(cancelButton);

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String url = productUrl.getText();
				ProductInfo pi = new ProductInfo();

				pi.setName(productName.getText());

				if (!pi.setUrl(url)) {
					JOptionPane.showMessageDialog(AddProductDialog.this,
							"Неверная ссылка на товар.", "Ошибка...",
							JOptionPane.WARNING_MESSAGE);

					return;
				}

				dataModel.add(pi);
				dispose();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		getContentPane().add(productBox, BorderLayout.CENTER);
		getContentPane().add(buttonsBox, BorderLayout.SOUTH);

		pack();
	}
}
