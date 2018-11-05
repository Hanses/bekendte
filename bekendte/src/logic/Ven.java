package logic;

import java.io.Serializable;

public class Ven implements Serializable {

	private static final long serialVersionUID = 1L;
	private VenType type;
	private String navn;
	private String email;
	private String telefon;
	
	public Ven(VenType type, String navn, String email, String telefon) {
		this.type = type;
		this.navn = navn;
		this.email = email;
		this.telefon = telefon;
	}

	public VenType getType() {
		return type;
	}

	public String getNavn() {
		return navn;
	}

	public String getEmail() {
		return email;
	}

	public String getTelefon() {
		return telefon;
	}

	public String toCsv() {
		return type.name() + ";" + navn + ";" + email + ";" + telefon;
	}

	@Override
	public String toString() {
		return "Ven [type=" + type + ", navn=" + navn + ", email=" + email + ", telefon=" + telefon + "]";
	}
	
	
}
