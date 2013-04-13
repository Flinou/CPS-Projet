package Decorator;

import Services.BombeService;

public class BombeDecorator implements BombeService {

	public BombeDecorator(BombeService delegate){
		this.delegates = delegate;
	}
	private BombeService delegates;
	@Override
	public int getNumero() {
		return delegates.getNumero();
	}

	@Override
	public int getX() {
		return delegates.getX();
	}

	@Override
	public int getY() {
		return delegates.getY();
	}

	@Override
	public int getAmplitude() {
		return delegates.getAmplitude();
	}

	@Override
	public int getCompteARebours() {
		return delegates.getCompteARebours();
	}

	@Override
	public boolean vaExploser() {
		return delegates.vaExploser();
	}

	@Override
	public void dimCompteARebours() {
		delegates.dimCompteARebours();
	}

	@Override
	public void init(int num, int x, int y, int amplitude) {
		delegates.init(num,x,y,amplitude);
		}

}
