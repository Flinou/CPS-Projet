package Contracts;

import Decorator.PersonnageJouableDecorator;
import Services.PersonnageJouableService;

public class PersonnageJouableContract extends PersonnageJouableDecorator {

	public PersonnageJouableContract(PersonnageJouableService delegate) {
		super(delegate);
		contratPere = new PersonnageContract(delegate);
	}
	
	private PersonnageContract contratPere;

}
