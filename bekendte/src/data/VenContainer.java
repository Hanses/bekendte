package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import logic.Ven;
import logic.VenType;

public class VenContainer {

	private List<Ven> venneListe = new ArrayList<>();
	private String dbfile = null;
	
	public VenContainer() {
		this.dbfile = null;
	}
	
	public VenContainer(String dbfile) {
		this.dbfile = dbfile;
		try {
			rollback();
		} catch (IOException e) {
		}
	}
	
		
	public void addElement(Ven ven) {
		venneListe.add(ven);
	}
	
	public Ven getElement(int i) {
		return venneListe.get(i);
	}
	
	public void removeElement(int i) {
		venneListe.remove(i);
	}
	
	public void replaceElement(int i, Ven ven) {
		venneListe.set(i, ven);
	}
	
	public int size() {
		return venneListe.size();
	}
	
	public void sort(Comparator<Ven> comparator) {
		venneListe.sort(comparator);
	}
	
//	public void commit() throws IOException {
//		if (dbfile != null) {
//			try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(dbfile)))) {
//				out.writeObject(this.venneListe);
//			}
//		}
//	}
//	
//	@SuppressWarnings("unchecked")
//	public void rollback() throws FileNotFoundException, IOException, ClassNotFoundException {
//		File db = new File(this.dbfile);
//		if (db.exists()) {
//			try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(db))) {
//				this.venneListe = (List<Ven>) in.readObject();
//			}
//		}
//	}
	
	public void commit() throws IOException {
		try (PrintWriter writer = new PrintWriter(new FileWriter(dbfile))) {
			writer.println("type;navn;email;telefon");
			for (Ven element: venneListe) {
				writer.println(element.toCsv());
			}
		}
	}
	
	public void rollback() throws IOException, IOException {
		this.venneListe = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(dbfile))) {
			String csv = reader.readLine(); // Overskrift
			csv = reader.readLine();
			while(csv != null) {
				String[] parts = csv.split(";");
				if (parts.length == 4) {
					Ven ven = new Ven(VenType.valueOf(parts[0]), parts[1], parts[2], parts[3]);
					venneListe.add(ven);
				}
				csv = reader.readLine();
			}
		}
	}

}
