package Services;

import java.util.ArrayList;

/**
 * 
 * Services fournis par le moteur du jeu
 * 
 * @author Antoine FLINOIS
 *
 */ 

public interface MoteurJeuService {
	
	
	/* Observators */
	
	/**
	 * Retourne le numero du pas de jeu courant.
	 */
	public int getPasJeuCourant();
	/**
	 * Retourne le nombre maximum de pas de jeu atteignable.
	 */
	public int getMaxPasJeu();
	
	/**
	 * Retourne l'abscisse du heros.
	 */
	public int getHerosX();

	/**
	 * Retourne l'ordonnee du heros.
	 */
	
	public int getHerosY();

	/**
	 * Retourne l'abscisse du kidnappeur.
	 */
	
	public int getKidnappeurX();

	/** 
	 * Retoune le terrain de jeu
	 * @return Terrain de jeu
	 */
	
	public TerrainService getTerrain();
	
	/**
	 * Retourne l'ordonnee du kidnappeur.
	 */
	
	public int getKidnappeurY();
	
	/**
	 * Retourne l'etat de sante du heros
	 * @return Sante Heros
	 */
	public Sante getHerosSante();
	
	/**
	 * Retourne l'etat de sante du kidnappeur
	 * @return Sante Kidnappeur
	 */
	
	public Sante getKidnappeurSante();
	
	/**
	 * Retourne la force vitale du heros
	 * @return force vitale heros
	 */
	
	public int getHerosForceVitale();
	
	/**
	 * Retourne la force vitale du kidnappeur
	 * @return force vitale kidnappeur
	 */
	
	public int getKidnappeurForceVitale();
	
	/**
	 * Verifie si une bombe existe
	 * @param num
	 *  		le numero de la bombe
	 */
	
	public boolean bombeExiste(int num);
	
	/**
	 * Retourne la liste des bombes
	 */
	
	public ArrayList<BombeService> getBombes();
	
	/**
	 * Retourne le nombre de bombes.
	 */
	
	public int getNbBombes();
	
	/**
	 * Renvoie la bombe au numero donne.
	 * @param num
	 * 			le numero de la bombe
	 * @pre bombeExiste(num) == true
	 */
	
	public BombeService getBombe(int num);
	
	/**
	 * Renvoie le tableau contenant les numeros des bombes
	 * @return tableau d'entiers
	 */
	
	public ArrayList<Integer> getBombeNumeros();
	
	/**
	 * Verifie si la partie est finie.
	 */
	
	public boolean estFini();
	
	/**
	 * Renvoie le resultat de la partie
	 * @pre estFini() == true
	 */
	public Resultat resultatFinal();
	
	/**
	 * Verifie si le heros est a portee d'une bombe.
	 * @param x
	 * 			l'abscisse du heros
	 * @param y
	 * 			l'ordonnee du heros
	 * @param num
	 * 			le numero de la bombe
	 * @pre bombeExiste(num) == true
	 */
	public boolean misEnJoue(int x,int y,int num);
	
	/* Invariants */

	// inv : 0 <= getPasJeuCourant(M) <= getMaxPasJeu(M)
	// inv : 1 <= getHerosX(M) <= getTerrain.getNombreColonnes
	// inv : 1 <= getKidnappeurX(M) <= getTerrain.getNombreColonnes
	// inv : 1 <= getHerosY(M) <= getTerrain.getNombreColonnes
	// inv : 1 <= getKidnappeurY(M) <= getTerrain.getNombreLignes
	// inv : 3 <= getHerosForceVitale(M) <= 11
	// inv : getNbBombes(M) = abs(getBombeNumeros(M))
	// inv : bombeExiste(M,num) = (num belongs to getBombeNumeros(M)
	// inv : estFini(M) = (getHerosSante(M) = Sante.MORT || getPasJeuCourant(M)=getMaxPasJeu(M))
	// inv : (resultatFinal(M)=RESULTAT.KIDNAPPEURGAGNE) <=> (getHerosSante(M) = Sante.MORT)
	// inv : (resultatFinal(M)=RESULTAT.PARTIENULLE) => (getHerosSante(M) = Sante.VIVANT
	// inv : misEnJoue(M,Integer x,Integer y, Integer num) => (xB = getBombe(M,num).getX()) && (yB = getBombe(M,num).getY()) && (aB = getBombe(M,num).getAmplitude()) && ((x=yB && abs(y - yB) < aB) || (y=yB && abs (x-xB) < xB)) 
	
	
	/* Constructors */
	
	//pre : maxPasJeu > 0
	//post : getMaxPasJeu(init(p))= p
	//post : getPasJeuCourant(init(p))=0
	//post : getHerosX(init(p))=2
	//post : getHerosY(init(p))=2
	//post : getHerosSante(init(p)) = SANTE.VIVANT
	//post : getHerosForceVitale(init(p))=3
	//post : getTerrain(init(p)).getNombreColonnes = 15
	//post : getTerrain(init(p)).getNombreLignes = 13
	//post : getBombeNumeros(init(p)) = NULL
	
	public void init (int maxPasJeu);
	
	/*Operators*/
	
	//pre: !estFini(M)
	//post : getPasJeuCourant(pasJeu(M,Commande c))= getPasJeuCourant(M) + 1
	//post: getHerosX(pasJeu(M,COMMANDE.GAUCHE)) = min (getHerosX(M) - 1,1)
	//post: getHerosX(pasJeu(M,COMMANDE.DROITE)) = max (getHerosX(M) + 1, getTerrain(M).getNombreColonnes())
	//psot :(c != COMMANDE.GAUCHE && c != COMMANDE.DROITE) => getHerosX(pasJeu(M,c)=getHerosX(M)
	//post: getHerosY(pasJeu(M,COMMANDE.HAUT)) = min (getHerosY(M) - 1, 1)
	//post: getHerosX(pasJeu(M,COMMANDE.BAS)) = max (getHerosY(M) + 1, getTerrain(M).getNombreLignes())
	//post :(c != COMMANDE.HAUT && c != COMMANDE.BAS) => getHerosY(pasJeu(M,c)=getHeros(Y))
	//post :getHerosForceVitale(pasJeu(M,c))=getHerosForceVitale(M)
	//post :getTerrain(pasJeu(M,c))=getTerrain(M)
	//post :
	public void pasJeu(Commande com); 
	
	
	

}
