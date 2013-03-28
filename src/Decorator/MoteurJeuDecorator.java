package Decorator;

import Services.MoteurJeuService;

public class MoteurJeuDecorator implements MoteurJeuService {
	private MoteurJeuService delegates;
	
	public int getPasJeuCourant() {
		return delegates.getPasJeuCourant();
	}

	
	public int getMaxPasJeu() {
		return delegates.getMaxPasJeu();
	}

	
	public void init(int maxPasJeu) {
		delegates.init(maxPasJeu);
	}

	
	public void pasJeu() {
		delegates.pasJeu();
	}


	public int getHerosX() {
		// TODO Auto-generated method stub
		return 0;
	}

}
