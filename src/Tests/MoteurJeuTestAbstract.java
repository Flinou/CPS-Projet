package Tests;

import Services.BlocType;
import Services.MoteurJeuService;
import Services.PersonnageJouableService;
import Services.Sante;
import Services.VilainService;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class MoteurJeuTestAbstract {
	
	private MoteurJeuService moteur;
	private MoteurJeuService clone;
	public void setMoteurJeu(MoteurJeuService moteurJeu){
		this.moteur = moteurJeu;
	}
	public void checkPasJeu(){
		moteur.init(10);
		assertTrue("Le pas de Jeu n'est pas égal au pas de jeu donné en argument",moteur.getMaxPasJeu() == 10);
		assertTrue("Le pas de jeu courant n'est pas égal à 0 après l'init",moteur.getPasJeuCourant() == 0);
		assertTrue("le nombre de colonnes après l'init n'est pas égale à 15", moteur.getTerrain().getNombreColonnes() == 15);
		assertTrue("le nombre de lignes après l'init n'est pas égale à 13", moteur.getTerrain().getNombreColonnes() == 13);
		assertTrue("Il y a une bombe dans la liste de bombes après l'init", moteur.getBombes()==null);
		assertTrue("Bloc d'apparition des vilains non vides", moteur.getTerrain().getBloc(6,6).getType() != BlocType.VIDE);
		assertTrue("Personnage heros non apparue en 2,2", moteur.getHeros().getX() == 2);
		assertTrue("Personnage heros non apparue en 2,2", moteur.getHeros().getY() == 2);
		assertTrue("Personnage kidnappeur non apparue en 10,10", moteur.getHeros().getX() == 10);
		assertTrue("Personnage kidnappeur non apparue en 10,10", moteur.getHeros().getY() == 10);
		for (PersonnageJouableService perso : moteur.getListeJoueurs()){
			assertTrue("Sante du personnage pas égal à vivant",perso.getSante() == Sante.VIVANT);
			assertTrue("Force vitale du personnage pas égal à 3",perso.getForceVitale() == 3);
		}
		for (VilainService vil : moteur.getVilains()){
			assertTrue("Vilain pas crée en 6,6",vil.getX()==6);
			assertTrue("Vilain pas crée en 6,6",vil.getX()==6);
		}
		
		for(PersonnageJouableService perso : moteur.getListeJoueurs()){
			perso.setCommande();
		}
		for (VilainService vil : moteur.getVilains()){
			vil.setCommande();
		}
		clone = moteur.clone(); 
		moteur.pasJeu();
		
		
	}

}
