package ru.snake.watcher.cache;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public final class IconCache {
	private static Map<String, ImageIcon> icons;

	public static Image getImage(String name) {
		Image image;
		String ref = "images/" + name;
		URL url = IconCache.class.getClassLoader().getResource(ref);

		try {
			image = ImageIO.read(url);
		} catch (IOException e) {
			return null;
		}

		return image;
	}

	public static ImageIcon getImageIcon(String name) {
		if (icons == null)
			icons = new HashMap<String, ImageIcon>();

		if (!icons.containsKey(name)) {
			ImageIcon icon;
			String ref = "images/" + name;
			URL url = IconCache.class.getClassLoader().getResource(ref);

			icon = new ImageIcon(url);

			icons.put(name, icon);
		}

		return icons.get(name);
	}
}
