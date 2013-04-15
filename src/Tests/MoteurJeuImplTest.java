package Tests;

import java.util.ArrayList;

import java.util.HashMap;

import Implementation.MoteurJeuImpl;
import Services.BlocType;
import Services.BombeService;
import Services.Commande;
import Services.MoteurJeuService;
import Services.PersonnageJouableService;
import Services.PersonnageType;
import Services.PowerUpType;
import Services.Sante;
import Services.VilainService;
import Services.VilainType;
import Implementation.PersonnageJouableImpl;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class MoteurJeuImplTest {
	
	private MoteurJeuImpl moteur;
	private MoteurJeuImpl clone;
	private PersonnageJouableImpl[] persos = new PersonnageJouableImpl [2];
	private ArrayList<BombeService> imminentes = new ArrayList();
	private ArrayList<BombeService> tranquilles = new ArrayList();;
	HashMap<VilainService, Integer[]> oldCoordsVilains = new HashMap<VilainService, Integer[]>();
	
	public void setMoteurJeu(MoteurJeuImpl moteurJeu){
		this.moteur = moteurJeu;
	}
	
	@Before
	public void setUp() {
		setMoteurJeu(new MoteurJeuImpl());
	}
	
	@Test
	public void checkPasJeu(){
		moteur.init(10);
		assertTrue("Le pas de Jeu n'est pas égal au pas de jeu donné en argument",moteur.getMaxPasJeu() == 10);
		assertTrue("Le pas de jeu courant n'est pas égal à 0 après l'init",moteur.getPasJeuCourant() == 0);
		assertTrue("le nombre de colonnes après l'init n'est pas égale à 15", moteur.getTerrain().getNombreColonnes() == 15);
		assertTrue("le nombre de lignes après l'init n'est pas égale à 13", moteur.getTerrain().getNombreLignes() == 13);
		assertTrue("Il y a une bombe dans la liste de bombes après l'init", moteur.getBombes().size()==0);
		assertTrue("Bloc d'apparition des vilains non vides", moteur.getTerrain().getBloc(6,6).getType() == BlocType.VIDE);
		assertTrue("Personnage heros non apparue en 2,2", moteur.getHeros().getX() == 2);
		assertTrue("Personnage heros non apparue en 2,2", moteur.getHeros().getY() == 2);
		assertTrue("Personnage kidnappeur non apparue en 10,10", moteur.getKidnappeur().getX() == 10);
		assertTrue("Personnage kidnappeur non apparue en 10,10", moteur.getKidnappeur().getY() == 10);
		for (PersonnageJouableService perso : moteur.getListeJoueurs()){
			assertTrue("Sante du personnage pas égal à vivant",perso.getSante() == Sante.VIVANT);
			assertTrue("Force vitale du personnage pas égal à 3",perso.getForceVitale() == 3);
		}
		for (VilainService vil : moteur.getVilains()){
			assertTrue("Vilain pas crée en 6,6",vil.getX()==6);
			assertTrue("Vilain pas crée en 6,6",vil.getX()==6);
		}
		
		
		for (int y = 0; y<moteur.getMaxPasJeu(); y ++){
		//Initialisation des listes de bombes
				for (BombeService bombe : moteur.getBombes()){
					if (bombe.vaExploser()){
						imminentes.add(bombe);
					}else{
						tranquilles.add(bombe);
					}
				}
				//Anciennes coordonnées des Vilains
				for(VilainService vil : moteur.getVilains()){
					Integer[] coor = {vil.getX(),vil.getY()};
					oldCoordsVilains.put(vil, coor);
				}


		clone = moteur.clone();
		PersonnageJouableImpl herosImpl = (PersonnageJouableImpl) moteur.getHeros();
		PersonnageJouableImpl kidnapImpl = (PersonnageJouableImpl) moteur.getKidnappeur();
		
		persos[0] = herosImpl.clone();
		persos[1] = kidnapImpl.clone();
		moteur.pasJeu();
		
		
		assertTrue("PasJeu non incrémenté", clone.getPasJeuCourant() == moteur.getPasJeuCourant() -1);
		for (PersonnageJouableService perso : moteur.getListeJoueurs()){
			Commande com;
			Commande cloneCommande;
			PersonnageJouableService persoclone;
			if (perso.getType() == PersonnageType.HEROS){
				persoclone = persos[0]; 
				com = moteur.getHerosCommande();
				cloneCommande = moteur.getKidnappeurCommande();
			}else{
				persoclone = persos[1];
				com = moteur.getKidnappeurCommande();
				cloneCommande = moteur.getHerosCommande();
			}
		
		assertTrue("Personnage sur une case non vide", moteur.getTerrain().getBloc(perso.getX(),perso.getY()).getType() == BlocType.VIDE);
		if (moteur.getTerrain().getBloc(persoclone.getX() + 1, persoclone.getY()).getType() != BlocType.VIDE && com == Commande.DROITE){
			assertTrue("Personnage a bougé alors que la case n'était pas vide", perso.getX() == persoclone.getX());
		}
		if (moteur.getTerrain().getBloc(persoclone.getX() - 1, persoclone.getY()).getType() != BlocType.VIDE && com == Commande.GAUCHE){
			assertTrue("Personnage a bougé alors que la case n'était pas vide", perso.getX() == persoclone.getX());
		}
		if (moteur.getTerrain().getBloc(persoclone.getX(), persoclone.getY() - 1).getType() != BlocType.VIDE && com == Commande.HAUT){
			assertTrue("Personnage a bougé alors que la case n'était pas vide", perso.getY() == persoclone.getY());
		}
		if (moteur.getTerrain().getBloc(persoclone.getX(), persoclone.getY() + 1).getType() != BlocType.VIDE && com == Commande.BAS){
			assertTrue("Personnage a bougé alors que la case n'était pas vide", perso.getY() == persoclone.getY());
		}
		if (com == Commande.DROITE && (moteur.getTerrain().getBloc(persoclone.getX() + 1, persoclone.getY()).getType() == BlocType.VIDE)){
			assertTrue("Personnage n'a pas bien bougé", perso.getX() == Math.min(moteur.getTerrain().getNombreColonnes(), persoclone.getX() + 1));
		}
		if (com == Commande.GAUCHE && (moteur.getTerrain().getBloc(persoclone.getX() - 1, persoclone.getY()).getType() == BlocType.VIDE)){
			assertTrue("Personnage n'a pas bien bougé", perso.getX() == Math.max(1, persoclone.getX() - 1));
		}
		if (com == Commande.HAUT && (moteur.getTerrain().getBloc(persoclone.getX(), persoclone.getY() - 1).getType() == BlocType.VIDE)){
			assertTrue("Personnage n'a pas bien bougé", perso.getY() == Math.max(1, persoclone.getY() - 1));
		}
		if (com == Commande.BAS && (moteur.getTerrain().getBloc(persoclone.getX(), persoclone.getY() + 1).getType() == BlocType.VIDE)){
			assertTrue("Personnage n'a pas bien bougé", perso.getY() == Math.min(moteur.getTerrain().getNombreLignes(), persoclone.getY() + 1));
		}
		if (com == Commande.BAS){
			assertTrue("Incohérence ordonnée personnage", perso.getX() == persoclone.getX());
		}
		if (com == Commande.HAUT){
			assertTrue("Incohérence ordonnée personnage", perso.getX() == persoclone.getX());
		}
		if (com == Commande.DROITE){
			assertTrue("Incohérence ordonnée personnage", perso.getY() == persoclone.getY());
		}
		if (com == Commande.GAUCHE){
			assertTrue("Incohérence ordonnée personnage", perso.getY() == persoclone.getY());
		}
		if (clone.getTerrain().getBloc(perso.getX(), perso.getY()).getPowerUpType() == PowerUpType.BOMBUP){
			assertTrue("BombUp non récupéré", persoclone.getNbBombes() + 1== perso.getNbBombes()); 
		}
		if (clone.getTerrain().getBloc(perso.getX(), perso.getY()).getPowerUpType() == PowerUpType.FIREUP && persoclone.getForceVitale() != 11){
			assertTrue("FireUp non récupéré", persoclone.getForceVitale() + 2== perso.getForceVitale()); 
		}
		if (clone.getTerrain().getBloc(perso.getX(), perso.getY()).getPowerUpType() == PowerUpType.FIREUP && persoclone.getForceVitale() == 11){
			assertTrue("FireUp appliqué alors que 11 = ForceVitale", 11 == perso.getForceVitale()); 
		}
		if (persoclone.getPowerUp() == PowerUpType.WALLPASS && clone.getTerrain().getBloc(persoclone.getX() + 1, persoclone.getY()).getType() == BlocType.MURBRIQUE && clone.getTerrain().getBloc(persoclone.getX() + 2, persoclone.getY()).getType() == BlocType.VIDE && com == Commande.DROITE){
			assertTrue("Wall pass non appliqué", perso.getX() == persoclone.getX() + 2);
		}
		if (persoclone.getPowerUp() == PowerUpType.WALLPASS && clone.getTerrain().getBloc(persoclone.getX() - 1, persoclone.getY()).getType() == BlocType.MURBRIQUE && clone.getTerrain().getBloc(persoclone.getX() - 2, persoclone.getY()).getType() == BlocType.VIDE && com == Commande.GAUCHE){
			assertTrue("Wall pass non appliqué", perso.getX() == persoclone.getX() - 2);
		}
		if (persoclone.getPowerUp() == PowerUpType.WALLPASS && clone.getTerrain().getBloc(persoclone.getX(), persoclone.getY() + 1).getType() == BlocType.MURBRIQUE && clone.getTerrain().getBloc(persoclone.getX(), persoclone.getY() +2).getType() == BlocType.VIDE && com == Commande.BAS){
			assertTrue("Wall pass non appliqué", perso.getY() == persoclone.getY() + 2);
		}
		if (persoclone.getPowerUp() == PowerUpType.WALLPASS && clone.getTerrain().getBloc(persoclone.getX(), persoclone.getY() -1).getType() == BlocType.MURBRIQUE && clone.getTerrain().getBloc(persoclone.getX(), persoclone.getY() -2).getType() == BlocType.VIDE && com == Commande.HAUT){
			assertTrue("Wall pass non appliqué", perso.getY() == persoclone.getY() - 2);
		}
		Integer[] coords= {perso.getX(),perso.getY()};
		if (clone.getHashBombes().get(coords) != null){  
			assertTrue("Sur une bombe sans BOMBPASS", persoclone.getPowerUp() == PowerUpType.BOMBPASS);
		}
		if (persoclone.getPowerUp() == PowerUpType.FIRESUIT && clone.getTerrain().getBloc(perso.getX(), perso.getY()).getPowerUpType() == PowerUpType.RIEN){
			assertTrue("Compteur FireSuit non décrémenté", persoclone.getCompteurFireSuit() == persoclone.getCompteurFireSuit() - 1);
		}
		if (clone.getTerrain().getBloc(perso.getX(), perso.getY()).getPowerUpType() != PowerUpType.RIEN){
			assertTrue("PowerUp non récupéré", persoclone.getPowerUp() == clone.getTerrain().getBloc(perso.getX(),perso.getY()).getPowerUpType());
		}
		if (com == Commande.BOMBE){
			System.out.println(moteur.getHashBombes().containsKey(coords));
			assertTrue("Bombe non placé",moteur.getHashBombes().get(coords) != null);
		}
		if (com == Commande.BOMBE){
			assertTrue("Bombe a mauvaise amplitude",moteur.getHashBombes().get(coords).getAmplitude() != perso.getForceVitale());
		}
		if (com == Commande.BOMBE){
			assertTrue("Perso a bougé alors qu'il a posé une bombe",persoclone.getX() == perso.getX() && persoclone.getY() == perso.getY());
		}
		for (BombeService bombe : imminentes){
			if (moteur.misEnJoue(persoclone.getX(), persoclone.getY(), bombe.getNumero()) && persoclone.getPowerUp() != PowerUpType.FIRESUIT){
				assertTrue("Joueur non mort alors qu'il n'a pas FireSuit", perso.getSante() == Sante.MORT);
			}
			assertTrue("Bombe n'a pas disparu après pasdeJeu",!moteur.getBombes().contains(bombe));
			for (int i = 1; i<moteur.getTerrain().getNombreColonnes() -2 ;i++){
				for (int j = 1; j<moteur.getTerrain().getNombreLignes() - 2; j++){
				if(moteur.misEnJoue(i, j, bombe.getNumero()) && clone.getTerrain().getBloc(i, j).getType()==BlocType.MURBRIQUE){
					assertTrue("Bloc pas modifié après explosion bombe", moteur.getTerrain().getBloc(i, j).getType() == BlocType.VIDE);
				}
				if(moteur.misEnJoue(i, j, bombe.getNumero()) && clone.getTerrain().getBloc(i, j).getPowerUpType()!=PowerUpType.RIEN){
					assertTrue("Bloc pas modifié après explosion bombe", moteur.getTerrain().getBloc(i, j).getPowerUpType() == PowerUpType.RIEN);
				}
				}
			}	
		}
		if (moteur.getHerosCommande() == Commande.BOMBE && moteur.getKidnappeurCommande() == Commande.BOMBE){
			assertTrue("nombre de bombes mal incrémentés",moteur.getNbBombes() == tranquilles.size() + 2);
		}
		if (moteur.getHerosCommande() == Commande.BOMBE && moteur.getKidnappeurCommande() != Commande.BOMBE){
			assertTrue("nombre de bombes mal incrémentés",moteur.getNbBombes() == tranquilles.size() + 1);
		}
		Commande comvil;
		for (VilainService vil : moteur.getVilains()){
			comvil = (Commande) moteur.getVilainsCommande().get(vil);		
		if (moteur.getTerrain().getBloc(oldCoordsVilains.get(vil)[0] + 1, oldCoordsVilains.get(vil)[1]).getType() == BlocType.VIDE && comvil == Commande.DROITE){
			assertTrue("Vilain a mal bougé", vil.getX() == oldCoordsVilains.get(vil)[0] + 1);
			}
		if (moteur.getTerrain().getBloc(oldCoordsVilains.get(vil)[0] - 1, oldCoordsVilains.get(vil)[1]).getType() == BlocType.VIDE && comvil == Commande.GAUCHE){
			assertTrue("Vilain a mal bougé", vil.getX() == oldCoordsVilains.get(vil)[0] - 1);
			}
		if (moteur.getTerrain().getBloc(oldCoordsVilains.get(vil)[0], oldCoordsVilains.get(vil)[1] -1 ).getType() == BlocType.VIDE && comvil == Commande.HAUT){
			assertTrue("Vilain a mal bougé", vil.getY() == oldCoordsVilains.get(vil)[1] - 1);
			}
		if (moteur.getTerrain().getBloc(oldCoordsVilains.get(vil)[0], oldCoordsVilains.get(vil)[1] +1).getType() == BlocType.VIDE && comvil == Commande.BAS){
			assertTrue("Vilain a mal bougé", vil.getY() == oldCoordsVilains.get(vil)[1] + 1);
			}
		if (moteur.getTerrain().getBloc(oldCoordsVilains.get(vil)[0] + 1, oldCoordsVilains.get(vil)[1]).getType() == BlocType.MURBRIQUE && vil.getType() == VilainType.FANTOMEBLEU && comvil == Commande.DROITE){
			assertTrue("Vilain a mal bougé", vil.getX() == oldCoordsVilains.get(vil)[0] + 1);
			}
		if (moteur.getTerrain().getBloc(oldCoordsVilains.get(vil)[0] - 1, oldCoordsVilains.get(vil)[1]).getType() == BlocType.MURBRIQUE && vil.getType() == VilainType.FANTOMEBLEU && comvil == Commande.GAUCHE){
			assertTrue("Vilain a mal bougé", vil.getX() == oldCoordsVilains.get(vil)[0] - 1);
			}
		if (moteur.getTerrain().getBloc(oldCoordsVilains.get(vil)[0], oldCoordsVilains.get(vil)[1] -1 ).getType() == BlocType.MURBRIQUE && vil.getType() == VilainType.FANTOMEBLEU && comvil == Commande.HAUT){
			assertTrue("Vilain a mal bougé", vil.getY() == oldCoordsVilains.get(vil)[1] - 1);
			}
		if (moteur.getTerrain().getBloc(oldCoordsVilains.get(vil)[0], oldCoordsVilains.get(vil)[1] +1).getType() == BlocType.MURBRIQUE && vil.getType() == VilainType.FANTOMEBLEU && comvil == Commande.BAS){
			assertTrue("Vilain a mal bougé", vil.getY() == oldCoordsVilains.get(vil)[1] + 1);
			}
		
		}
		}
	}
	}
}
