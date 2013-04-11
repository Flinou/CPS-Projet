package Implementation;

import Services.BombeService;

public class Bombe implements BombeService {

	private int numero;
	private int abscisse;
	private int ordonnee;
	private int amplitude;
	private int rebours;
	
	@Override
	public int getNumero() {
		return numero;
	}

	@Override
	public int getX() {
		return abscisse;
	}

	@Override
	public int getY() {
		return ordonnee;
	}

	@Override
	public int getAmplitude() {
		return amplitude;
	}

	@Override
	public int getCompteARebours() {
		return rebours;
	}

	@Override
	public boolean vaExploser() {
		if (rebours ==0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void init(int num, int x, int y, int ampl) {
		numero = num;
		abscisse = x;
		ordonnee = y;
		amplitude = ampl;
		rebours = 10;
	}

	@Override
	public void dimCompteARebours() {
		rebours = rebours - 1;
	}

}
