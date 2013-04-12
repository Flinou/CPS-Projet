package Implementation;

import Services.BlocService;

import Services.TerrainService;

public class Terrain implements TerrainService, Cloneable{

	@Override
	public void init(int l, int h) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getNombreColonnes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNombreLignes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BlocService getBloc(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBloc(BlocService b, int i, int j) {
		// TODO Auto-generated method stub

	}
	public Terrain clone(){
		Terrain ter = null;
		try {
			ter = (Terrain) super.clone();
		} catch (CloneNotSupportedException cnse){
			cnse.printStackTrace(System.err);
		}
		return ter;
	}

}
