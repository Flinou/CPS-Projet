package Contracts;

/**
 * 
 * Contrat du moteur de jeu
 * 
 * @author Antoine FLINOIS
 *
 */ 
import java.util.ArrayList;


import java.util.HashMap;

import Decorator.MoteurJeuDecorator;
import Services.*;
import Error.*;

public class MoteurJeuContract extends MoteurJeuDecorator {
	
	public MoteurJeuContract(MoteurJeuService delegate) {
		super(delegate);
	}
	public void checkInvariant(){
		TerrainService terrain = super.getTerrain();
		for (PersonnageJouableService perso : super.getListeJoueurs()){
		int x = perso.getX();
		int y = perso.getY();
		
		if (x < 1 || x > super.getTerrain().getNombreColonnes() - 2){
			throw new InvariantError ("L'abscisse du heros est hors du plateau de jeu");
		}
		if (y < 1 || y > super.getTerrain().getNombreLignes() - 2){
			throw new InvariantError ("L'ordonnee du heros est hors du plateau de jeu");
		}
		if (perso.getForceVitale() < 3 || perso.getForceVitale() > 11){
			throw new InvariantError ("Force Vitale non valide");
		}
		for (Integer num : super.getBombeNumeros()){
			if (super.misEnJoue(x,y,num)){
				int xB = super.getBombe(num).getX();
				int yB = super.getBombe(num).getY();
				int aB = super.getBombe(num).getAmplitude();
				if ((x==xB && Math.abs(y-yB) > aB) && (y==yB && Math.abs(x-xB) > aB)){
					throw new InvariantError("Erreur Mis en joue vrai alors que joueur hors de portee");
				}
			}
			}
		}
		int i = 0;
		//Verification que les bords sont des murs metalliques
		for (int j=0;j<terrain.getNombreColonnes()-1;j++){
			if (terrain.getBloc(i, j).getType() != BlocType.MURMETAL){
				throw new InvariantError("Premiere ligne non remplie par des murs mettaliques");
			}
		}

		int j=0;
		for (i =0;i<terrain.getNombreLignes()-1;i++){
			if (terrain.getBloc(i, j).getType() != BlocType.MURMETAL){
				throw new InvariantError("Premiere colonne non remplie par des murs mettaliques");
			}
		}
		j = terrain.getNombreColonnes() -1;
		for (i=0;i<terrain.getNombreLignes()-1;i++){
			if (terrain.getBloc(i, j).getType() != BlocType.MURMETAL){
				throw new InvariantError("Derniere colonne non remplie par des murs mettaliques");
			}
		}
		i = terrain.getNombreLignes() - 1;
		for (j=0;j<terrain.getNombreLignes()-1;j++){
			if (terrain.getBloc(i, j).getType() != BlocType.MURMETAL){
				throw new InvariantError("Derniere ligne non remplie par des murs mettaliques");
			}
		}
		if (super.getPasJeuCourant() < 1 || super.getPasJeuCourant()>super.getMaxPasJeu()){
			throw new InvariantError ("Le pas de jeu courant n'est pas compris entre 1 et le maximum possible.");
		}
		if (super.getPasJeuCourant() < 1 || super.getPasJeuCourant()>super.getMaxPasJeu()){
			throw new InvariantError ("Le pas de jeu courant n'est pas compris entre 1 et le maximum possible.");
		}
		
		if (super.getBombeNumeros().size() != super.getNbBombes()){
			throw new InvariantError("La taille de la liste des bombes n'est pas egale au nombre de bombes");
		}
		
		if (super.getNbBombes() != super.getBombeNumeros().size()){
			throw new InvariantError("La taille de la liste du numero des bombes n'est pas egale au nombre de bombes");
		}
		 /* CHELOU
		if (super.bombeExiste(num)){
			int cpt = -1;
			for (int i=0;i<getNbBombes();i++){
				if (num == getBombeNumeros().get(i)){
					break;
				} else if (cpt == getNbBombes()){
					throw new InvariantError("La Bombe existe mais num n'est pas dans la liste");
				} 
			}
		}
		*/
		if (super.estFini() && (super.getHerosSante() != Sante.MORT) && (super.getPasJeuCourant() < getMaxPasJeu())){
			throw new InvariantError("Fin du jeu imprevue");
		}
		if (super.resultatFinal() == Resultat.KIDNAPPEURGAGNE && super.getHerosSante() != Sante.MORT){
			throw new InvariantError("Resultat final pour la partie gagnee par le kidnappeur imprevue");
		}
		if (super.resultatFinal() == Resultat.PARTIENULLE && super.getHerosSante() != Sante.VIVANT){
			throw new InvariantError("Resultat final pour la partie nulle imprevue");
		}
	
	}
	public void init(int maxPasJeu){
		//pre : init(MaxPasJeu) require maxPasJeu >= 0
		if (maxPasJeu < 0){
			throw new PreConditionError("Le nombre max de Pas de Jeu est inferieur a zero");
		}
		super.init(maxPasJeu);
		//Post :getMaxPasJeu(init(p))=p
		if (super.getMaxPasJeu()!=maxPasJeu){
			throw new PostConditionError("Le nombre max de Pas de Jeu n'est pas egale a l'argument du init");
		}
		if (super.getPasJeuCourant() != 0){
			throw new PostConditionError("Le pas de jeu courant apres init n'est pas egal a 0");
		}
		if (super.getTerrain().getNombreColonnes() != 15){
			throw new PostConditionError("Le nombre de colonnes du terrain apres l'init n'est pas egal a 15");
		}
		if (super.getTerrain().getNombreLignes() != 13){
			throw new PostConditionError("Le nombre de lignes du terrain apres l'init n'est pas egal a 13");
		}
		if (super.getBombes() != null){
			throw new PostConditionError("Liste de Bombes non nulle après l'init");
		}
		if (super.getTerrain().getBloc(6, 6).getType() != BlocType.VIDE){
			throw new PostConditionError("Case d'apparition des vilains non vide");
		}
		if (super.getHeros().getX() != 2){
			throw new PostConditionError("L'abscisse du heros apres l'initialisation n'est pas egal a deux");
		}
		if (super.getHeros().getY() != 2){
			throw new PostConditionError("L'ordonnee du heros apres l'initialisation n'est pas egal a deux");
		}
		if (super.getKidnappeur().getX() != 10){
			throw new PostConditionError("L'abscisse du kidnappeur apres l'initialisation n'est pas egal a deux");
		}
		if (super.getKidnappeur().getY() != 10){
			throw new PostConditionError("L'ordonnee du kidnappeur apres l'initialisation n'est pas egal a deux");
		}
		
		for (PersonnageJouableService perso : getListeJoueurs()){
		if (perso.getSante() != Sante.VIVANT){
			throw new PostConditionError("La Sante du heros est different de Vivant apres l'init");
		}
		if (perso.getForceVitale() != 3){
			throw new PostConditionError("La force vitale du heros est different de trois après l'initialisation");
		}
		}
		for (VilainService vil : getVilains()){
			if (vil.getX() != 6 || vil.getY() != 6){
				throw new PostConditionError("Vilain non apparu en 6,6");
			}
		}
		checkInvariant();

	}
	public BombeService getBombe(int num){
		// pre : bombeExiste(num)
		if (!super.bombeExiste(num)){
			throw new PreConditionError("La bombe n'existe pas");
		}
		return super.getBombe(num);
	}
	
	public Resultat resultatFinal(){
		//pre : estFini()
		if (!super.estFini()){
			throw new PreConditionError("La partie n'est pas finie donc pas de resultat");
		}
		return super.resultatFinal();
	}
	
	public boolean misEnJoue(int x,int y, int num){
		//pre :bombeExiste(num)
		if (!super.bombeExiste(num)){
			throw new PreConditionError("La bombe n'existe pas");
		}
		return super.misEnJoue(x,y,num);
	}
	public void pasJeu(){
		
		HashMap<PersonnageType, Integer[]> oldcoordpersos = new HashMap<PersonnageType, Integer[]>();
		HashMap<PersonnageType, Integer> oldNbBombespersos = new HashMap<PersonnageType, Integer>();
		HashMap<PersonnageType, Integer> oldForceVitpersos = new HashMap<PersonnageType, Integer>();
		HashMap<PersonnageType, PowerUpType> oldPowerUppersos = new HashMap<PersonnageType, PowerUpType>();
		HashMap<PersonnageType, Integer> oldCompteurFireSuit = new HashMap<PersonnageType, Integer>();
		HashMap<PersonnageType, Boolean> aFireSuit = new HashMap<PersonnageType, Boolean>();
		HashMap<PersonnageType, PowerUpType[]> oldPowerUpAutour = new HashMap<PersonnageType, PowerUpType[]>();
		HashMap<VilainService, Integer[]> oldCoordsVilains = new HashMap<VilainService, Integer[]>();
		HashMap<Integer[],BombeService> hashBombes = super.getHashBombes();
		
		checkInvariant();
		
		TerrainService terrain = super.getTerrain();
		
		
		//Anciennes coordonnées, nombres de bombes, de chaques personnages
		for (PersonnageJouableService personnage : super.getListeJoueurs()){
			PersonnageType type = personnage.getType();
			//Anciennes coordonnées du joueur
			Integer[] coord = {personnage.getX(),personnage.getY()};
			oldcoordpersos.put(type, coord);
			//Tableau regroupant les PowerUp entourant le joueur avant le coup
			PowerUpType[] oldPowerUp = {terrain.getBloc(personnage.getX() - 1, personnage.getY()).getPowerUpType(),terrain.getBloc(personnage.getX() + 1, personnage.getY()).getPowerUpType(),terrain.getBloc(personnage.getX(), personnage.getY() - 1).getPowerUpType(),terrain.getBloc(personnage.getX(), personnage.getY() + 1).getPowerUpType()}; 
			oldPowerUpAutour.put(type, oldPowerUp);
			//Ancien nombre de bombes portés par le joueur
			oldNbBombespersos.put(type, personnage.getNbBombes());
			//Ancienne force vitale du joueur
			oldForceVitpersos.put(type, personnage.getForceVitale());
			//Ancien PowerUp porté par le joueur
			oldPowerUppersos.put(type, personnage.getPowerUp());
			//Ancien compteur pour le PowerUp fireSuit du joueur
			oldCompteurFireSuit.put(type, personnage.getCompteurFireSuit());
			//Boolean vrai si le joueur portait FireSuit avant le pasJeu
			if (personnage.getPowerUp() == PowerUpType.FIRESUIT){
				aFireSuit.put(type, true);
			} else {
				aFireSuit.put(type, false);
			}
		}
		
		//Anciennes coordonnées des Vilains
		for(VilainService vil : super.getVilains()){
			Integer[] coor = {vil.getX(),vil.getY()};
			oldCoordsVilains.put(vil, coor);
		}
		
		
		int oldpasJeuCour = super.getPasJeuCourant(); 
		// Liste des bombes non prêtes à exploser
		ArrayList<BombeService> tranquilles = new ArrayList<BombeService>();
		// Liste des bombes prêtes à exploser
		ArrayList<BombeService> imminentes = new ArrayList<BombeService>();
		// Liste des comptes à rebours des bombes non prêtes à exploser
		HashMap<Integer,Integer> rebourstranquilles = new HashMap<Integer,Integer>();
		//Copie par Valeur du terrain et non par référence
		TerrainService ancienterrain = terrain.clone();
		
		//Initialisation des listes de bombes
		for (BombeService bombe : super.getBombes()){
			if (!bombe.vaExploser()){
				tranquilles.add(bombe);
				rebourstranquilles.put(bombe.getNumero(), bombe.getCompteARebours());
			} else {
				imminentes.add(bombe);
			}
		}
		
		
		//pre : !estFini()
		if (estFini()){
			throw new PreConditionError("PasJeu() impossible, la partie est finie");
		}
		super.pasJeu();
		
		checkInvariant();
		
		for (PersonnageJouableService perso : super.getListeJoueurs()){
			PersonnageType type = perso.getType();
			int ancienx = oldcoordpersos.get(type)[0];
			int ancieny = oldcoordpersos.get(type)[1];
			if (oldpasJeuCour != super.getPasJeuCourant() - 1){
				throw new PostConditionError("PasJeu non incrémenté");
			}
			if (super.getTerrain().getBloc(perso.getX(),perso.getY()).getType() != BlocType.VIDE){
				throw new PostConditionError("Personnage sur une case non vide");
			}
			if (perso.getCommande() == Commande.DROITE && (super.getTerrain().getBloc(ancienx + 1, ancieny).getType() != BlocType.VIDE) && perso.getX() != ancienx){
				throw new PostConditionError("Incoherence abscisse personnage");
			}
			if (perso.getCommande() == Commande.GAUCHE && (super.getTerrain().getBloc(ancienx - 1, ancieny).getType() != BlocType.VIDE) && perso.getX() != ancienx){
				throw new PostConditionError("Incoherence abscisse personnage");
			}
			if (perso.getCommande() == Commande.HAUT && (super.getTerrain().getBloc(ancienx, ancieny-1).getType() != BlocType.VIDE) && perso.getY() != ancieny){
				throw new PostConditionError("Incoherence ordonnée personnage");
			}
			if (perso.getCommande() == Commande.BAS && (super.getTerrain().getBloc(ancienx, ancieny+1).getType() != BlocType.VIDE) && perso.getY() != ancieny){
				throw new PostConditionError("Incoherence ordonnée personnage");
			}
			if (perso.getCommande() == Commande.DROITE && (super.getTerrain().getBloc(ancienx + 1, ancieny).getType() == BlocType.VIDE) && perso.getX() != Math.min(1 + ancienx,super.getTerrain().getNombreColonnes() - 2)){
				throw new PostConditionError("Incoherence abscisse personnage");
			}
			if (perso.getCommande() == Commande.GAUCHE && (super.getTerrain().getBloc(ancienx - 1, ancieny).getType() == BlocType.VIDE) && perso.getX() != Math.max(ancienx - 2,2)){
				throw new PostConditionError("Incoherence abscisse personnage");
			}
			if (perso.getCommande() == Commande.HAUT && (super.getTerrain().getBloc(ancienx, ancieny-1).getType() == BlocType.VIDE) && perso.getY() != Math.max(ancieny - 2,2)){
				throw new PostConditionError("Incoherence ordonnée personnage");
			}
			if (perso.getCommande() == Commande.BAS && (super.getTerrain().getBloc(ancienx, ancieny+1).getType() == BlocType.VIDE) && perso.getY() != Math.min(ancieny + 1 ,super.getTerrain().getNombreLignes() - 2)){
				throw new PostConditionError("Incoherence ordonnée personnage");
			}
			if(perso.getCommande() != Commande.BAS && perso.getCommande() != Commande.HAUT && ancieny != perso.getY()){
				throw new PostConditionError("Incoherence ordonnée personnage");
			}
			if(perso.getCommande() != Commande.DROITE && perso.getCommande() != Commande.GAUCHE && ancienx != perso.getX()){
				throw new PostConditionError("Incoherence abscisse personnage");
			}
			if (ancienterrain.getBloc(perso.getX(), perso.getY()).getPowerUpType() == PowerUpType.BOMBUP && oldNbBombespersos.get(perso.getType()) + 1 != perso.getNbBombes()){
				throw new PostConditionError("PowerUp BombUp non appliqué");
			}
			if(ancienterrain.getBloc(perso.getX(), perso.getY()).getPowerUpType() == PowerUpType.FIREUP && oldForceVitpersos.get(perso.getType()) != 11 && oldForceVitpersos.get(perso.getType()) + 2 != perso.getForceVitale()){
				throw new PostConditionError("PowerUp FireUp non appliqué");
			}
			if(ancienterrain.getBloc(perso.getX(), perso.getY()).getPowerUpType() == PowerUpType.FIREUP && oldForceVitpersos.get(perso.getType()) == 11 && oldForceVitpersos.get(perso.getType()) + 2 == perso.getForceVitale()){
				throw new PostConditionError("PowerUp FireUp appliqué mais dépassant 11");
			}
			
			int[] coords = {perso.getX(),perso.getY()};
			if(oldPowerUppersos.get(perso.getType()) == PowerUpType.WALLPASS && terrain.getBloc(oldcoordpersos.get(perso.getType()) [0] +1, oldcoordpersos.get(perso.getType())[1]).getType() == BlocType.MURBRIQUE && terrain.getBloc(oldcoordpersos.get(perso.getType()) [0] +1, oldcoordpersos.get(perso.getType())[1]).getType() == BlocType.VIDE &&  perso.getCommande() == Commande.DROITE && ancienx + 2 != perso.getX()){
				throw new PostConditionError("PowerUp WALLPASS non appliqué");
			}
			if(oldPowerUppersos.get(perso.getType()) == PowerUpType.WALLPASS && terrain.getBloc(oldcoordpersos.get(perso.getType()) [0]  -1, oldcoordpersos.get(perso.getType())[1]).getType() == BlocType.MURBRIQUE && terrain.getBloc(oldcoordpersos.get(perso.getType()) [0] -1, oldcoordpersos.get(perso.getType())[1]).getType() == BlocType.MURBRIQUE &&  perso.getCommande() == Commande.GAUCHE && ancienx - 2 != perso.getX()){
				throw new PostConditionError("PowerUp WALLPASS non appliqué");
			}
			if(oldPowerUppersos.get(perso.getType()) == PowerUpType.WALLPASS  && terrain.getBloc(oldcoordpersos.get(perso.getType()) [0], oldcoordpersos.get(perso.getType())[1] - 1).getType() == BlocType.MURBRIQUE && terrain.getBloc(oldcoordpersos.get(perso.getType()) [0], oldcoordpersos.get(perso.getType())[1] - 1).getType() == BlocType.MURBRIQUE && ancieny - 2 != perso.getX() && perso.getCommande() == Commande.HAUT){
				throw new PostConditionError("PowerUp WALLPASS non appliqué");
			}
			if(oldPowerUppersos.get(perso.getType()) == PowerUpType.WALLPASS  && terrain.getBloc(oldcoordpersos.get(perso.getType()) [0], oldcoordpersos.get(perso.getType())[1] + 1).getType() == BlocType.MURBRIQUE && terrain.getBloc(oldcoordpersos.get(perso.getType()) [0], oldcoordpersos.get(perso.getType())[1] + 1).getType() == BlocType.MURBRIQUE && perso.getCommande() == Commande.BAS && ancieny + 2 != perso.getX()){
				throw new PostConditionError("PowerUp WALLPASS non appliqué");
			}
			if (hashBombes.get(coords) != null && perso.getPowerUp() != PowerUpType.BOMBPASS){
				throw new PostConditionError("Sur une bombe sans BOMBPASS");
			}
			if(aFireSuit.get(perso.getType()) == true && oldCompteurFireSuit.get(perso.getType()) -1  != perso.getCompteurFireSuit()){
				throw new PostConditionError("Compteur Fire Suite non décrémenté");
			}
			
			PowerUpType tresor = ancienterrain.getBloc(perso.getX(), perso.getY()).getPowerUpType(); 
			if (tresor != PowerUpType.RIEN &&  perso.getPowerUp()!=tresor ){
				throw new PostConditionError("Power Up non recupéré");
			}
			if (perso.getCommande() == Commande.BOMBE && hashBombes.get(coords) == null){
				throw new PostConditionError("Bombe non crée aux coordonnées du joueur");
			}
			if (perso.getCommande() == Commande.BOMBE && hashBombes.get(coords).getAmplitude() != perso.getForceVitale()){
				throw new PostConditionError("Bombe crée n'a pas la bonne amplitude");
			}
			if (perso.getCommande() == Commande.BOMBE && (ancienx != perso.getX() || ancieny != perso.getY())){
				throw new PostConditionError("Joueur a crée une bombe mais s'est déplacé");
			}
			for (BombeService bombe : imminentes){
			if (super.misEnJoue(ancienx,ancieny,bombe.getNumero()) && aFireSuit.get(perso.getType()) != true && perso.getSante() != Sante.MORT){
				throw new PostConditionError("Joueur non mort alors que la bombe a explosé");
			
				}
			}
			for (BombeService bombetranquille : tranquilles){
				if (bombetranquille.getCompteARebours() != rebourstranquilles.get(bombetranquille.getNumero())-1){
					throw new PostConditionError("Compte a rebours des bombes non mis à jour");
				}
			}
			for (BombeService bombeimmin : imminentes){
			for (int j = 1; j<terrain.getNombreColonnes() -2 ;j++){
				for (int i = 1; i<terrain.getNombreLignes() - 2; i++){
					if (((super.misEnJoue(i, j, bombeimmin.getNumero()) && ancienterrain.getBloc(i, j).getType() == BlocType.MURBRIQUE && terrain.getBloc(i, j).getType() != BlocType.VIDE) || (super.misEnJoue(i, j, bombeimmin.getNumero()) && ancienterrain.getBloc(i, j).getType() == BlocType.VIDE && ancienterrain.getBloc(i, j).getPowerUpType() != PowerUpType.RIEN && terrain.getBloc(i, j).getPowerUpType() != PowerUpType.RIEN))){
						throw new PostConditionError("Explosion de bombe n'a pas détruit un mur ou alors un tresor");
					}
				}
			}
			}
			PersonnageJouableService autreJoueur;
			if (perso == super.getHeros()){
				autreJoueur = super.getKidnappeur();
			}else{
				autreJoueur = super.getHeros();
			}
			
			if(perso.getCommande() == Commande.BOMBE && autreJoueur.getCommande() == Commande.BOMBE && super.getNbBombes() != tranquilles.size() + 2){
				throw new PostConditionError("Incohérence entre le nombre de bombes après que les deux joueurs en aient posé une");
			}
			if(perso.getCommande() != Commande.BOMBE && autreJoueur.getCommande() != Commande.BOMBE && super.getNbBombes() != tranquilles.size()){
				throw new PostConditionError("Incohérence entre le nombre de bombes après en avoir posé une");
			}
			for(VilainService vil : super.getVilains()){
				int oldvilainx = oldCoordsVilains.get(vil)[0];
				int oldvilainy = oldCoordsVilains.get(vil)[1];
				if ((ancienx == vil.getX() && ancieny == vil.getY() && oldvilainx == perso.getX() && oldvilainy == perso.getY() && perso.getSante() != Sante.MORT) ||(vil.getX() == perso.getX() && vil.getY()==perso.getY())){
					throw new PostConditionError("Perso non mort alors qu'il a croisé un vilain");
				}
				if (vil.getCommande() == Commande.DROITE && (super.getTerrain().getBloc(oldvilainx + 1, oldvilainy).getType() == BlocType.VIDE) && vil.getX() != Math.min(1 + oldvilainx,super.getTerrain().getNombreColonnes() - 2)){
					throw new PostConditionError("Incoherence abscisse personnage");
				}
				if (vil.getCommande() == Commande.GAUCHE && (super.getTerrain().getBloc(oldvilainx - 1, oldvilainy).getType() == BlocType.VIDE) && vil.getX() != Math.max(oldvilainx - 1,2)){
					throw new PostConditionError("Incoherence abscisse personnage");
				}
				if (vil.getCommande() == Commande.HAUT && (super.getTerrain().getBloc(oldvilainx, oldvilainy-1).getType() == BlocType.VIDE) && vil.getY() != Math.max(oldvilainy - 2,2)){
					throw new PostConditionError("Incoherence ordonnée personnage");
				}
				if (vil.getCommande() == Commande.BAS && (super.getTerrain().getBloc(oldvilainx, oldvilainy+1).getType() == BlocType.VIDE) && vil.getY() != Math.min(oldvilainy + 1 ,super.getTerrain().getNombreLignes() - 2)){
					throw new PostConditionError("Incoherence ordonnée personnage");
				}
				//Fantome bleu
				if (vil.getCommande() == Commande.DROITE && (super.getTerrain().getBloc(oldvilainx + 1, oldvilainy).getType() != BlocType.MURMETAL) && vil.getType() == VilainType.FANTOMEBLEU && vil.getX() != Math.min(1 + oldvilainx,super.getTerrain().getNombreColonnes() - 2)){
					throw new PostConditionError("Incoherence abscisse personnage");
				}
				if (vil.getCommande() == Commande.GAUCHE && (super.getTerrain().getBloc(oldvilainx - 1, oldvilainy).getType() != BlocType.MURMETAL) && vil.getType() == VilainType.FANTOMEBLEU && vil.getX() != Math.max(oldvilainx - 1,2)){
					throw new PostConditionError("Incoherence abscisse personnage");
				}
				if (vil.getCommande() == Commande.HAUT && (super.getTerrain().getBloc(oldvilainx, oldvilainy-1).getType() != BlocType.MURMETAL) && vil.getType() == VilainType.FANTOMEBLEU && vil.getY() != Math.max(oldvilainy - 2,2)){
					throw new PostConditionError("Incoherence ordonnée personnage");
				}
				if (vil.getCommande() == Commande.BAS && (super.getTerrain().getBloc(oldvilainx, oldvilainy+1).getType() != BlocType.MURMETAL)&& vil.getType() == VilainType.FANTOMEBLEU  && vil.getY() != Math.min(oldvilainy + 1 ,super.getTerrain().getNombreLignes() - 2)){
					throw new PostConditionError("Incoherence ordonnée personnage");
				}
				
			}
			
			
	}
}
}