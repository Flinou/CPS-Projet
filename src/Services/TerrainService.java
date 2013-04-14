package Services;

/**
 * 
 * Services fournis par un Terrain
 * 
 * @use {@link BlocService}
 * @types {@link BlocType}, int
 * 
 * @author Matthieu Dien
 *
 * @inv
 *		\[\forall i,j \ 0 \le i \lt l \ et \ 0 \le j \lt h, \\
 * 		Bloc::getType(getBloc(T,i,h))=Bloc::MURMETAL, \\
 * 		Bloc::getType(getBloc(T,l,j))=Bloc::MURMETAL, \\
 * 		si \ i=j=1 \ [2] \ alors \ Bloc::getType(getBloc(T,i,j))=Bloc::MURMETAL \\
 * 		sinon \ Bloc::getType(getBloc(T,i,j)) \ne Bloc::MURMETAL \) 
  */

public interface TerrainService extends Cloneable{
	
	
	/**
	 * Créé un nouveau terrain de jeu
	 * @param l la largeur
	 * @param h la hauteur
	 * @pre \(l = h = 1 \ [2] \\
	 * 		 l \gt 0\ \\ 
	 * 		h \gt 0\)
	 * @post \( getNombreColonnes(init(l,h))=l \\
	 * 		getNombreLignes(init(l,h))=h \)
	 * 		 
	 */
	public void init(int l, int h);
	
	
	/**
	 * Retourne la largeur du terrain
	 * @return la largeur du terrain
	 * @inv getNombreColonnes(T)>0
	 */
	public int getNombreColonnes();
	
	
	/**
	 * Retourne la hauteur du terrain
	 * 
	 * @return la hauteur du terrain
	 * @inv getNombreLignes(T)>0
	 */
	public int getNombreLignes();
	
	
	/**
	 * Retourne le Bloc en position (i,j)
	 * @param i un entier
	 * @param j un entier
	 * @return le bloc en position (i,j)
	 * @pre \(0 \le i \lt getNombreColonnes(T) \ et \ 0 \le j \lt getNombreLignes(T) \)
	 */
	public BlocService getBloc(int i, int j);
	
	
	/**
	 * Met le bloc b à la position (i,j)
	 * @param b un bloc
	 * @param i un entier
	 * @param j un entier
 	 * @pre \(0 \le i \lt getNombreColonnes(T) \ et \ 0 \le j \lt getNombreLignes(T) \)
	 * @post getBloc(setBloc(T,b,i,j),i,j)==b
	 */
	public void setBloc(BlocService b, int i, int j);
	
	
	public TerrainService clone(); 
	
}