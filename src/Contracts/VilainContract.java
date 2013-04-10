package Contracts;

import Decorator.VilainDecorator;
import Services.VilainService;

public class VilainContract extends VilainDecorator {

	public VilainContract(VilainService delegate) {
		super(delegate);
	}

}
