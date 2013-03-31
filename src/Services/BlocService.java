package Services;

/**
 * 
 * Services fournis par un bloc
 * 
 * @types {@link BlocType BlocType}, {@link PowerUpType PowerUptype} 
 * 
 * @author Matthieu Dien
 * 
 *
 */



public interface BlocService {
	
	/**
	 * Créé un nouveau bloc
	 * @post getType(init()) == BlocType::VIDE
	 * @post <br />getPowerUpType(init()) == PowerUpType::RIEN
	 */
	public void init();
	
	
	/**
	 * Donne le type d'un bloc
	 * @return type du bloc
	 * @inv \( getType(B) \in BlocType::\{VIDE,MURBRIQUE,MURMETAL\}\)
	 */
	public BlocType getType();
	
	
	/**
	 * Donne le contenu d'un bloc
	 * @return contenu du bloc
	 * @inv \( getPowerUpType(B) \in PowerUpType::\{RIEN,BOMBUP,FIREUP,WALLPASS,BOMBPASS,FIRESUIT\}\)
	 */
	public PowerUpType getPowerUpType();
	
	
	/**
	 * Définit le type d'un bloc
 	 * @post getType(setType(B,t)) == t
	 * @param b
	 * 		le type de bloc
	 */
	public void setType(BlocType b);
	
	/**
	 * Définit le PowerUp contenu dans un bloc
	 * @post getPowerUpType(setPowerUpType(B,p)) == p
	 * @param p
	 * 		le type de PowerUp
	 */
	public void setPowerUpType(PowerUpType p);
	
}
