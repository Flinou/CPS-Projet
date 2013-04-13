package Implementation;

import java.util.Random;


import Services.Commande;
import Services.PersonnageService;
import Services.Sante;

public class PersonnageImpl implements PersonnageService {

	@Override
	public void init(int x, int y) {
		this.x=x;
		this.y=y;
		this.sante = Sante.VIVANT;
		this.mouv = new Random();
	}
	
	private int x,y;
	private Sante sante;
	private Random mouv;
	

	@Override
	public Sante getSante() {
		return sante;
	}

	@Override
	public void setSante(Sante s) {
		sante = s;
	}

	@Override
	public Commande getCommande() {
		int t = mouv.nextInt(Commande.values().length);
		return Commande.values()[t];
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public void setCommande() {
		 
	}

}
