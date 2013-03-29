package Services;

/**
 * 
 * Services fournis par un Terrain
 * 
 * @author Matthieu Dien
 *
 */

public interface TerrainService {
	
	
	/**
	 * Créé un nouveau terrain de jeu
	 * @param l la largeur
	 * @param h la hauteur
	 * @pre \(l = h = 1 \ [2] \\
	 * 		 l \gt 0\ \\ 
	 * 		h \gt 0\)
	 * @post \( getLargeur(init(l,h))=l \\
	 * 		getHauteur(init(l,h))=h \\
	 * 		\forall i,j \ 0 \le i \le l \ et \ 0 \le j \le h, \ 
	 * 		Bloc::getType(getBloc(init(l,h),i,h))=Bloc::MURMETAL, \\
	 * 		Bloc::getType(getBloc(init(l,h),l,j))=Bloc::MURMETAL, \\
	 * 		si \ i=j=1 \ [2] \ alors \ Bloc::getType(getBloc(init(l,h),i,j))=Bloc::MURMETAL \\
	 * 		sinon \ Bloc::getType(getBloc(init(l,h),i,j)) \ne Bloc::MURMETAL \)  
	 */
	public void init(int l, int h);
	
	
	/**
	 * Retourne la largeur du terrain
	 * @return la largeur du terrain
	 * @inv getlargeur(T)>0
	 */
	public int getNombreColonnes();
	
	
	/**
	 * Retourne la hauteur du terrain
	 * 
	 * @return la hauteur du terrain
	 * @inv getHauteur(T)>0
	 */
	public int getNombreLignes();
	
	
	/**
	 * Retourne le Bloc en position (i,j)
	 * @param i un entier
	 * @param j un entier
	 * @return le bloc en position (i,j)
	 * @pre \(0 \le i \le getLargeur(T) \ et \ 0 \le j \le getHauteur(T) \)
	 */
	public BlocService getBloc(int i, int j);
	
	
	/**
	 * Met le bloc b à la position (i,j)
	 * @param b un bloc
	 * @param i un entier
	 * @param j un entier
 	 * @pre \(0 \le i \le getLargeur(T) \ et \ 0 \le j \le getHauteur(T) \)
	 * @post getBloc(setBloc(T,b,i,j),i,j)==b
	 */
	public void setBloc(BlocService b, int i, int j);
	
}