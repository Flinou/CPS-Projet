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
	 * Retourne le terrain
	 */
	public TerrainService getTerrain();
	
	/**
	 * Retourne la santé du heros.
	 */
	
	public Sante getHerosSante();
	
	/**
	 * Retourne l'etat de sante du kidnappeur
	 * @return Sante Kidnappeur
	 */
	
	public Sante getKidnappeurSante();
	
	/**
	 * @return la commande du heros
	 */
	public Commande getHerosCommande();

	/**
	 * @return la commande du kidnappeur
	 */
	
	public Commande getKidnappeurCommande();
	
	/**
	 * Retourne le heros
	 */
	
	public PersonnageJouableService getHeros();
	
	
	/**
	 * Retourne le kidnappeur
	 */
	
	public PersonnageJouableService getKidnappeur();

	
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
	
	/**
	 * Renvoie la liste des vilains du jeu
	 */
	public ArrayList<VilainService> getVilains();
	
	/* Invariants */

	/**
	*@inv 0 <= getPasJeuCourant(M) <= getMaxPasJeu(M)
	*<br /> 1 <= getHerosX(M) <= getTerrain.getNombreColonnes
	*<br /> 1 <= getKidnappeurX(M) <= getTerrain.getNombreColonnes
	*<br /> 1 <= getHerosY(M) <= getTerrain.getNombreColonnes
	*<br /> 1 <= getKidnappeurY(M) <= getTerrain.getNombreLignes
	*<br /> 3 <= getHerosForceVitale(M) <= 11
	*<br /> getNbBombes(M) = abs(getBombeNumeros(M))
	*<br /> bombeExiste(M,num) = (num belongs to getBombeNumeros(M)
	*<br /> estFini(M) = (getHeros().getSante = Sante.MORT || getKidnappeur().getSante= Sante.MORT  || getPasJeuCourant(M)=getMaxPasJeu(M))
	*<br /> (resultatFinal(M)=RESULTAT.KIDNAPPEURGAGNE) <=> (getHerosSante(M) = Sante.MORT)
	*<br /> (resultatFinal(M)=RESULTAT.PARTIENULLE) => (getHerosSante(M) = Sante.VIVANT)
	*<br /> misEnJoue(M,Integer x,Integer y, Integer num) => (xB = getBombe(M,num).getX()) && (yB = getBombe(M,num).getY()) && (aB = getBombe(M,num).getAmplitude()) && ((x=yB && abs(y - yB) < aB) || (y=yB && abs (x-xB) < xB)) 
	*/
	
	/* Constructors */
	/**
	*@pre  :  maxPasJeu > 0
	*@post : getMaxPasJeu(init(p))= p
	*<br /> getPasJeuCourant(init(p))=0
	*<br /> Pour tout perso in getListeJoueurs()
	*<br /> perso:getSante() = SANTE.VIVANT
	
	*<br /> Pour tout perso in getListeJoueurs()
	*<br /> perso:getForceVitale() = 3
		
	*<br /> Pour tout perso in getListeJoueurs()
	*<br /> 1<=perso:getX()<=getNombreLignes() - 2
		
	*<br /> Pour tout perso in getListeJoueurs()
	*<br /> 1 <= perso:getY() <= getNombreColonnes() - 2
		
	*<br /> getTerrain(init(p)).getNombreColonnes = 15
	*<br /> getTerrain(init(p)).getNombreLignes = 13
	*<br /> getBombeNumeros(init(p)) = NULL
	*/
	public void init (int maxPasJeu);
	
	/*Operators*/
	
	/**
	*@pre !estFini(M)
	*@post getPasJeuCourant(pasJeu(M))= getPasJeuCourant(M) + 1
	
	*<br />  Pour tout perso in getListeJoueurs() :
	*<br /><br />  if (perso::getCommande == Commande.GAUCHE && (BlocService::getBloc(old.perso::getX() -1 ,perso::getY()).getType!=BlocType.VIDE) then perso::getX() = old.perso::getX()

	*<br /><br />  if (perso::getCommande == Commande.DROITE && (BlocService::getBloc(old.perso::getX() + 1 ,perso::getY()).getType!=BlocType.VIDE) then perso::getX() = old.perso::getX()
	
	*<br /><br />  if (perso::getCommande == Commande.HAUT && (BlocService::getBloc(old.perso::getX() ,perso::getY() - 1).getType!=BlocType.VIDE) then perso::getY() = old.perso::getY()
	
	*<br /><br />  if (perso::getCommande == Commande.BAS && (BlocService::getBloc(old.perso::getX() ,perso::getY() + 1).getType!=BlocType.VIDE) then perso::getY() = old.perso::getY()
	
	*<br /><br />  if (perso::getCommande == Commande.GAUCHE && (BlocService::getBloc(perso::getX(),perso::getY()).getType==BlocType.VIDE) then perso:getX() = max (old.perso:getX(M) - 1,1)
	
	*<br /><br />  if perso::getCommande == Commande.DROITE && (BlocService::getBloc(perso::getX(),perso::getY()).getType==BlocType.VIDE) then perso:getX() = min (old.perso:getX(M) + 1,Terrain::getNombreColonnes)
	
	*<br /><br />  if perso::getCommande == Commande.HAUT && (BlocService::getBloc(perso::getX(),perso::getY()).getType==BlocType.VIDE) then perso:getY() = max (old.perso:getY(M) - 1,1)
	
	*<br /><br />  if perso::getCommande == Commande.BAS && (BlocService::getBloc(perso::getX(),perso::getY()).getType==BlocType.VIDE) then perso:getY() = min (old.perso:getY(M) + 1,Terrain::getNombreLignes())
	
	*<br /><br />  (perso::getCommande != Commande.BAS && perso::getCommande != Commande.HAUT) => perso:getY() = old.perso:getY()	
	
	*<br /><br />  if (perso::getCommande != Commande.DROITE && perso::getCommande != Commande.GAUCHE) then perso:getX() = old.perso:getX()
	
	*<br /><br /> if (BlocService::getBloc(perso::getX(),perso::getY()).getPowerUpType == PowerUpType.BOMBUP) And perso::getVientDobtenir == true then old.perso::getNbBombes() + 1 = perso::getNbBombes()
	
	*<br /><br />  
	*<br /><br /> if (BlocService::getBloc(perso::getX(),perso::getY()).getPowerUpType == PowerUpType.BOMBUP) And perso::getVientDobtenir == false then old.perso::getNbBombes() = perso::getNbBombes()
	
	*<br /><br />  
	*<br /><br /> if (BlocService::getBloc(perso::getX(),perso::getY()).getPowerUpType == PowerUpType.FIREUP And perso::getVientDobtenir == true And old.perso::getForceVitale() != 11) then old.perso::getForceVitale() + 2 = perso::getForceVitale()
	
	*<br /><br />  
	*<br /><br /> if (BlocService::getBloc(perso::getX(),perso::getY()).getPowerUpType == PowerUpType.FIREUP And perso::getVientDobtenir == false And old.perso::getForceVitale() != 11) then old.perso::getForceVitale() + 2 = perso::getForceVitale()
	
	*<br /><br />  
	*<br /><br /> if (BlocService::getBloc(perso::getX(),perso::getY()).getPowerUpType == PowerUpType.FIREUP And old.perso::getForceVitale() == 11) then old.perso::getForceVitale() = perso::getForceVitale()
	
	
	*<br /><br /> (perso::getPowerUp == PowerUpType.WALLPASS and BlocService::getBloc(perso::getX(),perso::getY() + 1).getType == Bloc.MURBRIQUE and  BlocService::getBloc(perso::getX(),perso::getY() + 2).getType == Bloc.VIDE and perso::getCommande() == Commande.BAS) => old.perso::getY() + 2 = perso::getY()  
	
	*<br /><br /> (perso::getPowerUp == PowerUpType.WALLPASS and BlocService::getBloc(perso::getX(),perso::getY() - 1).getType == Bloc.MURBRIQUE and  BlocService::getBloc(perso::getX(),perso::getY() - 2).getType == Bloc.VIDE and perso::getCommande() == Commande.HAUT) => old.perso::getY() - 2 = perso::getY()  
	
	*<br /><br /> (perso::getPowerUp == PowerUpType.WALLPASS and BlocService::getBloc(perso::getX() + 1,perso::getY()).getType == Bloc.MURBRIQUE and  BlocService::getBloc(perso::getX() + 2,perso::getY()).getType == Bloc.VIDE and perso::getCommande() == Commande.DROITE) => old.perso::getX() + 2 = perso::getX()  
	
	*<br /><br /> (perso::getPowerUp == PowerUpType.WALLPASS and BlocService::getBloc(perso::getX() - 1,perso::getY()).getType == Bloc.MURBRIQUE and  BlocService::getBloc(perso::getX()-2,perso::getY()).getType == Bloc.VIDE and perso::getCommande() == Commande.GAUCHE) => old.perso::getX() - 2 = perso::getX()  
	
	
	*<br /><br /> (perso::getCommande = Commande.GAUCHE And [perso::getX()-1,perso::getY()] belongs to keys(getHashBombes()) And perso::getPowerUp =! PowerUpType.BOMBPASS) => old.perso::getX() = perso::getX() 
	
	*<br /><br /> (perso::getCommande = Commande.DROITE And [perso::getX()+1,perso::getY()] belongs to keys(getHashBombes()) And perso::getPowerUp =! PowerUpType.BOMBPASS) => old.perso::getX() = perso::getX() 
	
	*<br /><br /> (perso::getCommande = Commande.HAUT And [perso::getX(),perso::getY() - 1] belongs to keys(getHashBombes()) And perso::getPowerUp =! PowerUpType.BOMBPASS) => old.perso::getY() = perso::getY() 
	
	*<br /><br /> (perso::getCommande = Commande.BAS And [perso::getX(),perso::getY() + 1] belongs to keys(getHashBombes()) And perso::getPowerUp =! PowerUpType.BOMBPASS) => old.perso::getY() = perso::getY() 
		
	*<br /><br /> (perso::getPowerUp == TypePowerUp.FIRESUIT) => perso::getCompteurFireUp() = old.perso::getCompteurFireUp() - 1	
	
	*<br /><br />  ((type = old.BlocService::getBloc(perso::getX(), perso::getY()).getPowerUpType) != PowerUpType.RIEN) => perso::getPowerUp == type)  
	
	*<br /><br /> (perso::getCommande()==Commande.BOMBE =>  Bombe::init(getNbBombes() - 1,perso::getX(),perso::getY(),perso::getForceVitale()) \belongs getBombes())
	
	*<br /><br /> Pour tout bombes in old.getBombesImminentes()
	*<br /><br /> (misEnJoue(old.perso::getX(),old.perso::getY(),bombes::getNumero() && perso::getPowerUp != PowerUpType.FIRESUIT) => perso::getSante() = SANTE.MORT

	*<br /><br /> Pour tout vil in getVilains()
	*<br /><br /> Pour tout bombes in old.getBombesImminentes()
	*<br /><br /> (misEnJoue(old.vil::getX(),old.vil::getY(),bombes::getNumero()) => vil::getSante() = SANTE.MORT
	
	*<br /><br /> ((perso::getCommande() == Commande.BOMBE) => getNbBombes() = #(old.getBombes \ old.getBombeImminentes) + 1)
	
	*<br /><br /> ((perso::getCommande() != Commande.BOMBE) => getNbBombes() = #(old.getBombes \ old.getBombeImminentes))
	
	*<br /><br /> Pour tout bombes in (old.getBombes \ old.getBombeImminentes)
	*<br /><br /> bombes::getCompteARebours = old.bombes::getCompteARebours() - 1	
	
	*<br /><br /> (perso::getCommande == Commande.BOMBE) => ( old.perso::getX == perso::getX && old.perso::getY == perso::getY)
	
	*<br /><br /> Pour toutes bombes in old.getBombesImminentes
	*<br /><br /> Pour tout bloc old.b t.q misEnJoue(bombe::getnum,old.b::getX,old.b::getY)
	*<br /><br /> (old.b::getType == Type.MURBRIQUE => b.getType::Type.VIDE) || (old.b::getType == Type.VIDE && old.b::getPowerUp != PowerUpType.RIEN => B::getPowerUpType == PowerUpType.RIEN) 

	*<br /><br /> Pour tout vil in getVilains
	*<br /><br /> Pour tout perso in getListeJoueurs()
	*<br /><br /> (old.perso::getX() == vil::getX() And old.perso::getY() == vil::getY() And perso::getX() == old.vil::getX() And perso::getY() == old.vil::getY() || (perso::getX()==vil::getX() And perso::getY()==vil::getY())) => perso::getSante = SANTE.MORT 
	
	*<br /><br /> Pour tout vil in getVilains
	*<br /><br />  (vil::getCommande == Commande.DROITE && (BlocService::getBloc(old.vil::getX() + 1,old.vil::getY()).getType==BlocType.VIDE)) => vil::getX() = min (old.vil::getX(M) + 1,Terrain::getNombreColonnes)
	
	*<br /><br /> Pour tout vil in getVilains
	*<br /><br />  (vil::getCommande == Commande.GAUCHE && (BlocService::getBloc(old.vil::getX() - 1,old.vil::getY()).getType==BlocType.VIDE)) => vil::getX() = max (old.vil::getX(M) - 1,1)
	
	*<br /><br /> Pour tout vil in getVilains
	*<br /><br />  (vil::getCommande == Commande.BAS && (BlocService::getBloc(old.vil::getX(),old.vil::getY() + 1).getType==BlocType.VIDE)) => vil::getY() = min (old.vil::getY(M) + 1,Terrain::getNombreLignes)
	
	*<br /><br />  Pour tout vil in getVilains()
	*<br /><br />  if vil::getCommande == Commande.HAUT && (BlocService::getBloc(old.vil::getX(),perso::old.getY()).getType==BlocType.VIDE) => vil::getY() = max (old.vil::getY(M) - 1,1)


	*<br /><br /> Pour tout vil in getVilains
	*<br /><br />  (vil::getCommande == Commande.DROITE && (BlocService::getBloc(old.vil::getX() + 1,old.vil::getY()).getType==BlocType.MURBRIQUE) And vil::getType == Type.FANTOMEBLEU) => vil::getX() = min (old.vil::getX(M) + 1,Terrain::getNombreColonnes)
	
	*<br /><br /> Pour tout vil in getVilains
	*<br /><br />  (vil::getCommande == Commande.GAUCHE && (BlocService::getBloc(old.vil::getX() - 1,old.vil::getY()).getType==BlocType.MURBRIQUE) And vil::getType == Type.FANTOMEBLEU)=> vil::getX() = max (old.vil::getX(M) - 1,1)
	
	*<br /><br /> Pour tout vil in getVilains
	*<br /><br />  (vil::getCommande == Commande.BAS && (BlocService::getBloc(old.vil::getX(),old.vil::getY() + 1).getType==BlocType.MURBRIQUE) And vil::getType == Type.FANTOMEBLEU) => vil::getY() = min (old.vil::getY(M) + 1,Terrain::getNombreLignes)
	
	*<br /><br />  Pour tout vil in getVilains()
	*<br /><br />  if vil::getCommande == Commande.HAUT && (BlocService::getBloc(old.vil::getX(),perso::old.getY()).getType==BlocType.MURBRIQUE) And vil::getType == Type.FANTOMEBLEU)=> vil::getY() = max (old.vil::getY(M) - 1,1)
	*/
	public void pasJeu();
	
	
	

}

