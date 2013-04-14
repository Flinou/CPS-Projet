package Implementation;


import Services.PersonnageJouableService;
import Services.PersonnageType;
import Services.PowerUpType;


public class PersonnageJouableImpl extends PersonnageImpl implements PersonnageJouableService {

	@Override
	public void init(int i, int j, PersonnageType v) {
		super.init(i, j);
		this.type = v;
		this.forcevitale = 3;
		this.nbbomb = 1;
	}

	private int nbbomb;
	private int forcevitale;
	private PersonnageType type;
	private int firesuit;
	private PowerUpType powerup;
	
	@Override
	public int getForceVitale() {
		return forcevitale;
	}

	@Override
	public void setForceVitale(int f) {
		this.forcevitale = f;
	}

	@Override
	public PersonnageType getType() {
		return type;
	}

	@Override
	public void setBombe(int num) {
		nbbomb = num;
	}

	@Override
	public void addBombe() {
		nbbomb++;
	}

	@Override
	public int getNbBombes() {
		return nbbomb;
	}

	@Override
	public PowerUpType getPowerUp() {
		return powerup;
	}

	@Override
	public int getCompteurFireSuit() {
		return firesuit;
	}

	@Override
	public void setCompteurFireSuit(int c) {
		firesuit = c;
	}

	@Override
	public void setPowerUp(PowerUpType pouvoir) {
		powerup = pouvoir;
	}

}
