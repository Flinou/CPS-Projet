package Implementation;

import Services.BlocService;

import Services.TerrainService;

public class TerrainImpl implements TerrainService, Cloneable{

	@Override
	public void init(int l, int h) {
		this.grille = new BlocService[l][h];
		this.l=l;
		this.h=h;
	}
	
	private int l,h;
	private BlocService[][] grille;

	@Override
	public int getNombreColonnes() {
		return l;
	}

	@Override
	public int getNombreLignes() {
		return h;
	}

	@Override
	public BlocService getBloc(int i, int j) {
		return grille[i][j];
	}

	@Override
	public void setBloc(BlocService b, int i, int j) {
		grille[i][j] = b;
	}
	
	public TerrainImpl clone(){
		TerrainImpl ter = new TerrainImpl();
		ter.init(getNombreLignes(), getNombreColonnes());
		for(int i=0;i<getNombreLignes();i++)
			for(int j=0;j<getNombreColonnes();j++)
				ter.setBloc(getBloc(j, i), j, i);
		return ter;
	}

}
