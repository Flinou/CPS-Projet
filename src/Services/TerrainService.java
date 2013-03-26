package Services;

/**
 * 
 * Services fournis par un Terrain
 * 
 * @author Matthieu Dien
 * 
 * @inv \(\forall i,j\)
 *
 */

public interface TerrainService {
	
	
	/**
	 * Créé un nouveau terrain de jeu
	 * @param l
	 * 		largeur
	 * @param h
	 * 		hauteur
	 * @pre \(l = h = 1 \ [2] \\
	 * 		 l \gt 0\ \\ 
	 * 		h \gt 0\)
	 */
	public void init(int l, int h);
	
	public int getLargeur();
	
	public int getHauteur();
	
	public BlocService getBloc(int i, int j);
	
	public void setBloc(BlocService b, int i, int j);
	
}
