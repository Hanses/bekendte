package logic;

import java.io.IOException;

import data.Container;
import data.VenContainer;

public class VennerOgBekendteImpl implements VennerOgBekendte {

	private VenContainer venner = new VenContainer("dbfile.csv");

	@Override
	public boolean opret(Ven ven) {
		Ven glven = hent(ven.getEmail());
		if (glven == null) {
			venner.addElement(ven);
			try {
				venner.commit();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean opdater(Ven ven) {
		for (int i=0; i < venner.size(); i++) {
			Ven glven = venner.getElement(i);
			if (glven.getEmail().equals(ven.getEmail())) {
				venner.replaceElement(i, ven);
				try {
					venner.commit();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean slet(String email) {
		for (int i=0; i < venner.size(); i++) {
			Ven glven = venner.getElement(i);
			if (glven.getEmail().equals(email)) {
				venner.removeElement(i);
				try {
					venner.commit();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public Ven hent(String email) {
		for (int i = 0; i < venner.size(); i++) {
			Ven ven = venner.getElement(i);
			if (ven.getEmail().equals(email)) {
				return ven;
			}
		}
		return null;
	}

	@Override
	public Container<Ven> find(String sogestreng) {
		Container<Ven> fundneVenner = new Container<>();
		for (int i = 0; i < venner.size(); i++) {
			Ven ven = venner.getElement(i);
			if (ven.getNavn().toUpperCase().contains(sogestreng.toUpperCase()) 
					|| ven.getEmail().toUpperCase().contains(sogestreng.toUpperCase())
					|| ven.getTelefon().toLowerCase().contains(sogestreng.toLowerCase())) {
				fundneVenner.addElement(ven);
			}
		}
		return fundneVenner;
	}

}
