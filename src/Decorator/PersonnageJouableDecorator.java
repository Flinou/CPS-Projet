package Decorator;

import Services.PersonnageJouableService;
import Services.PersonnageType;
import Services.PowerUpType;

public class PersonnageJouableDecorator extends PersonnageDecorator implements PersonnageJouableService {
	
	public PersonnageJouableDecorator(PersonnageJouableService delegate) {
		super(delegate);
	}
	
	protected PersonnageJouableService delegate;

	@Override
	public void init(int i, int j, PersonnageType v) {
		delegate.init(i,j,v);
	}

	@Override
	public int getForceVitale() {
		return delegate.getForceVitale();
	}

	@Override
	public void setForceVitale(int f) {
		delegate.setForceVitale(f);
	}

	@Override
	public PersonnageType getType() {
		return delegate.getType();
	}

	@Override
	public void setBombe(int num) {
		delegate.setBombe(num);
	}

	@Override
	public void addBombe() {
		delegate.addBombe();
	}

	@Override
	public int getNbBombes() {
		return delegate.getNbBombes();
	}

	@Override
	public PowerUpType getPowerUp() {
		return delegate.getPowerUp();
	}


	@Override
	public int getCompteurFireSuit() {
		return delegate.getCompteurFireSuit();
	}

	@Override
	public void setCompteurFireSuit(int c) {
		delegate.setCompteurFireSuit(c);
	}

	@Override
	public void setPowerUp(PowerUpType pouvoir) {
		delegate.setPowerUp(pouvoir);
		
	}


}
