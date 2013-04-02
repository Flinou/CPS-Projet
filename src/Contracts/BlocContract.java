package Contracts;

import Decorator.BlocDecorator;
import Error.PostConditionError;
import Services.BlocService;
import Services.BlocType;
import Services.PowerUpType;

public class BlocContract extends BlocDecorator {

	public BlocContract(BlocService delegate) {
		super(delegate);
	}

	public void init() {
		super.init();
		if(!(super.getType() == BlocType.VIDE))
			throw new PostConditionError("getType(init())!=BlocType::VIDE");
		if(!(super.getPowerUpType() == PowerUpType.RIEN))
			throw new PostConditionError("getPowerUpType(init())!=PowerUpType::RIEN");
	}
	
	
	public void init(BlocType t) {
		super.init(t);
		if(!(super.getType() == t))
			throw new PostConditionError("getType(init(t))!=t");
		if(!(super.getPowerUpType() == PowerUpType.RIEN))
			throw new PostConditionError("getPowerUpType(init(t))!=PowerUpType::RIEN");
	}
	
	public void init(BlocType t, PowerUpType p) {
		super.init(t,p);
		if(!(super.getType() == t))
			throw new PostConditionError("getType(init(t,p))!=t");
		if(!(super.getPowerUpType() == PowerUpType.RIEN))
			throw new PostConditionError("getPowerUpType(init(t,p))!=p");
	}
	
	public void checkInvariants() {}

	public BlocType getType() {
		BlocType t;
		checkInvariants();
		t = super.getType();
		checkInvariants();
		return t;
	}

	public PowerUpType getPowerUpType() {
		PowerUpType p;
		checkInvariants();
		p = super.getPowerUpType();
		checkInvariants();
		return p;
	}
	
	public void setType(BlocType b) {
		BlocType t = super.getType();
		checkInvariants();
		super.setType(b);
		checkInvariants();
		if(t==BlocType.MURMETAL){
			if(!(super.getType() == BlocType.MURMETAL)){
				throw new PostConditionError(
						"getType(B) == BlocType::MURMETAL et "+
								"getType(setType(B,t)) != BlocType :: MURMETAL");}
		} else {
			if(!(super.getType()==b))
				throw new PostConditionError("getType(setType(B,t)) == t");
		}
			
		
	}
	
	public void setPowerUpType(PowerUpType p) {
		checkInvariants();
		super.setPowerUpType(p);
		checkInvariants();
		if(!(super.getPowerUpType() == p))
			throw new PostConditionError("getPowerUpType(setPowerUpType(B,p)) == p");
	}
}
