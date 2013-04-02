package Contracts;

import Decorator.PersonnageDecorator;
import Error.ContractError;
import Error.PostConditionError;
import Services.Commande;
import Services.PersonnageService;
import Services.Sante;

public class PersonnageContract extends PersonnageDecorator {

	public PersonnageContract(PersonnageService delegate) {
		super(delegate);
	}

	public void checkInvariants() {
		if(!(super.getX()>=0))
			throw new ContractError("getX(P) >= 0");
		if(!(super.getY()>=0))
			throw new ContractError("getY(P) >= 0");
	}
	
	@Override
	public void init(int x, int y) {
		super.init(x, y);
		if(!(super.getSante()==Sante.VIVANT))
			throw new PostConditionError("getSante(init(x,y)) == Sante::VIVANT");
	}

	@Override
	public Sante getSante() {
	}

	@Override
	public void setSante(Sante s) {
	}

	@Override
	public Commande getCommande() {
	}

	@Override
	public int getX() {
	}

	@Override
	public int getY() {
	}

	@Override
	public void setX(int x) {
	}

	@Override
	public void setY(int y) {
	}
	
}
