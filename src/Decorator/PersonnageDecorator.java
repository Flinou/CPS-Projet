package Decorator;

import Services.Commande;
import Services.PersonnageService;
import Services.Sante;

public class PersonnageDecorator implements PersonnageService {

	public PersonnageDecorator(PersonnageService delegate) {
		this.delegate = delegate;
	}

	protected PersonnageService delegate;
	
	@Override
	public void init(int x, int y) {
		delegate.init(x,y);
	}

	@Override
	public Sante getSante() {
		return delegate.getSante();
	}

	@Override
	public void setSante(Sante s) {
		delegate.setSante(s);
	}

	@Override
	public Commande getCommande() {
		return delegate.getCommande();
	}

	@Override
	public int getX() {
		return delegate.getX();
	}

	@Override
	public int getY() {
		return delegate.getY();
	}

	@Override
	public void setX(int x) {
		delegate.setX(x);
	}

	@Override
	public void setY(int y) {
		delegate.setY(y);
	}

}
