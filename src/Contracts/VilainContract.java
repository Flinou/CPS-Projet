package Contracts;

import Decorator.VilainDecorator;
import Error.PostConditionError;
import Error.PreConditionError;
import Services.Commande;
import Services.Sante;
import Services.VilainService;
import Services.VilainType;

public class VilainContract extends VilainDecorator {

	public VilainContract(VilainService delegate) {
		super(delegate);
		contratPere = new PersonnageContract(delegate);
	}
	
	private PersonnageContract contratPere;
	
	public void checkInvariants() {
		contratPere.checkInvariants();
	}
	
	public void init(int i, int j, VilainType v) {
		if(!(i>=0 && j>=0))
			throw new PreConditionError("i>= && j>=0");
		super.init(i, j, v);
		if(!(super.getType()==v))
			throw new PostConditionError("getType(init(i,j,v)) == v");
		if(!(super.getX() == i))
			throw new PostConditionError("getX(init(i,j,v)) == i");
		if(!(super.getY() == j))
			throw new PostConditionError("getX(init(i,j,v)) == j");
		if(!(super.getSante() == Sante.VIVANT))
			throw new PostConditionError("getSante(init(i,j,v)) == Sante::VIVANT");
	}

	public VilainType getType() {
		VilainType t;
		checkInvariants();
		t=super.getType();
		checkInvariants();
		return t;
	}
	
	public Commande getCommande() {
		Commande t;
		checkInvariants();
		t=super.getCommande();
		if(t==Commande.BOMBE)
			throw new PostConditionError("getCommande(V) \\in Commande \\ { Commande::BOMBE }");
		checkInvariants();
		return t;
	}
}
