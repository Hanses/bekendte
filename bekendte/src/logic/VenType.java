package logic;

public enum VenType {
	FRIEND("Ven"),
	ENEMY("Fjende"),
	FAMILY("Familie");
	
	private String label;

    VenType(String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }
}
