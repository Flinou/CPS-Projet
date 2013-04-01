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

}
