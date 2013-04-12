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
import Implementation.Terrain;

public class MoteurJeuContract extends MoteurJeuDecorator {
	
	public void checkInvariant(){

		int num = 5;
		PersonnageJouableService heros = super.getHeros();
		int x = heros.getX();
		int y = heros.getY();
		if (super.getPasJeuCourant() < 1 || super.getPasJeuCourant()>super.getMaxPasJeu()){
			throw new InvariantError ("Le pas de jeu courant n'est pas compris entre 1 et le maximum possible.");
		}
		if (x < 1 || x > super.getTerrain().getNombreColonnes()){
			throw new InvariantError ("L'abscisse du heros est hors du plateau de jeu");
		}
		if (y < 1 || y > super.getTerrain().getNombreLignes()){
			throw new InvariantError ("L'ordonnee du heros est hors du plateau de jeu");
		}
		if (super.getHerosForceVitale() < 3 || super.getHerosForceVitale() > 11){
			throw new InvariantError ("Force Vitale non valide");
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
		if (super.estFini() && (super.getHerosSante() != Sante.MORT) && (super.getPasJeuCourant() < getMaxPasJeu())){
			throw new InvariantError("Fin du jeu imprevue");
		}
		if (super.resultatFinal() == Resultat.KIDNAPPEURGAGNE && super.getHerosSante() != Sante.MORT){
			throw new InvariantError("Resultat final pour la partie gagnee par le kidnappeur imprevue");
		}
		if (super.resultatFinal() == Resultat.PARTIENULLE && super.getHerosSante() != Sante.VIVANT){
			throw new InvariantError("Resultat final pour la partie nulle imprevue");
		}
		if (super.misEnJoue(x,y,num)){
			int xB = super.getBombe(num).getX();
			int yB = super.getBombe(num).getY();
			int aB = super.getBombe(num).getAmplitude();
			if ((x==xB && Math.abs(y-yB) > aB) && (y==yB && Math.abs(x-xB) > aB)){
				throw new InvariantError("Erreur Mis en joue vrai alors que joueur hors de portee");
			}
		}
	
	}
	public void init(int maxPasJeu){
		//inv
		checkInvariant();
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
		if (super.getHeros().getX() != 2){
			throw new PostConditionError("L'abscisse du heros apres l'initialisation n'est pas egal a deux");
		}
		if (super.getHeros().getX() != 2){
			throw new PostConditionError("L'ordonnee du heros apres l'initialisation n'est pas egal a deux");
		}
		if (super.getHerosSante() != Sante.VIVANT){
			throw new PostConditionError("La Sante du heros est different de Vivant apres l'init");
		}
		if (super.getHerosForceVitale() != 3){
			throw new PostConditionError("La force vitale du heros est different de trois après l'initialisation");
		}
		if (super.getTerrain().getNombreColonnes() != 15){
			throw new PostConditionError("Le nombre de colonnes du terrain apres l'init n'est pas egal a 15");
		}
		if (super.getTerrain().getNombreLignes() != 13){
			throw new PostConditionError("Le nombre de lignes du terrain apres l'init n'est pas egal a 13");
		}
		if (super.getBombeNumeros() != null){
			throw new PostConditionError("Liste de Bombes non nulle après l'init");
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
			throw new PreConditionError("La partie n'est pas finie");
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
		HashMap<PersonnageType, Integer[]> oldcoordpersos = new HashMap();
		HashMap<PersonnageType, Integer> oldNbBombespersos = new HashMap();
		HashMap<PersonnageType, Integer> oldForceVitpersos = new HashMap();
		HashMap<PersonnageType, PowerUpType> oldPowerUppersos = new HashMap();
		HashMap<PersonnageType, Integer> oldCompteurFireSuit = new HashMap();
		HashMap<PersonnageType, Boolean> aFireSuit = new HashMap();
		HashMap<PersonnageType, PowerUpType[]> oldPowerUpAutour = new HashMap();
		HashMap<PersonnageType, BlocType[]> oldBlocTypeAutour = new HashMap();
		HashMap<VilainService, Integer[]> oldCoordsVilains = new HashMap();
		checkInvariant();
		//Anciennes coordonnées, nombres de bombes, de chaques personnages
		for (PersonnageJouableService personnage : super.getListeJoueurs()){
			Integer[] coord = {personnage.getX(),personnage.getY()};
			TerrainService terrain = super.getTerrain();
			PersonnageType type = personnage.getType();
			PowerUpType[] oldPowerUp = {terrain.getBloc(personnage.getX() - 1, personnage.getY()).getPowerUpType(),terrain.getBloc(personnage.getX() + 1, personnage.getY()).getPowerUpType(),terrain.getBloc(personnage.getX(), personnage.getY() - 1).getPowerUpType(),terrain.getBloc(personnage.getX(), personnage.getY() + 1).getPowerUpType()}; 
			oldPowerUpAutour.put(type, oldPowerUp);
			// Tableau recuperant le type des blocs autour du joueur avant son coup (premier element : gauche du joueur, deuxieme : droite du joueur, troisieme : haut, quatrieme : bas)
			BlocType[] oldBlocType = {terrain.getBloc(coord[0] - 1, coord[1]).getType(),terrain.getBloc(coord[0]+1,coord[1]).getType(),terrain.getBloc(coord[0],coord[1]-1).getType(),terrain.getBloc(coord[0], coord[1] + 1).getType()};
			oldBlocTypeAutour.put(type, oldBlocType);
			oldcoordpersos.put(type, coord);
			oldNbBombespersos.put(type, personnage.getNbBombes());
			oldForceVitpersos.put(type, personnage.getForceVitale());
			oldPowerUppersos.put(type, personnage.getPowerUp());
			oldCompteurFireSuit.put(type, personnage.getCompteurFireSuit());
			if (personnage.getPowerUp() == PowerUpType.FIRESUIT){
				aFireSuit.put(type, true);
			} else {
				aFireSuit.put(type, false);
			}
		}
		
		for(VilainService vil : super.getVilains()){
			Integer[] coor = {vil.getX(),vil.getY()};
			oldCoordsVilains.put(vil, coor);
		}
		
		
		HashMap<Integer[],BombeService> hashBombes = super.getHashBombes();
		int oldpasJeuCour = super.getPasJeuCourant();
		int oldforvitahero = super.getHerosForceVitale(); 
		TerrainService terr = super.getTerrain();
		boolean verifbombe = false;
		ArrayList<BombeService> tranquilles = new ArrayList<BombeService>();
		ArrayList<BombeService> imminentes = new ArrayList<BombeService>();
		HashMap<Integer,Integer> rebourstranquilles = new HashMap<Integer,Integer>();
		int nbbombes = super.getNbBombes();
		TerrainService ancienterrain = terr.clone();
		
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
		//Commande comHeros = super.getHeros().getCommande();
		//Commande comKidnap = super.getKidnappeur().getCommande();
		
		checkInvariant();
		
		for (PersonnageJouableService perso : super.getListeJoueurs()){
			PersonnageType type = perso.getType();
			int ancienx = oldcoordpersos.get(type)[0];
			int ancieny = oldcoordpersos.get(type)[1];
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
			if(oldPowerUppersos.get(perso.getType()) == PowerUpType.WALLPASS && hashBombes.get(coords) == null && terr.getBloc(perso.getX() - 1, perso.getY()).getType() == BlocType.MURBRIQUE &&  perso.getCommande() == Commande.DROITE && ancienx + 2 != perso.getX()){
				throw new PostConditionError("PowerUp WALLPASS non appliqué");
			}
			if(oldPowerUppersos.get(perso.getType()) == PowerUpType.WALLPASS && hashBombes.get(coords) == null && terr.getBloc(perso.getX() + 1, perso.getY()).getType() == BlocType.MURBRIQUE &&  perso.getCommande() == Commande.GAUCHE && ancienx - 2 != perso.getX()){
				throw new PostConditionError("PowerUp WALLPASS non appliqué");
			}
			if(oldPowerUppersos.get(perso.getType()) == PowerUpType.WALLPASS && hashBombes.get(coords) == null && terr.getBloc(perso.getX(), perso.getY() + 1).getType() == BlocType.MURBRIQUE && perso.getCommande() == Commande.HAUT && ancieny - 2 != perso.getX()){
				throw new PostConditionError("PowerUp WALLPASS non appliqué");
			}
			if(oldPowerUppersos.get(perso.getType()) == PowerUpType.WALLPASS && hashBombes.get(coords) == null && terr.getBloc(perso.getX(), perso.getY() - 1).getType() == BlocType.MURBRIQUE && perso.getCommande() == Commande.BAS && ancieny + 2 != perso.getX()){
				throw new PostConditionError("PowerUp WALLPASS non appliqué");
			}
			if (hashBombes.get(coords) != null && perso.getPowerUp() != PowerUpType.BOMBPASS){
				throw new PostConditionError("Sur une bombe sans BOMBPASS");
			}
			if(super.getTerrain().getBloc(perso.getX(), perso.getY()).getType() != BlocType.VIDE){
				throw new PostConditionError("Personnage sur une case non vide");
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
			if (perso.getCommande() == Commande.BOMBE && hashBombes.get(coords).getNumero() != super.getNbBombes() + 1){
				throw new PostConditionError("Bombe crée n'a pas le bon numéro");
			}
			if (perso.getCommande() == Commande.BOMBE && hashBombes.get(coords).getAmplitude() != perso.getForceVitale()){
				throw new PostConditionError("Bombe crée n'a pas la bonne amplitude");
			}
			if (perso.getCommande() == Commande.BOMBE && (ancienx != perso.getX() || ancieny != perso.getY())){
				throw new PostConditionError("Joueur a crée une bombe mais s'est déplacé");
			}
			for (BombeService bombe : super.getBombes()){
			if (super.misEnJoue(ancienx,ancieny,bombe.getNumero()) && perso.getPowerUp() != PowerUpType.FIRESUIT && perso.getSante() != Sante.MORT){
				throw new PostConditionError("Joueur non mort alors que la bombe a explosé");
			
				}
			}
			for (BombeService bombetranquille : tranquilles){
				if (bombetranquille.getCompteARebours() != rebourstranquilles.get(bombetranquille.getNumero())-1){
					throw new PostConditionError("Compte a rebours des bombes non mis à jour");
				}
			}
			for (BombeService bombeimmin : imminentes){
			for (int j = 1; j<terr.getNombreColonnes() -2 ;j++){
				for (int i = 1; i<terr.getNombreLignes() - 2; i++){
					if (((super.misEnJoue(i, j, bombeimmin.getNumero()) && ancienterrain.getBloc(i, j).getType() == BlocType.MURBRIQUE && terr.getBloc(i, j).getType() != BlocType.VIDE) || (super.misEnJoue(i, j, bombeimmin.getNumero()) && ancienterrain.getBloc(i, j).getType() == BlocType.VIDE && ancienterrain.getBloc(i, j).getPowerUpType() != PowerUpType.RIEN && terr.getBloc(i, j).getPowerUpType() != PowerUpType.RIEN))){
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
			
			if(perso.getCommande() == Commande.BOMBE && autreJoueur.getCommande() != Commande.BOMBE && super.getNbBombes() != tranquilles.size() + 1){
				throw new PostConditionError("Incohérence entre le nombre de bombes après en avoir posé une");
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
		
		
		
		/*if (super.getPasJeuCourant() != pasJeuCour + 1){
			throw new PostConditionError("Incoherence entre le nombre de pas de jeu avant et apres pasJeu");
		}
		int x = super.getHeros().getX();
		int y = super.getHeros().getY();
		switch (com){
		case GAUCHE :
			if ((super.getHerosX() != Math.max(1, abscishero - 1)) || (super.getHerosY() != ordonnehero)){
				throw new PostConditionError("Erreur valeur coordonnees heros apres pasJeu");
			}
			if (super.getNbBombes() != tranquilles.size()){
				throw new PostConditionError("Incoherence Nb bombes et taille tranquilles");
			}
		case DROITE :
			if (super.getHerosX() != Math.min(super.getTerrain().getNombreColonnes(), abscishero + 1) || (super.getHerosY() != ordonnehero)){
				throw new PostConditionError("Erreur valeur coordonnes heros apres pasJeu");
			}
			if (super.getNbBombes() != tranquilles.size()){
				throw new PostConditionError("Incoherence Nb bombes et taille tranquilles");
			}
		case HAUT :
			if (super.getHerosX() != Math.max(super.getTerrain().getNombreColonnes(), ordonnehero - 1) || (super.getHerosX() != abscishero)){
				throw new PostConditionError("Erreur valeur coordonnees heros apres pasJeu");
			}
			if (super.getNbBombes() != tranquilles.size()){
				throw new PostConditionError("Incoherence Nb bombes et taille tranquilles");
			}
		case BAS :
			if (super.getHerosX() != Math.min(super.getTerrain().getNombreColonnes(), ordonnehero + 1)|| (super.getHerosX() != abscishero)){
				throw new PostConditionError("Erreur valeur coordonnees heros apres pasJeu");
			}
			if (super.getNbBombes() != tranquilles.size()){
				throw new PostConditionError("Incoherence Nb bombes et taille tranquilles");
			}
		case BOMBE :
			for (int i=0;i<super.getBombeNumeros().size();i++){
				if ((super.getBombe(i).getX() == abscishero) && (super.getBombe(i).getY() == ordonnehero) && (super.getBombe(i).getAmplitude() == super.getHerosForceVitale())){
					verifbombe = true;
					break;
				}
			}
			if (!verifbombe){
				throw new PostConditionError("Bombe non cree apres PasJeu");
			}
			if (super.getNbBombes() != tranquilles.size() + 1){
				throw new PostConditionError("Incoherence nb bombes avec tranquilles");
			}
		}
		
		if (forvitahero != super.getHerosForceVitale()){
			throw new PostConditionError("Incoherence force vitale heros dans PasJeu");
		}
		if (terr != super.getTerrain()){
			throw new PostConditionError("Incoherence Terrain dans PasJeu");
		}
		if (!super.getBombeNumeros().contains(tranquilles)){
			throw new PostConditionError("Tranquilles n'est pas compris dans les bombes du jeu");
		}
		for (int i=0;i<super.getBombeNumeros().size();i++){
			if(super.getBombe(super.getBombeNumeros().get(i)).getCompteARebours() != rebourstranquilles.get(super.getBombeNumeros().get(i)) - 1){
				throw new PostConditionError("Incoherences dans les comptes a rebours");
			}
		
		
	}
	
	
	}
}
*/
