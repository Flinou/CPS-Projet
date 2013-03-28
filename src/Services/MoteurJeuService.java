package Services;

public interface MoteurJeuService {
	
	
	/* Observators */
	
		
	public int getPasJeuCourant();
	
	public int getMaxPasJeu();
	
	public int getHerosX();
	
	/* Invariants */

	// inv : 0 <= getPasJeuCourant(M) <= getMaxPasJeu(M)
	// inv : 1 <= getHerosX(M) <= getTerrain.getNombreColonnes
	// inv : 1 <= getHerosY(M) <= getTerrain.getNombreColonnes
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
	public void pasJeu(); 
	
	
	

}
