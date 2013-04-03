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
import Services.BombeService;
import Services.Commande;
import Services.Resultat;
import Services.Sante;
import Services.TerrainService;
import Error.*;

public class MoteurJeuContract extends MoteurJeuDecorator {
	
	public void checkInvariant(){
		int num = 5;
		int x = super.getHerosX();
		int y = super.getHerosY();
		if (super.getPasJeuCourant() < 1 || super.getPasJeuCourant()>super.getMaxPasJeu()){
			throw new InvariantError ("Le pas de jeu courant n'est pas compris entre 1 et le maximum possible.");
		}
		if (super.getHerosX() < 1 || super.getHerosX() > super.getTerrain().getNombreColonnes()){
			throw new InvariantError ("L'abscisse du heros est hors du plateau de jeu");
		}
		if (super.getHerosY() < 1 || super.getHerosY() > super.getTerrain().getNombreLignes()){
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
			if ((x==xB && Math.abs(y-yB) > aB) || (y==yB && Math.abs(x-xB) > yB)){
				throw new InvariantError("Erreur Mis en joue, hors de portee");
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
		if (super.getHerosX() != 2){
			throw new PostConditionError("L'abscisse du heros apres l'initialisation n'est pas egal a deux");
		}
		if (super.getHerosY() != 2){
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
	public void pasJeu(Commande com){
		checkInvariant();
		int abscishero = super.getHerosX();
		int ordonnehero = super.getHerosY();
		int pasJeuCour = super.getPasJeuCourant();
		int forvitahero = super.getHerosForceVitale(); 
		TerrainService terr = super.getTerrain();
		boolean verifbombe = false;
		ArrayList<Integer> tranquilles = new ArrayList<Integer>();
		HashMap<Integer,Integer> rebourstranquilles = new HashMap<Integer,Integer>();
		
		
		for (int i=0;i<super.getBombeNumeros().size();i++){
			if (!super.getBombe(i).vaExploser()){
				tranquilles.add(super.getBombe(i).getNumero());
				rebourstranquilles.put(super.getBombe(i).getNumero(), super.getBombe(i).getCompteARebours());
			}
		}
		//pre : !estFini()
		if (estFini()){
			throw new PreConditionError("PasJeu() impossible, la partie est finie");
		}
		super.pasJeu(com);
		checkInvariant();
		if (super.getPasJeuCourant() != pasJeuCour + 1){
			throw new PostConditionError("Incoherence entre le nombre de pas de jeu avant et apres pasJeu");
		}
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
