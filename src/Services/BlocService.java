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
	 * Créé un nouveau bloc vide ne contenant rien
	 * @post getType(init()) == BlocType::VIDE
	 * 		 <br />getPowerUpType(init()) == PowerUpType::RIEN
	 */
	public void init();
	
	/**
	 * Créé un nouveau bloc de type t ne contenant rien
	 * @param t
	 * 		un type de bloc
	 * @post getType(init(t)) == t
	 * 		 <br />getPowerUpType(init(t)) == PowerUpType::RIEN
	 */
	public void init(BlocType t);
	
	/**
	 * Créé un nouveau bloc de type t contenant un powerup p
	 * @param t
	 * 		un type de bloc
	 * @param p
	 * 		un type de powerup
	 * @post getType(init(t,p)) == t
	 *		 <br />getPowerUpType(init(t,p)) == p
	 */
	public void init(BlocType t, PowerUpType p);
	
	
	/**
	 * Donne le type d'un bloc
	 * @return type du bloc
	 */
	public BlocType getType();
	
	
	/**
	 * Donne le contenu d'un bloc
	 * @return contenu du bloc
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
