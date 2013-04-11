package Implementation;

import Services.BlocService;
import Services.BlocType;
import Services.PowerUpType;

public class BlocImpl implements BlocService {

	private BlocType type;
	private PowerUpType powerup;
	
	@Override
	public void init() {
		this.type = BlocType.VIDE;
		this.powerup = PowerUpType.RIEN;
	}

	@Override
	public void init(BlocType t) {
		this.type = t;
		this.powerup = PowerUpType.RIEN;
	}

	@Override
	public void init(BlocType t, PowerUpType p) {
		this.type = t;
		this.powerup = p;
	}

	@Override
	public BlocType getType() {
		return this.type;
	}

	@Override
	public PowerUpType getPowerUpType() {
		return this.powerup;
	}

	@Override
	public void setType(BlocType b) {
		this.type = b;
	}

	@Override
	public void setPowerUpType(PowerUpType p) {
		this.powerup = p;
	}

}
