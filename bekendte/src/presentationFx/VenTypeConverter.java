package presentationFx;

import javafx.util.StringConverter;
import logic.VenType;

public class VenTypeConverter extends StringConverter<VenType>{

	@Override
	public VenType fromString(String arg0) {
		return null;
	}

	@Override
	public String toString(VenType type) {
		switch (type) {
		case FRIEND:
			return "Ven";
		case ENEMY:
			return "Fjende";
		case FAMILY:
			return "Familie";
		default:
			return null;
		}
	}

}
