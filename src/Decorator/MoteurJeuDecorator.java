package Decorator;

import java.util.ArrayList;
import java.util.HashMap;

import Services.BombeService;
import Services.Commande;
import Services.MoteurJeuService;
import Services.PersonnageJouableService;
import Services.Resultat;
import Services.Sante;
import Services.TerrainService;
import Services.VilainService;

public class MoteurJeuDecorator implements MoteurJeuService {
	
	public MoteurJeuDecorator(MoteurJeuService delegate){
		this.delegates = delegate;
	}
	protected MoteurJeuService delegates;
	@Override
	public int getPasJeuCourant() {
		return delegates.getPasJeuCourant();
	}

	@Override
	public int getMaxPasJeu() {
		return delegates.getMaxPasJeu();
	}

	@Override
	public void init(int maxPasJeu) {
		delegates.init(maxPasJeu);
	}


	@Override
	public void pasJeu() {
		delegates.pasJeu();
	}

	@Override
	public TerrainService getTerrain() {
		return delegates.getTerrain();
	}

	@Override
	public Sante getHerosSante() {
		return delegates.getHerosSante();
	}

	@Override
	public Sante getKidnappeurSante() {
		return delegates.getKidnappeurSante();
	}

	@Override
	public boolean bombeExiste(int num) {
		return delegates.bombeExiste(num);
	}

	@Override
	public int getNbBombes() {
		return delegates.getNbBombes();
	}

	@Override
	public BombeService getBombe(int num) {
		return delegates.getBombe(num);
	}

	@Override
	public ArrayList<Integer> getBombeNumeros() {
		return delegates.getBombeNumeros();
	}

	@Override
	public boolean estFini() {
		return delegates.estFini();
	}

	@Override
	public Resultat resultatFinal() {
		return delegates.resultatFinal();
	}

	@Override
	public boolean misEnJoue(int x, int y, int num) {
		return delegates.misEnJoue(x, y, num);
	}

	@Override
	public ArrayList<BombeService> getBombes() {
		return delegates.getBombes();
	}

	@Override
	public HashMap<Integer[], BombeService> getHashBombes() {
		return delegates.getHashBombes();
	}

	@Override
	public ArrayList<PersonnageJouableService> getListeJoueurs() {
		return delegates.getListeJoueurs();
		}

	@Override
	public PersonnageJouableService getHeros() {
		return delegates.getHeros();
	}

	@Override
	public PersonnageJouableService getKidnappeur() {
		return delegates.getKidnappeur();
	}

	@Override
	public ArrayList<VilainService> getVilains() {
		return delegates.getVilains();
	}

}
