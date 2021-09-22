package ru.snake.watcher.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public final class ProductModel extends AbstractTableModel implements AbstractProductModel {
	private static final String PRODUCT_LIST_FILE = "productlist.txt";

	private List<ProductInfo> productInfo;

	public ProductModel() {
		productInfo = new ArrayList<ProductInfo>();

		try {
			loadFromFile(PRODUCT_LIST_FILE);
		} catch (IOException e) {
		}
	}

	public int getColumnCount() {
		return 5;
	}

	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Наименование";

		case 1:
			return "Ссылка на страницу";

		case 2:
			return "Текущая цена (у.е.)";

		case 3:
			return "Текущая цена (руб.)";

		case 4:
			return "Есть изменение";

		default:
			return super.getColumnName(column);
		}
	}

	public int getRowCount() {
		return productInfo.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		ProductInfo pi = productInfo.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return pi.toString();

		case 1:
			return pi.getUrl();

		case 2:
			float price = pi.getPrice();

			if (price > 0.01f) {
				String result;

				result = String.format(pi.getPriceInfo().getHTMLCurrency(), price);

				return result;
			}

			break;

		case 3:
			float realPrice = pi.getRealPrice();

			if (realPrice > 0.01f) {
				String result;

				result = String.format("%.2f руб.", realPrice);

				return result;
			}

			break;

		case 4:
			if (pi.getPrice() > 0.01f && pi.getRealPrice() > 0.01f) {
				switch (pi.getDelta()) {
				case LOWERED:
					return "снизилась";

				case RAISED:
					return "повысилась";

				default:
					break;
				}
			}

			break;
		}

		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractProductModel#add(ProductInfo)
	 */

	public void add(ProductInfo e) {
		int oldSize = productInfo.size();

		if (productInfo.add(e)) {
			try {
				saveToFile(PRODUCT_LIST_FILE);
			} catch (IOException e1) {
			}

			fireTableRowsInserted(oldSize - 1, productInfo.size() - 1);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractProductModel#get(int)
	 */

	public ProductInfo get(int index) {
		return productInfo.get(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractProductModel#set(int, ProductInfo)
	 */

	public void set(int index, ProductInfo product) {
		productInfo.set(index, product);

		try {
			saveToFile(PRODUCT_LIST_FILE);
		} catch (IOException e) {
		}

		fireTableRowsUpdated(index, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractProductModel#clear()
	 */

	public void clear() {
		int oldSize = productInfo.size();

		productInfo.clear();

		try {
			saveToFile(PRODUCT_LIST_FILE);
		} catch (IOException e) {
		}

		fireTableRowsDeleted(0, oldSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractProductModel#isEmpty()
	 */

	public boolean isEmpty() {
		return productInfo.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractProductModel#remove(ProductInfo)
	 */

	public void remove(int index) {
		productInfo.remove(index);

		try {
			saveToFile(PRODUCT_LIST_FILE);
		} catch (IOException e) {
		}

		fireTableRowsDeleted(index, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractProductModel#size()
	 */
	public int size() {
		return productInfo.size();
	}

	private void loadFromFile(String fileName) throws IOException {
		try (FileReader fstream = new FileReader(fileName); BufferedReader in = new BufferedReader(fstream)) {
			String line = in.readLine();

			productInfo.clear();

			while (line != null) {
				String data[] = line.split("\t", 4);
				ProductInfo pi = new ProductInfo();

				pi.setName(data[0]);
				pi.setUrl(data[1]);
				pi.setPrice(data[2], Float.parseFloat(data[3]));

				productInfo.add(pi);

				line = in.readLine();
			}
		}

		fireTableRowsInserted(0, productInfo.size() - 1);
	}

	private void saveToFile(String fileName) throws IOException {
		FileWriter fstream = new FileWriter(fileName);
		BufferedWriter out = new BufferedWriter(fstream);

		for (ProductInfo pi : productInfo) {
			StringBuilder sb = new StringBuilder();

			sb.append(pi.getName());
			sb.append("\t");
			sb.append(pi.getUrl());
			sb.append("\t");
			sb.append(pi.getCurrency());
			sb.append("\t");
			sb.append(pi.getPrice());
			sb.append("\n");

			out.write(sb.toString());
		}

		out.close();
	}
}
