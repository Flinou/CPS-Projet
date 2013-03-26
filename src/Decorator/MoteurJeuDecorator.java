package Decorator;

import Services.MoteurJeuService;

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
	public void pasJeu() {
		delegates.pasJeu();
	}

}
