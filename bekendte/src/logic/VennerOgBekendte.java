package logic;

import data.Container;

/**
 * Interface til logiklag
 * 
 * @author hi
 *
 */
public interface VennerOgBekendte {

	/**
	 * 
	 * @param ven
	 * @return
	 */
	public boolean opret(Ven ven);
	
	public boolean opdater(Ven ven);
	
	public boolean slet(String email);
	
	public Ven hent(String email);
	
	public Container<Ven> find(String sogestreng);
	
}
