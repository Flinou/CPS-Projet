package Services;

/**
 * 
 * Services fournis par un bloc
 * 
 * @author Matthieu Dien
 *
 */

enum BlocType {
	VIDE,MURBRIQUE,MURMETAL
}

public interface BlocService {
	
	/**
	 * Créé un nouveau bloc
	 * @post getType(init()) == BlocType::VIDE
	 * @post <br />getPowerUp(init()) == PowerUp::RIEN
	 */
	public void init();
	
	
	/**
	 * Donne le type d'un bloc
	 * @return type du bloc
	 */
	public BlocType getType();
	
	
	/**
	 * Donne le contenu d'un bloc
	 * @return contenu du bloc
	 */
	public PowerUp getPowerUp();
	
	
	/**
	 * Définit le type d'un bloc
 	 * @post getType(setType(init(),t)) == t
	 * @param b
	 * 		le type de bloc
	 */
	public void setType(BlocType b);
	
	/**
	 * Définit le PowerUp contenu dans un bloc
	 * @param p
	 * 		le type de PowerUp
	 */
	public void setPowerUp(PowerUp p);
	
}
