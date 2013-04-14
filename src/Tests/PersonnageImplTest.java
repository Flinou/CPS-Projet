package Tests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import Implementation.PersonnageImpl;
import Services.PersonnageService;
import Services.Sante;

public class PersonnageImplTest {

	@Test
	public void test() {
		PersonnageService perso = new PersonnageImpl();
		Random rand = new Random();
		System.out.println("Tests de pré-condition non atteignable");
		perso.init(0, 0);
		System.out.println("Tests de couverture des postconditions de init");
		assertTrue("Le personnage est mort après init", perso.getSante() == Sante.VIVANT);
		assertTrue("La position du personnage est mauvaise", perso.getX()==0 && perso.getY() == 0);
		System.out.println("Tests de couverture des post-conditions des operators");
		int x = rand.nextInt(), y = rand.nextInt();
		perso.setX(x);
		assertTrue("Post-condition de setX non respéctées", perso.getX()==x && perso.getY()==0);
		perso.setY(y);
		assertTrue("Post-condition de setY non respéctées", perso.getX()==x && perso.getY()==y);
		perso.setSante(Sante.MORT);
		assertTrue("Post-condition de setSante non respectées",perso.getSante()==Sante.MORT);
	}

}
