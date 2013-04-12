package Contracts;

import Decorator.PersonnageJouableDecorator;
import Error.InvariantError;
import Error.PostConditionError;
import Error.PreConditionError;
import Services.PersonnageJouableService;
import Services.PersonnageType;
import Services.PowerUpType;
import Services.Sante;

public class PersonnageJouableContract extends PersonnageJouableDecorator {

	public PersonnageJouableContract(PersonnageJouableService delegate) {
		super(delegate);
		contratPere = new PersonnageContract(delegate);
	}
	
	private PersonnageContract contratPere;
	
	public void checkInvvariants() {
		contratPere.checkInvariants();
		if(!(super.getForceVitale() >= 3 && super.getForceVitale() <=11))
			throw new InvariantError("3 <= getForceVitale(P) <= 11");
		if(!(super.getNbBombes()>=0))
			throw new InvariantError("getNbBombes(P)>=0");
		if(!(super.getCompteurFireSuit()>=0))
			throw new InvariantError("getCompteurFireSuit(P)>=0");
	}
	
	public void init(int i, int j, PersonnageType v) {
		if(i<0 && j <0)
			throw new PreConditionError("i>=0 && j>=0");
		super.init(i, j, v);
		if(!(super.getType()==v))
			throw new PostConditionError("getType(init(i,j,v)) == v");
		if(!(super.getForceVitale() == 3))
			throw new PostConditionError("getForceVitale(init(i,j,v)) == 3");
		if(!(super.getX() == i))
			throw new PostConditionError("getX(init(i,j,v)) == i");
		if(!(super.getY() == j))
			throw new PostConditionError("getX(init(i,j,v)) == j");
		if(!(super.getSante() == Sante.VIVANT))
			throw new PostConditionError("getSante(init(i,j,v)) == Sante::VIVANT");
		if(!(super.getCompteurFireSuit() == 0))
			throw new PostConditionError("getCompteurFireSuit(init(i,j,v)) == 0");
		if(!(super.getNbBombes() == 1))
			throw new PostConditionError("getNbBombes(init(i,j,v)) == 1");
	}

	@Override
	public int getForceVitale() {
		int t;
		checkInvvariants();
		t=super.getForceVitale();
		checkInvvariants();
		return t;				
	}

	@Override
	public void setForceVitale(int f) {
		if(!(3<=f && f<=11))
			throw new PreConditionError("3<=f<=11");
		checkInvvariants();
		super.setForceVitale(f);
		checkInvvariants();
	}

	@Override
	public PersonnageType getType() {
		PersonnageType t;
		checkInvvariants();
		t=super.getType();
		checkInvvariants();
		return t;
	}

	@Override
	public void setBombe(int num) {
		if(!(num>=0))
			throw new PreConditionError("num >= 0");
		checkInvvariants();
		super.setBombe(num);
		if(!(super.getNbBombes()==num))
			throw new PostConditionError("getNbBombes(setBombe(P,num)) == num");
		checkInvvariants();
	}

	@Override
	public void addBombe() {
		int t = super.getNbBombes();
		checkInvvariants();
		super.addBombe();
		if(!(super.getNbBombes() == t+1))
			throw new PostConditionError("getNbBombes(addBombe(P)) == getNbBombes(P) + 1");
		checkInvvariants();
	}

	@Override
	public int getNbBombes() {
		int t;
		checkInvvariants();
		t=super.getNbBombes();
		checkInvvariants();
		return t;
	}

	@Override
	public PowerUpType getPowerUp() {
		PowerUpType t;
		checkInvvariants();
		t=super.getPowerUp();
		checkInvvariants();
		return t;
	}

	@Override
	public int getCompteurFireSuit() {
		int t;
		checkInvvariants();
		t=super.getCompteurFireSuit();
		checkInvvariants();
		return t;
	}

	@Override
	public void setCompteurFireSuit(int c) {
		if(!(c>=0))
			throw new PreConditionError("c>=0");
		checkInvvariants();
		super.setCompteurFireSuit(c);
		if(!(super.getCompteurFireSuit() == c))
			throw new PostConditionError("getCompteurFireSuit(setCompteurFireSuit(P,c)) == c ");
		checkInvvariants();
	}

}
