package Services;

/**
 * 
 * Service Personnage donnant les méthodes communes aux vilains, héros et méchant
 * 
 * @types {@link Sante}, {@link Commande}, int
 * 
 * @author matthieu
 *
 */

public interface PersonnageService {
	
	/**
	 * Créé un nouveau Personnage
	 * @param x
	 * 		position en abscisse
	 * @param y
	 * 		position en ordonnée
	 * @pre \( x \ge 0 \ et \ y \ge 0 \)
	 * @post 
	 */
	public void init(int x, int y);
	
	/**
	 * @return la santé du personnage (mort ou vif)
	 */
	public Sante getSante();
	
	/**
	 * Modifie la santé
	 * 
	 *  @post getSante(setSante(P,s)) == s
	 */
	public void setSante(Sante s);
	
	/**
	 * @return la prochaine action du personnage
	 */
	public Commande getCommande();
	
	/**
	 * @return l'abscisse de la position du personnage
	 * @inv \(getX(P) \ge 0 \)
	 */
	public int getX();
	
	/**
	 * @return l'ordonnée de la position du personnage
	 * @inv \(getY(P) \ge 0\)
	 */
	public int getY();
	
	/**
	 * Définit la position en abscisse
	 * @param x
	 * 		la nouvelle abscisse
	 * @pre \(x \ge 0\)
	 * @post getX(setX(T,x)) == x
	 * 		<br /> getY(setX(T,x)) == getY(T)
	 */
	public void setX(int x);
	
	/**
	 * Définit la position en ordonnée
	 * @param y
	 * 		la nouvelle ordonnée
	 * @pre \(y \ge 0\)
	 * @post getY(setY(T,y)) == y
	 * 		<br \>getX(setY(T,y)) == getX(T)
	 */
	public void setY(int y);
}
