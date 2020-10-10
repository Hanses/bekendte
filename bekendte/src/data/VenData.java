package data;

import java.io.IOException;
import java.util.Comparator;

import logic.Ven;

public interface VenData {
	public void addElement(Ven ven);
	public Ven getElement(int i);
	public void removeElement(int i);
	public void replaceElement(int i, Ven ven);
	public int size();
	public void sort(Comparator<Ven> comparator);
	public void commit() throws IOException;
	public void rollback() throws IOException;
}
