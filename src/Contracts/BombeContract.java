package Contracts;

import Decorator.BombeDecorator;
import Services.BombeService;
import Error.*;

public class BombeContract extends BombeDecorator {

	public BombeContract(BombeService delegate) {
		super(delegate);
	}
	
	public void checkInvariants(){
		if (super.getCompteARebours() > 10 || super.getCompteARebours() <0){
			throw new InvariantError("Compte a rebours de la bombe non permis");
		}
		if(super.vaExploser()){
			if (super.getCompteARebours() != 0){
			throw new InvariantError("Vaexploser vrai mais compte a rebours != 0");
			}
		}
	}


	public void init(int num, int x,int y, int amplitude){
		if (amplitude < 3 || amplitude > 11){
			throw new PreConditionError("Amplitude non autorisée lors de la création de la bombe");
		}
		checkInvariants();
		super.init(num, x, y, amplitude);
		checkInvariants();
		if (super.getNumero() != num){
			throw new PostConditionError("Incohérence numéro de bombe lors de la création");
		}
		if (super.getX() != x){
			throw new PostConditionError("Incohérence abscisse bombe lors de la création");
		}
		if (super.getY() != y){
			throw new PostConditionError("Incohérence ordonnée de bombe lors de la création");
		}
		if (super.getAmplitude() != amplitude){
			throw new PostConditionError("Incohérence amplitude de bombe lors de la création");
		}
		if (super.getCompteARebours() != 10){
			throw new PostConditionError("Incohérence compte à rebours de bombe lors de la création");
		}
		
	}
	public void dimCompteARebours(){
		int rebours = super.getCompteARebours();
		if (rebours< 0){
			throw new PreConditionError("Impossible décréménter compte à rebours déjà à 1");
		}
		super.dimCompteARebours();
		if (super.getCompteARebours()  != rebours -1){
			throw new PostConditionError("Incohérence compte à rebours de bombe aprés dimCompteARebours");
		}
	}

}
