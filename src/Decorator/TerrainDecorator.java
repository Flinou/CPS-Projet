package Decorator;

import Services.BlocService;
import Services.TerrainService;

public class TerrainDecorator implements TerrainService {
	
	public TerrainDecorator(TerrainService delegate) {
		this.delegate = delegate;
	}

	private final TerrainService delegate;	
	
	@Override
	public void init(int l, int h) {
		delegate.init(l, h);
	}

	@Override
	public int getNombreColonnes() {
		return delegate.getNombreColonnes();
	}

	@Override
	public int getNombreLignes() {
		return delegate.getNombreLignes();
	}

	@Override
	public BlocService getBloc(int i, int j) {
		return delegate.getBloc(i, j);
	}

	@Override
	public void setBloc(BlocService b, int i, int j) {
		delegate.setBloc(b, i, j);
	}
	
	public TerrainService clone(){
		return delegate.clone();
	}

}
