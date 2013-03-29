package Services;
/**
 * 
 * Services fournis par une Bombe
 * 
 * @author Antoine FLINOIS
 *
 */
public interface BombeService {
	
	public int getNumero();
	public int getX();
	public int getY();
	public int getAmplitude();
	public int getCompteARebours();
	public boolean vaExploser();
	
	
	/**
	 * Cree une bombe
	 * @param num
	 * 		   le numero de la bombe
	 * @param x
	 * 		   l'abscisse de la bombe
	 * @param y
	 *         l'ordonnee de la bombe
	 * @param amplitude
	 *         l'amplitude de la bombe
	 * @pre 3 <= amplitude <= 11
	 * @post getNumero(init(num,x,y,amplitude) = num 
	 * @post getX(init(num,x,y,amplitude) = x
	 * @post getY(init(num,x,y,amplitude) = y 
	 * @post getamplitude(init(num,x,y,amplitude) = amplitude
	 * @post getCompteARebours(init(num,x,y,amplitude) = 10 
	 */
	public void init(int num, int x,int y, int amplitude);
	 
	//inv : 0 <= getCompteARebours(B) <= 10
	//inv : (vaExploser() == true) <=> (getCompteARebours == 0) 

}
