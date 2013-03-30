package Contracts;

/**
 * 
 * Contrat du moteur de jeu
 * 
 * @author Antoine FLINOIS
 *
 */ 
import Decorator.MoteurJeuDecorator;
import Services.BombeService;
import Services.Resultat;
import Services.Sante;
import Error.*;

public class MoteurJeuContract extends MoteurJeuDecorator {
	
	public void checkInvariant(){
		int num = 5;
		int x = this.getHerosX();
		int y = this.getHerosY();
		if (getPasJeuCourant() < 1 || getPasJeuCourant()>getMaxPasJeu()){
			throw new InvariantError ("Le pas de jeu courant n'est pas compris entre 1 et le maximum possible.");
		}
		if (getHerosX() < 1 || getHerosX() > getTerrain().getNombreColonnes()){
			throw new InvariantError ("L'abscisse du heros est hors du plateau de jeu");
		}
		if (getHerosY() < 1 || getHerosY() > getTerrain().getNombreLignes()){
			throw new InvariantError ("L'ordonnee du heros est hors du plateau de jeu");
		}
		if (getHerosForceVitale() < 3 || getHerosForceVitale() > 11){
			throw new InvariantError ("Force Vitale non valide");
		}
		if (getPasJeuCourant() < 1 || getPasJeuCourant()>getMaxPasJeu()){
			throw new InvariantError ("Le pas de jeu courant n'est pas compris entre 1 et le maximum possible.");
		}
		
		if (getBombeNumeros().size() != getNbBombes()){
			throw new InvariantError("La taille de la liste des bombes n'est pas egale au nombre de bombes");
		}
		//getNbBombes > A faire !
		
		if (bombeExiste(num)){
			int cpt = -1;
			for (int i=0;i<getNbBombes();i++){
				if (num == getBombeNumeros().get(i)){
					break;
				} else if (cpt == getNbBombes()){
					throw new InvariantError("La Bombe existe mais num n'est pas dans la liste");
				} 
			}
		}
		if (estFini() && (getHerosSante() != Sante.MORT) && (getPasJeuCourant() < getMaxPasJeu())){
			throw new InvariantError("Fin du jeu imprevue");
		}
		if (resultatFinal() == Resultat.KIDNAPPEURGAGNE && getHerosSante() != Sante.MORT){
			throw new InvariantError("Resultat final pour la partie gagnee par le kidnappeur imprevue");
		}
		if (resultatFinal() == Resultat.PARTIENULLE && getHerosSante() != Sante.VIVANT){
			throw new InvariantError("Resultat final pour la partie nulle imprevue");
		}
		if (misEnJoue(x,y,num)){
			int xB = getBombe(num).getX();
			int yB = getBombe(num).getY();
			int aB = getBombe(num).getAmplitude();
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
		if (getMaxPasJeu()!=maxPasJeu){
			throw new PostConditionError("Le nombre max de Pas de Jeu n'est pas egale a l'argument du init");
		}
		if (getPasJeuCourant() != 0){
			throw new PostConditionError("Le pas de jeu courant apres init n'est pas egal a 0");
		}
		if (getHerosX() != 2){
			throw new PostConditionError("L'abscisse du heros apres l'initialisation n'est pas egal a deux");
		}
		if (getHerosY() != 2){
			throw new PostConditionError("L'ordonnee du heros apres l'initialisation n'est pas egal a deux");
		}
		if (getHerosSante() != Sante.VIVANT){
			throw new PostConditionError("La Sante du heros est different de Vivant apres l'init");
		}
		if (getHerosForceVitale() != 3){
			throw new PostConditionError("La force vitale du heros est different de trois après l'initialisation");
		}
		if (getTerrain().getNombreColonnes() != 15){
			throw new PostConditionError("Le nombre de colonnes du terrain apres l'init n'est pas egal a 15");
		}
		if (getTerrain().getNombreLignes() != 13){
			throw new PostConditionError("Le nombre de lignes du terrain apres l'init n'est pas egal a 13");
		}
		if (getBombeNumeros() != null){
			throw new PostConditionError("Liste de Bombes non après l'init");
		}
		checkInvariant();

	}
	public BombeService getBombe(int num){
		// pre : bombeExiste(num)
		if (!bombeExiste(num)){
			throw new PreConditionError("La bombe n'existe pas");
		}
		return super.getBombe(num);
	}
	
	public Resultat resultatFinal(){
		//pre : estFini()
		if (!estFini()){
			throw new PreConditionError("La partie n'est pas finie");
		}
		return super.resultatFinal();
	}
	
	public boolean misEnJoue(int x,int y, int num){
		//pre :bombeExiste(num)
		if (!bombeExiste(num)){
			throw new PreConditionError("La bombe n'existe pas");
		}
		return super.misEnJoue(x,y,num);
	}
	
	

}
