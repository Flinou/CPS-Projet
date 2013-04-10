package Decorator;

import Services.VilainService;
import Services.VilainType;

public class VilainDecorator extends PersonnageDecorator implements VilainService {

	public VilainDecorator(VilainService delegate) {
		super(delegate);
	}
	protected VilainService delegate;
	@Override
	public void init(int i, int j, VilainType v) {
		delegate.init(i,j,v);
	}
	@Override
	public VilainType getType() {
		return delegate.getType();
	}

}
