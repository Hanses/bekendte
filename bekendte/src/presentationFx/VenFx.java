package presentationFx;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import logic.Ven;

public class VenFx {

	private StringProperty type = new SimpleStringProperty(this, "type");
	private StringProperty navn = new SimpleStringProperty(this, "navn");
	private StringProperty email = new SimpleStringProperty(this, "email");
	private StringProperty telefon = new SimpleStringProperty(this, "telefon");
	
	public VenFx(Ven ven) {
		setType(new VenTypeConverter().toString(ven.getType()));
		setNavn(ven.getNavn());
		setEmail(ven.getEmail());
		setTelefon(ven.getTelefon());
	}

	public String getType() {
		return type.get();
	}

	public void setType(String type) {
		this.type.set(type);
	}

	public String getNavn() {
		return navn.get();
	}

	public void setNavn(String navn) {
		this.navn.set(navn);
	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(String email) {
		this.email.set(email);
	}

	public String getTelefon() {
		return telefon.get();
	}

	public void setTelefon(String telefon) {
		this.telefon.set(telefon);
	}

}
