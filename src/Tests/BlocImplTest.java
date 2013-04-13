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
		System.out.println("Test avec l'init sans argument et tous les états possible");
		bloc1.init();
		assertTrue("type initial Fail",bloc1.getType() == BlocType.VIDE);
		assertTrue("power-up inital Fail",bloc1.getPowerUpType() == PowerUpType.RIEN);
		for(BlocType b : BlocType.values()) {
			bloc1.setType(b);
			assertTrue("changement de type Fail",bloc1.getType() == b);
			for(PowerUpType p : PowerUpType.values()) {
				bloc1.setPowerUpType(p);
				assertTrue("changement de power-up Fail",bloc1.getPowerUpType() == p);
			}
		}		
	}
	
	@Test
	public void test2() {
		System.out.println("Test avec l'init avec 1 argument et tous les états possibles");
		for(BlocType b : BlocType.values()) {
			bloc2.init(b);
			assertTrue("type initial Fail",bloc2.getType()==b);
			assertTrue("power-up initial Fail",bloc2.getPowerUpType() == PowerUpType.RIEN);
		}
		for(BlocType b : BlocType.values()) {
			bloc2.setType(b);
			assertTrue("changement de type Fail",bloc2.getType() == b);
			for(PowerUpType p : PowerUpType.values()) {
				bloc2.setPowerUpType(p);
				assertTrue("changement de power-up Fail",bloc2.getPowerUpType() == p);
			}
		}
	}

	@Test
	public void test3() {
		System.out.println("Test avec l'init avec 2 argument et tous les états possibles");
		for(BlocType b : BlocType.values()) {
			for(PowerUpType p : PowerUpType.values()) {
				bloc3.init(b,p);
				assertTrue("type initial Fail",bloc3.getType()==b);
				assertTrue("power-up initial Fail",bloc3.getPowerUpType() == p);
			}
		}
		for(BlocType b : BlocType.values()) {
			bloc3.setType(b);
			assertTrue("changement de type Fail",bloc3.getType() == b);
			for(PowerUpType p : PowerUpType.values()) {
				bloc3.setPowerUpType(p);
				assertTrue("changement de power-up Fail",bloc3.getPowerUpType() == p);
			}
		}
	}
	
}
