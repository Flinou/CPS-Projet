package Decorator;

import Services.BlocService;
import Services.BlocType;
import Services.PowerUpType;

public class BlocDecorator implements BlocService {

	public BlocDecorator(BlocService delegate) {
		this.delegate = delegate;
	}
	
	private final BlocService delegate;
	
	@Override
	public void init() {
		delegate.init();
	}

	public void init(BlocType t){
		delegate.init(t);
	}
	
	public void init(BlocType t, PowerUpType p) {
		delegate.init(t,p);
	}
	
	@Override
	public BlocType getType() {
		return delegate.getType();
	}

	@Override
	public PowerUpType getPowerUpType() {
		return delegate.getPowerUpType();
	}

	@Override
	public void setType(BlocType b) {
		delegate.setType(b);
	}

	@Override
	public void setPowerUpType(PowerUpType p) {
		delegate.setPowerUpType(p);
	}

}
