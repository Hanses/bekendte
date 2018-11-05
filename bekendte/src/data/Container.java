package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Container<T> {

	private List<T> liste = new ArrayList<>();
	private String dbfile = null;
	
	public Container() {
		this.dbfile = null;
	}
	
	public Container(String dbfile) {
		this.dbfile = dbfile;
		try {
			rollback();
		} catch (ClassNotFoundException | IOException e) {
		}
	}
	
		
	public void addElement(T element) {
		liste.add(element);
	}
	
	public T getElement(int i) {
		return liste.get(i);
	}
	
	public void removeElement(int i) {
		liste.remove(i);
	}
	
	public void replaceElement(int i, T element) {
		liste.set(i, element);
	}
	
	public int size() {
		return liste.size();
	}
	
	public void sort(Comparator<T> comparator) {
		liste.sort(comparator);
	}
	
	public void commit() throws IOException {
		if (dbfile != null) {
			try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(dbfile)))) {
				out.writeObject(this.liste);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void rollback() throws FileNotFoundException, IOException, ClassNotFoundException {
		File db = new File(this.dbfile);
		if (db.exists()) {
			try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(db))) {
				this.liste = (List<T>) in.readObject();
			}
		}
	}
	
}
