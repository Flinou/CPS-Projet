package Contracts;

import Decorator.TerrainDecorator;
import Error.ContractError;
import Error.PostConditionError;
import Error.PreConditionError;
import Services.BlocService;
import Services.TerrainService;
import Services.BlocType;

public class TerrainContract extends TerrainDecorator {

	public TerrainContract(TerrainService delegate) {
		super(delegate);
	}
	
	public void checkInvariants() {
		if(!(super.getNombreLignes()>0))
			throw new ContractError("getNombresLignes() <= 0");
		if(!(super.getNombreColonnes()>0))
			throw new ContractError("getNombresColonnes() <= 0");
		int l = super.getNombreColonnes();
		int h = super.getNombreLignes();
		for(int i=0; i<l; i++){
			for(int j=0; j<h; j++){
				if(!(super.getBloc(i, h).getType() == BlocType.MURMETAL ||
					 super.getBloc(l, j).getType() == BlocType.MURMETAL))
					throw new PostConditionError(
							"Bloc::getType(getBloc(init(l,h),i,h))!=Bloc::MURMETAL, "+
							"Bloc::getType(getBloc(init(l,h),l,j))!=Bloc::MURMETAL");
				if(i%2==1 && j%2==1){
					if(!(super.getBloc(i, j).getType() == BlocType.MURMETAL))
						throw new PostConditionError("i=j=1[2] et "+
					"Bloc::getType(getBloc(init(l,h),i,j))!=Bloc::MURMETAL");
				} else {
					if(!(super.getBloc(i, j).getType() != BlocType.MURMETAL))
						throw new PostConditionError("i!=1[2] et j!=1[2] et "+
								"Bloc::getType(getBloc(init(l,h),i,j))==Bloc::MURMETAL");
				}
			}
		}
	}
	
	@Override
	public void init(int l, int h) {
		if(!(l%2 == 1 && h%2 == 1))
			throw new PreConditionError("l and h must be odd");
		if(!(l>0 && h>0))
			throw new PreConditionError("l and h must be positive");
		super.init(l,h);
		if(!(super.getNombreColonnes()==l))
			throw new PostConditionError("getNombreColonnes(init(l,h)) != l");
		if(!(super.getNombreLignes()==l))
			throw new PostConditionError("getNombreLignes(init(l,h)) != l");
	}
	
	@Override
	public int getNombreColonnes() {
		int r;
		checkInvariants();
		r = super.getNombreColonnes();
		checkInvariants();
		return r;
	}

	@Override
	public int getNombreLignes() {
		int r;
		checkInvariants();
		r = super.getNombreLignes();
		checkInvariants();
		return r;
	}
	
	@Override
	public BlocService getBloc(int i, int j){
		BlocService b;
		if(!(0<=i && i<super.getNombreColonnes() && 0<=j && j<super.getNombreLignes()))
			throw new PreConditionError("unboud i or j");
		checkInvariants();
		b = super.getBloc(i, j);
		checkInvariants();
		return b;
	}
	
	@Override
	public void setBloc(BlocService b, int i, int j) {
		if(!(0<=i && i<super.getNombreColonnes() && 0<=j && j<super.getNombreLignes()))
			throw new PreConditionError("unboud i or j");
		checkInvariants();
		super.setBloc(b, i, j);
		checkInvariants();
		if(!(super.getBloc(i, j)==b))
			throw new PostConditionError("getBloc(setBloc(T,b,i,j),i,j)!=b");
	}
	
	public TerrainService clone() {return null;}
}
