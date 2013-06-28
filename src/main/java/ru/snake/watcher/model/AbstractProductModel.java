package ru.snake.watcher.model;

public interface AbstractProductModel {

	public abstract void add(ProductInfo e);

	public abstract ProductInfo get(int index);

	public abstract void set(int index, ProductInfo product);

	public abstract void clear();

	public abstract boolean isEmpty();

	public abstract void remove(int index);

	public abstract int size();

}
