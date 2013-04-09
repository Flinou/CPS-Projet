package Decorator;

import java.util.ArrayList;

import Services.BombeService;
import Services.Commande;
import Services.MoteurJeuService;
import Services.Resultat;
import Services.Sante;
import Services.TerrainService;

public class MoteurJeuDecorator implements MoteurJeuService {
	private MoteurJeuService delegates;
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
	public int getHerosX() {
		return delegates.getHerosX();
	}

	@Override
	public int getHerosY() {
		return delegates.getHerosY();
	}

	@Override
	public int getKidnappeurX() {
		return delegates.getKidnappeurX();
	}

	@Override
	public int getKidnappeurY() {
			return delegates.getKidnappeurY();
	}

	@Override
	public void pasJeu(Commande com) {
		delegates.pasJeu(com);
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
	public int getHerosForceVitale() {
		return delegates.getHerosForceVitale();
	}

	@Override
	public int getKidnappeurForceVitale() {
		return delegates.getKidnappeurForceVitale();
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

}
