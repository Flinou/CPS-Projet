package Decorator;

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHerosY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getKidnappeurX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getKidnappeurY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void pasJeu(Commande com) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TerrainService getTerrain() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sante getHerosSante() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sante getKidnappeurSante() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHerosForceVitale() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getKidnappeurForceVitale() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean bombeExiste(int num) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getNbBombes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BombeService getBombe(int num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getBombeNumeros() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean estFini() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Resultat resultatFinal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean misEnJoue(int x, int y, int num) {
		// TODO Auto-generated method stub
		return false;
	}

}
