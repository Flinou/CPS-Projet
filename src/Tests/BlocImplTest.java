package Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Implementation.BlocImpl;
import Services.BlocService;
import Services.BlocType;
import Services.PowerUpType;

public class BlocImplTest {

	@Before
	public void setUp() throws Exception {
		bloc1 = new BlocImpl();
		bloc2 = new BlocImpl();
		bloc3 = new BlocImpl();
	}

	private BlocService bloc1 , bloc2, bloc3;
	
	@Test
	public void test1() {
		System.out.println("Test avec l'init sans argument : ");
		bloc1.init();
		assertTrue("Le bloc est vide",bloc1.getType() == BlocType.VIDE);
		assertTrue("Le bloc n'a pas de power-up",bloc1.getPowerUpType() == PowerUpType.RIEN);
		bloc1.setType(BlocType.MURMETAL);
		assertTrue("Le bloc est un MURMETAL",bloc1.getType() == BlocType.MURMETAL);
		bloc1.setPowerUpType(PowerUpType.BOMBUP);
		assertTrue("Le bloc a un power-up BOMBUP",bloc1.getPowerUpType() == PowerUpType.BOMBUP);		
	}
	
	@Test
	public void test2() {
		System.out.println("Test avec l'init avec 1 argument : ");
		bloc2.init(BlocType.MURBRIQUE);
		assertTrue("Le bloc est un mur en brique",bloc2.getType() == BlocType.MURBRIQUE);
		assertTrue("Le bloc n'a pas de power-up",bloc2.getPowerUpType() == PowerUpType.RIEN);
		bloc2.setType(BlocType.MURMETAL);
		assertTrue("Le bloc est un MURMETAL",bloc2.getType() == BlocType.MURMETAL);
		bloc2.setPowerUpType(PowerUpType.BOMBUP);
		assertTrue("Le bloc a un power-up BOMBUP",bloc2.getPowerUpType() == PowerUpType.BOMBUP);		
	}

	@Test
	public void test3() {
		System.out.println("Test avec l'init avec 2 argument : ");
		bloc3.init(BlocType.MURMETAL,PowerUpType.WALLPASS);
		assertTrue("Le bloc est un mur en metal",bloc3.getType() == BlocType.MURMETAL);
		assertTrue("Le bloc a un power-up WALLPASS",bloc3.getPowerUpType() == PowerUpType.WALLPASS);
		bloc3.setType(BlocType.VIDE);
		assertTrue("Le bloc est vide",bloc3.getType() == BlocType.VIDE);
		bloc3.setPowerUpType(PowerUpType.BOMBUP);
		assertTrue("Le bloc a un power-up BOMBUP",bloc3.getPowerUpType() == PowerUpType.BOMBUP);		
	}
	
}
