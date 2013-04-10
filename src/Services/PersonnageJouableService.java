package Services;

/**
 * 
 * Service du héros et du vilain, raffinant {@link PersonnageService}
 * 
 * @types {@link PersonnageType}, {@link Sante}, {@link Commande}, int
 * 
 * @author matthieu
 *
 */

public interface PersonnageJouableService extends PersonnageService {
	
	/**
	 * Crée un personnage jouable
	 * @param i
	 * 		position en abscisse
	 * @param j
	 * 		position en ordonnée
	 * @param v
	 * 		type de personnage
	 * @pre \( i \ge 0 \ et \ j \ge 0\)
	 * @post getType(init(i,j,v)) == v
	 * 		<br /> getForceVitale(init(i,j,v)) == 3
	 *		<br /> getX(init(i,j,v)) = x
	 * 		<br /> getY(init(i,j,v)) = y
	 * 		<br /> getSante(init(i,j,v)) == Sante::VIVANT
	 * 		<br /> getCompteurFireUp(init(i,j,v)) == 0
	 * 		<br /> getNbBombes(init(i,j,v)) == 1
	 */
	public void init(int i, int j, PersonnageType v);
	
	/**
	 * @return la force vitale du personnage
	 * @inv \( 3 \le getForceVitale(P) \le 11 \) 
	 */
	public int getForceVitale();
	
	
	/**
	 * Modifie la force vitale du personnage
	 * @param f
	 * 		la nouvelle force vitale
	 * @pre \( 3 \le f \le 11 \)
	 * @post getForceVitale(setForceVitale(P,f)) == f
	 */
	public void setForceVitale(int f);
	
	/**
	 * @return le type de Personnage
	 */
	public PersonnageType getType();
	
	/**
	 * Augmente de un le nombre de bombes portes par le heros
	 * @pre \( num \ge 0 \) //On peut imaginer des malus empêchant de poser des bombes
	 * @post getNbBombes(setBombe(P,num)) == num
	 */
	public void setBombe(int num);
	
	/**
	 * Augmente de un le nombre de bombes portes par le heros
	 * @post getNbBombes(addBombe(P)) == getNbBombes(P) + 1
	 */
	public void addBombe();
	
	/**
	 * @return le nombre de bombes portes
	 * @inv \ getNbBombes(P) \ge 0 \)
	 */
	
	public int getNbBombes();
	
	/**
	 * @return le PowerUp porté par le Personnage
	 */
	public PowerUpType getPowerUp();
	
	/**
	 * @return le nombre de pas de jeu restants au joueur pour le powerUp FireSuit
	 * @inv \(getCompteurFireUp(P) \ge 0 \) 
	 */
	
	public int getCompteurFireUp();
	
	
	/**
	 * Définit le nombre des pas de jeu pendant lequel le personnage est invincible
	 * 
	 * @param c
	 * 		le nombre de pas de jeu
	 * @pre \( c \ge 0 \)
	 * @post \( getCompteurFireUp(setCompteurFireUp(P,c)) == c \)
	 */
	public void setCompteurFireUp(int c);
	
}
