package Contracts;


import Decorator.MoteurJeuDecorator;
import Error.InvariantError;
import Error.PostConditionError;
import Error.PreConditionError;

public class MoteurJeuContract extends MoteurJeuDecorator {
	
	public void checkInvariant(){
		int num = 5; 
		int x = 2;
		int y = 6;
		if (getPasJeuCourant() < 1 || getPasJeuCourant()>getMaxPasJeu()){
			throw new InvariantError ("Le pas de jeu courant n'est pas compris entre 1 et le maximum possible.");
		}
		if (getHerosX() < 1 || getHerosX() > getTerrain().getNombreColonnes()){
			throw new InvariantError ("L'abscisse du heros est hors du plateau de jeu");
		}
		if (getHerosY() < 1 || getHerosY() > getTerrain().getNombreLignes()){
			throw new InvariantError ("L'ordonnée du héros est hors du plateau de jeu");
		}
		if (getHerosForceVitale() < 3 || getHerosForceVitale() > 11){
			throw new InvariantError ("Force Vitale non valide");
		}
		if (getPasJeuCourant() < 1 || getPasJeuCourant()>getMaxPasJeu()){
			throw new InvariantError ("Le pas de jeu courant n'est pas compris entre 1 et le maximum possible.");
		}

		//getNbBombes > A faire !
		
		if (bombeExiste(num)){
			cpt = -1;
			for (int i=0;i<getNbBombes();i++){
				cpt++;
				if (num == getBombeNumerosi()[i]){
					break;
				} else if (cpt == getNbBombes()){
					throw new InvariantError("Bombe existe mais num pas dans la liste");
				} 
			}
		}
		if (estFini() && (getHerosSante != SANTE.MORT) && (getPasJeuCourant < getMaxPasJeu())){
			throw new InvariantError("Fin du jeu imprevue");
		}
		if (resultatFinal() == RESULTAT.KIDNAPPEURGAGNE && getHerosSante() != SANTE.MORT){
			throw new InvariantError("Resultat final pour la partie gagnee par le kidnappeur imprevue")
		}
		if (resultatFinal() == RESULTAT.PARTIENULLE && getHerosSante() != SANTE.VIVANT){
			throw new InvariantError("Resultat final pour la partie nulle imprevue")
		}
		if ((misEnJoue(x,y,num)){
			int xB = getBombe(num).getX();
			int yB = getBombe(num).getY();
			int aB = getBombe(num).getAmplitude();
			if ((x==xB && Math.abs(y-yB) > aB) || (y==yB && Math.abs(x-xB) > yB)){
				throw new InvariantError("Erreur Mis en joue, hors de portee");
			}
		}
	
	}
	public void init(int maxPasJeu){
		//pre : init(MaxPasJeu) require maxPasJeu >= 0
		if (maxPasJeu < 0){
			throw new PreConditionError("Le nombre max de Pas de Jeu est inférieur à zéro");
		}
		super.init(maxPasJeu);
		//Post :getMaxPasJeu(init(p))=p
		if (getMaxPasJeu()!=maxPasJeu){
			throw new PostConditionError("Le nombre max de Pas de Jeu n'est pas égale à l'argument du init");
		}
		if (getPasJeuCourant() != 0){
			throw new PostConditionError("Le pas de jeu courant apres init n'est pas égal à 0");
		}
		if (getHerosX() != 2){
			throw new PostConditionError("L'abscisse du héros apres l'initialisation n'est pas égal à deux");
		}
		if (getHerosY() != 2){
			throw new PostConditionError("L'ordonnée du héros apres l'initialisation n'est pas égal à deux");
		}
		if (getHerosSante() != SANTE.VIVANT){
			throw new PostConditionError("La Sante du héros est différent de Vivant apres l'init");
		}
		if (getHerosForceVitale() != 3){
			throw new PostConditionError("La force vitale du héros est différent de trois après l'initialisation");
		}
		if (getTerrain().getNombreColonnes() != 15){
			throw new PostConditionError("Le nombre de colonnes du terrain apres l'init n'est pas égal à 15");
		}
		if (getTerrain().getNombreLignes() != 13){
			throw new PostConditionError("Le nombre de lignes du terrain apres l'init n'est pas égal à 13");
		}
		if (getBombeNumeros() != null){
			throw new PostConditionError("Liste de Bombes non après l'init");
		}

	}

}
