package MoteurJeuImplementation;

import java.util.ArrayList;


import java.util.HashMap;


import Services.BlocType;
import Services.BombeService;
import Services.Commande;
import Services.MoteurJeuService;
import Services.PersonnageJouableService;
import Services.PersonnageService;
import Services.PersonnageType;
import Services.RequirePersonnageJouableService;
import Services.RequireTerrainService;
import Services.RequireVilainService;
import Services.Resultat;
import Services.Sante;
import Services.TerrainService;
import Services.VilainService;
import Services.VilainType;

/**
 * 
 * Implémentation du moteur de jeu
 * 
 * @author Antoine FLINOIS
 *
 */
public class MoteurJeuImpl implements MoteurJeuService, RequirePersonnageJouableService, RequireTerrainService,
	RequireVilainService{
	private ArrayList<PersonnageJouableService> persos = new ArrayList<PersonnageJouableService>();
	private ArrayList<VilainService> vilains = new ArrayList<VilainService>();
	private VilainService vilain;
	private PersonnageJouableService heros;
	private PersonnageJouableService kidnappeur;
	private int pasJeuCourant;
	private int maxiPasJeu;
	private TerrainService plateaujeu;
	private ArrayList<Integer> indexbombes;
	private ArrayList<BombeService> bombes;
	private HashMap<Integer[],BombeService> hashbombes;
	private HashMap vilainscoords;
	private BombeService bombe;
	private int nbBombesKidnappeur;
	private int nbBombesHeros;

	@Override
	public int getPasJeuCourant() {
		return pasJeuCourant;
	}

	@Override
	public int getMaxPasJeu() {
		return maxiPasJeu;
	}
	

	@Override
	public void init(int maxPasJeu) {
		bindPersonnageJouableService(heros);
		bindPersonnageJouableService(kidnappeur);
		bindTerrainService(plateaujeu);
		plateaujeu.init(15, 13);
		pasJeuCourant = 0;
		maxiPasJeu = maxPasJeu;
		boolean hero = true;
		for (PersonnageJouableService perso : persos){
			if (hero) {
				heros = perso;
				heros.init(1, 1, PersonnageType.HEROS);
				heros.setForceVitale(3);
				heros.setBombe(1);
				hero = false;
			} else {
				kidnappeur = perso;
				kidnappeur.init(getTerrain().getNombreColonnes() - 2, getTerrain().getNombreColonnes() - 2, PersonnageType.MECHANT);
				kidnappeur.setBombe(1);
				kidnappeur.setForceVitale(3);
				
			}
		}
		// Initialisation des coordonnees des vilains
		for (int j=0;j<4;j++){
			bindVilainService(vilain);
		}
		boolean ballonOrange = true;
		for (VilainService vil : vilains){
			if (ballonOrange){
				vil.init((int)Math.random()*(plateaujeu.getNombreLignes()-2) + 1,(int)Math.random()*(plateaujeu.getNombreColonnes()-2) +1,VilainType.BALLONORANGE);
			}else{
				vil.init((int)Math.random()*(plateaujeu.getNombreLignes()-2) + 1,(int)Math.random()*(plateaujeu.getNombreColonnes()-2) +1,VilainType.FANTOMEBLEU);	
				
	}
		}
	
		
		
	}
	public PersonnageJouableService getHeros(){
		return heros;
	}

	public PersonnageJouableService getKidnappeur(){
		return kidnappeur;
	}
	
	@Override
	public void pasJeu() {
		
		for (BombeService bom : bombes){
			bom.dimCompteARebours();
			if (bom.vaExploser()){
				
			}
		}
		
	}

	@Override
	public TerrainService getTerrain() {
		return plateaujeu;
		}

	@Override
	public Sante getHerosSante() {
		return heros.getSante();
	}
	@Override
	public Sante getKidnappeurSante() {
		return kidnappeur.getSante();
	}

	@Override
	public int getHerosForceVitale() {
		return heros.getForceVitale();
		}
	

	@Override
	public int getKidnappeurForceVitale() {
		return kidnappeur.getForceVitale();
		}
	

	@Override
	public boolean bombeExiste(int num) {
		if (bombes.get(num) != null){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public int getNbBombes() {
		return bombes.size();
	}

	@Override
	public BombeService getBombe(int num) {
		return bombes.get(num);
	}

	@Override
	public ArrayList<BombeService> getBombes() {
		return bombes;
	}
	
	@Override
	public boolean estFini() {
		if (heros.getSante() == Sante.MORT || kidnappeur.getSante() == Sante.MORT || pasJeuCourant == getMaxPasJeu()){
			return true;
		}
		return false;
	}

	@Override
	public Resultat resultatFinal() {
		if (getHerosSante() == Sante.MORT){
			return Resultat.KIDNAPPEURGAGNE;
		}else if (getKidnappeurSante() == Sante.MORT){
			return Resultat.HEROSGAGNE;
		}else{
			return Resultat.PARTIENULLE;
		}
	}


	@Override
	public ArrayList<Integer> getBombeNumeros() {
		ArrayList<Integer> numbombes = new ArrayList<Integer>();
		for(BombeService bom : bombes){
			numbombes.add(bom.getNumero());
		}
		return numbombes;
	}

	@Override
	public boolean misEnJoue(int x, int y, int num) {
		int xB = getBombe(num).getX();
		int yB = getBombe(num).getY();
		int aB = getBombe(num).getAmplitude();
		if ((x==xB && Math.abs(y-yB) < aB) || (y==yB && Math.abs(x-xB) < aB)){
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void bindPersonnageJouableService(PersonnageJouableService personne) {
		persos.add(personne);
	}

	@Override
	public void bindTerrainService(TerrainService terrain) {
		plateaujeu = terrain;
		
	}

	@Override
	public void bindVilainService(VilainService vilain) {
		vilains.add(vilain);
	}

	@Override
	public ArrayList<PersonnageJouableService> getListeJoueurs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<Integer[], BombeService> getHashBombes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<BombeService> getBombesImminentes() {
		// TODO Auto-generated method stub
		return null;
	}
	}
