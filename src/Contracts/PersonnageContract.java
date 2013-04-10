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
		Sante t;
		checkInvariants();
		t=super.getSante();
		checkInvariants();
		return t;
	}

	@Override
	public void setSante(Sante s) {
		checkInvariants();
		super.setSante(s);
		if(!(super.getSante()==s))
			throw new PostConditionError("getSante(setSante(P,s)) == s");
		checkInvariants();
	}

	@Override
	public Commande getCommande() {
		Commande t;
		checkInvariants();
		t=super.getCommande();
		checkInvariants();
		return t;
	}

	@Override
	public int getX() {
		int t;
		checkInvariants();
		t=super.getX();
		checkInvariants();
		return t;		
	}

	@Override
	public int getY() {
		int t;
		checkInvariants();
		t=super.getY();
		checkInvariants();
		return t;
	}

	@Override
	public void setX(int x) {
		int t = super.getY();
		checkInvariants();
		super.setX(x);
		if(!(super.getX()==x))
			throw new PostConditionError("getX(setX(T,x)) == x");
		if(!(super.getY()==t))
			throw new PostConditionError("getY(setX(T,x)) == getY(T)");
		checkInvariants();		
	}

	@Override
	public void setY(int y) {
		int t = super.getX();
		checkInvariants();
		super.setY(y);
		if(!(super.getY()==y))
			throw new PostConditionError("getY(setY(T,y)) == y");
		if(!(super.getX()==t))
			throw new PostConditionError("getX(setY(T,x)) == getX(T)");
		checkInvariants();	
	}
	
}
