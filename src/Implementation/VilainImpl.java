package Implementation;


import Services.Commande;
import Services.VilainService;
import Services.VilainType;

public class VilainImpl extends PersonnageImpl implements VilainService {

	@Override
	public void init(int i, int j, VilainType v) {
		super.init(i, j);
		this.type = v;		
	}
	
	private VilainType type;
	
	@Override
	public VilainType getType() {
		return type;
	}
	
	@Override
	public Commande getCommande() {
		Commande t = super.getCommande();
		if(t == Commande.BOMBE)
			return getCommande();
		else
			return t;
	}

}
