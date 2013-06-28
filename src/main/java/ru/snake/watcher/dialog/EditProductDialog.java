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
public final class EditProductDialog extends JDialog {
	private final AbstractProductModel dataModel;
	private final int selectedIndex;

	private JTextField productName;
	private JTextField productUrl;

	private JButton okButton;
	private JButton cancelButton;

	public EditProductDialog(JFrame parent, AbstractProductModel dataModel,
			int selectedIndex) {
		super(parent);

		this.dataModel = dataModel;
		this.selectedIndex = selectedIndex;

		initComponents();

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setModal(true);
		setVisible(true);
	}

	private void initComponents() {
		ProductInfo pi;

		productName = new JTextField();
		productUrl = new JTextField(60);
		okButton = new JButton("Сохранить");
		cancelButton = new JButton("Закрыть");

		pi = dataModel.get(selectedIndex);
		productName.setText(pi.getName());
		productUrl.setText(pi.getUrl().toString());

		Box productBox = Box.createVerticalBox();
		productBox.add(new JLabel("Наименование товара:"));
		productBox.add(productName);
		productBox.add(new JLabel("Ссылка на страницу товара:"));
		productBox.add(productUrl);

		Box buttonbox = Box.createHorizontalBox();
		buttonbox.add(okButton);
		buttonbox.add(cancelButton);

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String url = productUrl.getText();
				ProductInfo pi = dataModel.get(selectedIndex);

				pi.setName(productName.getText());

				if (!pi.setUrl(url)) {
					JOptionPane.showMessageDialog(EditProductDialog.this,
							"Неверная ссылка на товар.", "Ошибка...",
							JOptionPane.WARNING_MESSAGE);

					return;
				}

				dataModel.set(selectedIndex, pi);
				dispose();
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		getContentPane().add(productBox, BorderLayout.CENTER);
		getContentPane().add(buttonbox, BorderLayout.SOUTH);

		pack();
	}
}
