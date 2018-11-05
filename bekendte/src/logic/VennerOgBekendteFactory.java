package logic;

public class VennerOgBekendteFactory {

	public VennerOgBekendte makeVennerOgBekendte() {
		return new VennerOgBekendteImpl();
	}
}
