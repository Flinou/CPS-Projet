package Services;

import java.util.ArrayList;
import java.util.HashMap;

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
	public TerrainService getTerrain();
	
	/**
	 * Retourne l'ordonnee du kidnappeur.
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
	 * @return la HashMap ayant pour clés les coordonnées des bombes et pour valeurs les bombes
	 */

	public HashMap<Integer[],BombeService> getHashBombes();
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
	 * @return la listes des bombes sur le point d'exploser
	 */

	public ArrayList<BombeService> getBombesImminentes();

	/**
	 * @return la liste des personnages jouables
	 */
	public ArrayList<PersonnageJouableService> getListeJoueurs();
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
	
	//pre  :  maxPasJeu > 0
	//post : getMaxPasJeu(init(p))= p
	//post : getPasJeuCourant(init(p))=0
	//post : Pour tout perso in getListeJoueurs()
	//post : perso:getSante() = SANTE.VIVANT
	
	//post : Pour tout perso in getListeJoueurs()
	//post : perso:getForceVitale() = 3
		
	//post : Pour tout perso in getListeJoueurs()
	//post : 1<=perso:getX()<=getNombreLignes() - 2
		
	//post : Pour tout perso in getListeJoueurs()
	//post : 1 <= perso:getY() <= getNombreColonnes() - 2
		
	//post : getTerrain(init(p)).getNombreColonnes = 15
	//post : getTerrain(init(p)).getNombreLignes = 13
	//post : getBombeNumeros(init(p)) = NULL
	//post : 
	public void init (int maxPasJeu);
	
	/*Operators*/
	
	//pre: !estFini(M)
	//post : getPasJeuCourant(pasJeu(M))= getPasJeuCourant(M) + 1
	
	//post:  Pour tout perso in getListeJoueurs()
	//post:  if (perso::getCommande == Commande.GAUCHE && (BlocService::getBloc(old.perso::getX() -1 ,perso::getY()).getType!=BlocType.VIDE) then perso::getX() = old.perso::getX()

	//post:  Pour tout perso in getListeJoueurs()
	//post:  if (perso::getCommande == Commande.DROITE && (BlocService::getBloc(old.perso::getX() + 1 ,perso::getY()).getType!=BlocType.VIDE) then perso::getX() = old.perso::getX()
	
	//post:  Pour tout perso in getListeJoueurs()
	//post:  if (perso::getCommande == Commande.HAUT && (BlocService::getBloc(old.perso::getX() ,perso::getY() - 1).getType!=BlocType.VIDE) then perso::getY() = old.perso::getY()
	
	//post:  Pour tout perso in getListeJoueurs()
	//post:  if (perso::getCommande == Commande.BAS && (BlocService::getBloc(old.perso::getX() ,perso::getY() + 1).getType!=BlocType.VIDE) then perso::getY() = old.perso::getY()
	
	//post:  Pour tout perso in getListeJoueurs()
	//post:  if (perso::getCommande == Commande.GAUCHE && (BlocService::getBloc(perso::getX(),perso::getY()).getType==BlocType.VIDE) then perso:getX() = max (old.perso:getX(M) - 1,1)
	
	//post:  Pour tout perso in getListeJoueurs()
	//post:  if perso::getCommande == Commande.DROITE && (BlocService::getBloc(perso::getX(),perso::getY()).getType==BlocType.VIDE) then perso:getX() = min (old.perso:getX(M) + 1,Terrain::getNombreColonnes)
	
	//post:  Pour tout perso in getListeJoueurs()
	//post:  if perso::getCommande == Commande.HAUT && (BlocService::getBloc(perso::getX(),perso::getY()).getType==BlocType.VIDE) then perso:getY() = max (old.perso:getY(M) - 1,1)
	
	//post:  Pour tout perso in getListeJoueurs()
	//post:  if perso::getCommande == Commande.BAS && (BlocService::getBloc(perso::getX(),perso::getY()).getType==BlocType.VIDE) then perso:getY() = min (old.perso:getY(M) + 1,Terrain::getNombreLignes())
	
	//post:  Pour tout perso in getListeJoueurs()
	//post:  (perso::getCommande != Commande.BAS && perso::getCommande != Commande.HAUT) => perso:getY() = old.perso:getY()	
	
	//post:  Pour tout perso in getListeJoueurs()
	//post:  if (perso::getCommande != Commande.DROITE && perso::getCommande != Commande.GAUCHE) then perso:getX() = old.perso:getX()
	
	//post:  Pour tout perso in getListeJoueurs()
	//post : old.getHerosForceVitale=getHerosForceVitale(M)
	
	//post:  Pour tout perso in getListeJoueurs()
	//post : old.getTerrain()=getTerrain(M)
	
	//post:  Pour tout perso in getListeJoueurs()
	//post : if (BlocService::getBloc(perso::getX(),perso::getY()).getPowerUpType == PowerUpType.BOMBUP) then old.perso::getNbBombes() + 1 = perso::getNbBombes()
	
	//post:  Pour tout perso in getListeJoueurs()
	//post : if (BlocService::getBloc(perso::getX(),perso::getY()).getPowerUpType == PowerUpType.FIREUP && old.perso::getForceVitale() != 11) then old.perso::getForceVitale() + 2 = perso::getForceVitale()
	
	//post:  Pour tout perso in getListeJoueurs()
	//post : if (BlocService::getBloc(perso::getX(),perso::getY()).getPowerUpType == PowerUpType.FIREUP && old.perso::getForceVitale() == 11) then old.perso::getForceVitale() = perso::getForceVitale()
	
	
	//post:  Pour tout perso in getListeJoueurs()
	//post: (perso::getPowerUp == PowerUpType.WALLPASS and BlocService::getBloc(perso::getX(),perso::getY() + 1).getType == Bloc.MURBRIQUE and  BlocService::getBloc(perso::getX(),perso::getY() + 2).getType == Bloc.VIDE and perso::getCommande() == Commande.BAS) => old.perso::getY() + 2 = perso::getY()  
	
	//post:  Pour tout perso in getListeJoueurs()
	//post: (perso::getPowerUp == PowerUpType.WALLPASS and BlocService::getBloc(perso::getX(),perso::getY() - 1).getType == Bloc.MURBRIQUE and  BlocService::getBloc(perso::getX(),perso::getY() - 2).getType == Bloc.VIDE and perso::getCommande() == Commande.HAUT) => old.perso::getY() - 2 = perso::getY()  
	
	//post:  Pour tout perso in getListeJoueurs()
	//post: (perso::getPowerUp == PowerUpType.WALLPASS and BlocService::getBloc(perso::getX() + 1,perso::getY()).getType == Bloc.MURBRIQUE and  BlocService::getBloc(perso::getX() + 2,perso::getY()).getType == Bloc.VIDE and perso::getCommande() == Commande.DROITE) => old.perso::getX() + 2 = perso::getX()  
	
	//post:  Pour tout perso in getListeJoueurs()
	//post: (perso::getPowerUp == PowerUpType.WALLPASS and BlocService::getBloc(perso::getX() - 1,perso::getY()).getType == Bloc.MURBRIQUE and  BlocService::getBloc(perso::getX()-2,perso::getY()).getType == Bloc.VIDE and perso::getCommande() == Commande.GAUCHE) => old.perso::getX() - 2 = perso::getX()  
	
	
	//post: Pour tout perso in getListeJoueurs()
	//post: (perso::getCommande = Commande.GAUCHE And [perso::getX()-1,perso::getY()] belongs to keys(getHashBombes()) And perso::getPowerUp =! PowerUpType.BOMBPASS) => old.perso::getX() = perso::getX() 
	
	//post: Pour tout perso in getListeJoueurs()
	//post: (perso::getCommande = Commande.DROITE And [perso::getX()+1,perso::getY()] belongs to keys(getHashBombes()) And perso::getPowerUp =! PowerUpType.BOMBPASS) => old.perso::getX() = perso::getX() 
	
	//post: Pour tout perso in getListeJoueurs()
	//post: (perso::getCommande = Commande.HAUT And [perso::getX(),perso::getY() - 1] belongs to keys(getHashBombes()) And perso::getPowerUp =! PowerUpType.BOMBPASS) => old.perso::getY() = perso::getY() 
	
	//post: Pour tout perso in getListeJoueurs()
	//post: (perso::getCommande = Commande.BAS And [perso::getX(),perso::getY() + 1] belongs to keys(getHashBombes()) And perso::getPowerUp =! PowerUpType.BOMBPASS) => old.perso::getY() = perso::getY() 
		
	//post: Pour tout perso in getListeJoueurs()
	//post : (perso::getPowerUp == TypePowerUp.FIRESUIT) => perso::getCompteurFireUp() = old.perso::getCompteurFireUp() - 1	
	
	//post : Pour tout perso in getListeJoueurs()
	//post:  ((type = BlocService::getBloc(perso::getX() - 1,perso::getY()).getPowerUpType) != PowerUpType.RIEN and  BlocService::getBloc(perso::getX()-1,perso::getY()).getType == Bloc.VIDE and perso::getCommande() == Commande.GAUCHE And perso::getPowerUp == PowerUpType.RIEN) => perso::getPowerUp == type)  
	
	//post : Pour tout perso in getListeJoueurs()
	//post:  ((type = BlocService::getBloc(perso::getX() + 1,perso::getY()).getPowerUpType) != PowerUpType.RIEN and  BlocService::getBloc(perso::getX()+1,perso::getY()).getType == Bloc.VIDE and perso::getCommande() == Commande.DROITE And perso::getPowerUp == PowerUpType.RIEN) => perso::getPowerUp == type)  
	
	
	//post : Pour tout perso in getListeJoueurs()
	//post:  ((type = BlocService::getBloc(perso::getX(),perso::getY() + 1).getPowerUpType) != PowerUpType.RIEN and  BlocService::getBloc(perso::getX(),perso::getY()+1).getType == Bloc.VIDE and perso::getCommande() == Commande.BAS And perso::getPowerUp == PowerUpType.RIEN) => perso::getPowerUp == type)  
	
	
	//post : Pour tout perso in getListeJoueurs()
	//post:  ((type = BlocService::getBloc(perso::getX(),perso::getY() - 1).getPowerUpType) != PowerUpType.RIEN and  BlocService::getBloc(perso::getX(),perso::getY()-1).getType == Bloc.VIDE and perso::getCommande() == Commande.HAUT And perso::getPowerUp == PowerUpType.RIEN) => perso::getPowerUp == type)  
	
	//post : Pour tout perso in getListeJoueurs()
	//post : (perso::getCommande()==Commande.BOMBE =>  Bombe::init(getNbBombes() - 1,perso::getX(),perso::getY(),perso::getForceVitale()) \belongs getBombes())
	
	//post : Pour tout perso in getListeJoueurs()
	//post : Pour tout bombes in old.getBombesImminentes()
	//post : (misEnJoue(perso::getX(),perso::getY(),bombes::getNumero() && perso::getPowerUp != PowerUpType.FIRESUIT) => perso::getSante() = SANTE.MORT

	//post : Pour tout perso in getListeJoueurs()
	//post : Pour tout bombes in old.getBombesImminentes()
	//post : (misEnJoue(perso::getX(),perso::getY(),bombes::getNumero() && perso::getPowerUp == PowerUpType.FIRESUIT) => perso::getSante() = SANTE.VIVANT
	
	//post : Pour tout perso in getListeJoueurs()
	//post : ((perso::getCommande() == Commande.BOMBE) => getNbBombes() = #(old.getBombes \ old.getBombeImminentes) + 1)
	
	//post : Pour tout perso in getListeJoueurs()
	//post : ((perso::getCommande() != Commande.BOMBE) => getNbBombes() = #(old.getBombes \ old.getBombeImminentes))
	
	//post : Pour tout bombes in (old.getBombes \ old.getBombeImminentes)
	//post : bombes::getCompteARebours = old.bombes::getCompteARebours() - 1	
	
	public void pasJeu(Commande com); 
	
	
	

}

