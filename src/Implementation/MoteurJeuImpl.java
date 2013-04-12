package Implementation;

import java.util.ArrayList;


import java.util.HashMap;


import Error.InvariantError;
import Services.BlocType;
import Services.BombeService;
import Services.Commande;
import Services.MoteurJeuService;
import Services.PersonnageJouableService;
import Services.PersonnageService;
import Services.PersonnageType;
import Services.PowerUpType;
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
	private PersonnageJouableService heros;
	private PersonnageJouableService kidnappeur;
	private int pasJeuCourant;
	private int maxiPasJeu;
	private TerrainService plateau = new Terrain();
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
		bindTerrainService(plateau);
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
			VilainService vilain = new Vilain();
			bindVilainService(vilain);
		}
		boolean ballonOrange = true;
		for (VilainService vil : vilains){
			//CHANGEMENT A FAIRE
			if (ballonOrange){
				vil.init((int)Math.random()*(plateaujeu.getNombreLignes()-2) + 1,(int)Math.random()*(plateaujeu.getNombreColonnes()-2) +1,VilainType.BALLONORANGE);
			}else{
				vil.init((int)Math.random()*(plateaujeu.getNombreLignes()-2) + 1,(int)Math.random()*(plateaujeu.getNombreColonnes()-2) +1,VilainType.FANTOMEBLEU);	
				
			}
		}
		//Mise en place des murs metalliques 
		int i = 0;
		for (int j=0;j<plateaujeu.getNombreColonnes()-1;j++){
			plateaujeu.getBloc(i, j).setType(BlocType.MURMETAL); 
		}
		int j=0;
		for (i =0;i<plateaujeu.getNombreLignes()-1;i++){
			plateaujeu.getBloc(i, j).setType(BlocType.MURMETAL);
		}
		j = plateaujeu.getNombreColonnes() -1;
		for (i=0;i<plateaujeu.getNombreLignes()-1;i++){
			plateaujeu.getBloc(i, j).setType(BlocType.MURMETAL);
		}
		i = plateaujeu.getNombreLignes() - 1;
		for (j=0;j<plateaujeu.getNombreLignes()-1;j++){
			plateaujeu.getBloc(i, j).setType(BlocType.MURMETAL);
		}
		for (i=1 ; i<plateaujeu.getNombreLignes() - 2; i++){
			for (j=1;j<plateaujeu.getNombreColonnes()-2; j++){
				if ( (i%2 == 1) && (j%2 == 1)){
					plateaujeu.getBloc(i,j).setType(BlocType.MURMETAL);
				}
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
		//Verifie si la partie est terminée
		if (!estFini()){
		// Explosion des bombes
		for (BombeService bom : bombes){
			if (bom.vaExploser()){
				//Morts des Personnages Jouables
				for (PersonnageJouableService perso : getListeJoueurs()){
					if(misEnJoue(perso.getX(), perso.getY(), bom.getNumero()) && perso.getPowerUp() != PowerUpType.FIRESUIT){
						perso.setSante(Sante.MORT);
					}
				}
				//Morts des Vilains
				for (VilainService vil : getVilains()){
					if(misEnJoue(vil.getX(),vil.getY(),bom.getNumero())){
						vil.setSante(Sante.MORT);
					}
				}
				int j = bom.getY();
				//Changement du terrain après explosion
				for (int i = bom.getX() - bom.getAmplitude(); i<bom.getX() + bom.getAmplitude(); i++){
					if (i < 0){
						i++;
					} else if (i>=plateaujeu.getNombreColonnes()){
						break;
					}
					else{
						if (misEnJoue(i, j, bom.getNumero())){
							if (plateaujeu.getBloc(i, j).getType() == BlocType.MURBRIQUE){
								plateaujeu.getBloc(i, j).setType(BlocType.VIDE);
							} else if(plateaujeu.getBloc(i, j).getPowerUpType() != PowerUpType.RIEN && plateaujeu.getBloc(i, j).getType() == BlocType.MURBRIQUE){
								plateaujeu.getBloc(i, j).setPowerUpType(PowerUpType.RIEN);
							}
						}
					}
				}
				int i = bom.getX();
				for (j = bom.getY() - bom.getAmplitude(); j<bom.getY() + bom.getAmplitude(); j++){
					if (j < 0){
						j++;
					} else if (j>=plateaujeu.getNombreColonnes()){
						break;
					}
					else{
						if (misEnJoue(i, j, bom.getNumero())){
							if (plateaujeu.getBloc(i, j).getType() == BlocType.MURBRIQUE){
								plateaujeu.getBloc(i, j).setType(BlocType.VIDE);
							} else if(plateaujeu.getBloc(i, j).getPowerUpType() != PowerUpType.RIEN && plateaujeu.getBloc(i, j).getType() == BlocType.MURBRIQUE){
								plateaujeu.getBloc(i, j).setPowerUpType(PowerUpType.RIEN);
							}
						}
					}
				}
				bombes.remove(bom);
				Integer[] coordsbombes = {bom.getX(),bom.getY()};
				hashbombes.remove(coordsbombes);
			} else {
			bom.dimCompteARebours();
			}
		}
		
		//Deplacement des personnages
		for (PersonnageJouableService perso : getListeJoueurs()){
			Commande comande = perso.getCommande();
			int xperso = perso.getX();
			int yperso = perso.getY();
			switch (comande){
			case DROITE:
				Integer [] coordscote = {xperso+1, yperso};
				if (perso.getPowerUp() != PowerUpType.WALLPASS && perso.getPowerUp() != PowerUpType.BOMBPASS){
					if (plateaujeu.getBloc(xperso + 1, yperso).getType() == BlocType.VIDE && !(hashbombes.containsKey(coordscote))){
						perso.setX(xperso + 1);
					}
				}else if (perso.getPowerUp() == PowerUpType.WALLPASS){
					if (plateaujeu.getBloc(xperso + 1, yperso).getType() == BlocType.MURBRIQUE && plateaujeu.getBloc(xperso + 2, yperso).getType() == BlocType.VIDE ){
						perso.setX(xperso + 2);
				}else if (perso.getPowerUp() == PowerUpType.BOMBPASS){
					if (plateaujeu.getBloc(xperso + 1, yperso).getType() == BlocType.VIDE && (hashbombes.containsKey(coordscote))){
						perso.setX(xperso + 1);
					}
				}
				}
			break;
			case GAUCHE:
			break;
			case HAUT:
				break;
			case BAS:
			
				
			break;
			
			
			case BOMBE:
			break;
			
			}
			
			
		}
		
		
		
		}else{
			resultatFinal();
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
		//A ameliorer
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
	public void addHashBombes(Integer[] coords,BombeService bombe){
		hashbombes.put(coords, bombe);
	}

	@Override
	public ArrayList<BombeService> getBombesImminentes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<VilainService> getVilains() {
		// TODO Auto-generated method stub
		return null;
	}
	}
