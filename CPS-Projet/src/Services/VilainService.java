package Services;

/**
 * 
 * Service des vilains, raffinant {@link PersonnageService}
 * 
 * @types {@link VilainType}, {@link Sante}, {@link Commande}, int
 * 
 * @author matthieu
 *
 */

public interface VilainService extends PersonnageService {
	
	/**
	 * Crée un vilain
	 * @param i
	 * 		position en abscisse
	 * @param j
	 * 		position en ordonnée
	 * @param v
	 * 		type de vilain
	 * @pre \( i \ge 0 \ et \ j \ge 0\)
	 * @post getType(init(i,j,v)) == v
	 *		<br /> getX(init(i,j,v)) = x
	 * 		<br /> getY(init(i,j,v)) = y
	 * 		<br /> getSante(init(i,j,v)) == Sante::VIVANT
	 */
	public void init(int i, int j, VilainType v);
	
	/**
	 * @return le type de vilain
	 */
	public VilainType getType();
	
	
	/**
	 * @return la prochaine commande
	 * @post getCommande(V) \in Commande \backslash \{ Commande::BOMBE \}
	 */
	public Commande getCommande();
}
