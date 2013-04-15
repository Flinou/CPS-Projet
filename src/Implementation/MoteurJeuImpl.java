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
	RequireVilainService, Cloneable{
	private ArrayList<PersonnageJouableService> persos = new ArrayList<PersonnageJouableService>();
	private ArrayList<VilainService> vilains = new ArrayList<VilainService>();
	private PersonnageJouableService heros = new PersonnageJouableImpl();
	private PersonnageJouableService kidnappeur = new PersonnageJouableImpl();
	private int pasJeuCourant;
	private int maxiPasJeu;
	private Commande herosCommande;
	private Commande kidnappeurCommande;
	private TerrainService plateau = new TerrainImpl();
	private TerrainService plateaujeu = new TerrainImpl();
	private ArrayList<BombeService> bombes = new ArrayList();
	private HashMap<Integer[],BombeService> hashbombes = new HashMap();
	public Commande commande;
	private HashMap <VilainService, Commande> HashVilComm = new HashMap();

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
		heros.init(1, 1, PersonnageType.HEROS);
		heros.setForceVitale(3);
		heros.setBombe(1);
		heros.setX(2);
		heros.setY(2);
		kidnappeur.init(10,10, PersonnageType.MECHANT);
		kidnappeur.setBombe(1);
		kidnappeur.setForceVitale(3);
		kidnappeur.setX(10);
		kidnappeur.setY(10);
		// Initialisation des coordonnees des vilains
		for (int j=0;j<4;j++){
			VilainService vilain = new VilainImpl();
			bindVilainService(vilain);
		}
		boolean ballonOrange = true;
		for (VilainService vil : vilains){
			if (ballonOrange){
				vil.init(6,6,VilainType.BALLONORANGE);	
			} else{
				vil.init(6,6,VilainType.FANTOMEBLEU);
				}
			ballonOrange = !ballonOrange;
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
		Integer[] coordperso = new Integer[2];
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
			Commande comande;
			if (perso.getType() == PersonnageType.HEROS){
				herosCommande = perso.getCommande();
				comande = herosCommande;
			} else{
				kidnappeurCommande = perso.getCommande();
				comande = kidnappeurCommande;
			}
			int xperso = perso.getX();
			int yperso = perso.getY();
			PowerUpType powerup = PowerUpType.RIEN;
			coordperso[0]=xperso;
			coordperso[1]=yperso;
			switch (comande){
			case DROITE:
				Integer [] coordsdroit = {xperso+1, yperso};
				if (perso.getPowerUp() != PowerUpType.WALLPASS && perso.getPowerUp() != PowerUpType.BOMBPASS){
					if (plateaujeu.getBloc(xperso + 1, yperso).getType() == BlocType.VIDE && !(hashbombes.containsKey(coordsdroit))){
						perso.setX(xperso + 1);
					}
				}else if (perso.getPowerUp() == PowerUpType.WALLPASS){
					if (plateaujeu.getBloc(xperso + 1, yperso).getType() == BlocType.MURBRIQUE && plateaujeu.getBloc(xperso + 2, yperso).getType() == BlocType.VIDE ){
						perso.setX(xperso + 2);
				}else if (perso.getPowerUp() == PowerUpType.BOMBPASS){
					if (plateaujeu.getBloc(xperso + 1, yperso).getType() == BlocType.VIDE && (hashbombes.containsKey(coordsdroit))){
						perso.setX(xperso + 1);
					}
				}
			}
			break;
			case GAUCHE:
				Integer [] coordsgauche = {xperso-1, yperso};
				if (perso.getPowerUp() != PowerUpType.WALLPASS && perso.getPowerUp() != PowerUpType.BOMBPASS){
					if (plateaujeu.getBloc(xperso - 1, yperso).getType() == BlocType.VIDE && !(hashbombes.containsKey(coordsgauche))){
						perso.setX(xperso - 1);
					}
				}else if (perso.getPowerUp() == PowerUpType.WALLPASS){
					if (plateaujeu.getBloc(xperso + 1, yperso).getType() == BlocType.MURBRIQUE && plateaujeu.getBloc(xperso - 2, yperso).getType() == BlocType.VIDE ){
						perso.setX(xperso - 2);
				}else if (perso.getPowerUp() == PowerUpType.BOMBPASS){
					if (plateaujeu.getBloc(xperso + 1, yperso).getType() == BlocType.VIDE && (hashbombes.containsKey(coordsgauche))){
						perso.setX(xperso - 1);
					}
				}
			}
			break;
			case HAUT:
				Integer [] coordshaut = {xperso, yperso - 1};
				if (perso.getPowerUp() != PowerUpType.WALLPASS && perso.getPowerUp() != PowerUpType.BOMBPASS){
					if (plateaujeu.getBloc(xperso, yperso - 1).getType() == BlocType.VIDE && !(hashbombes.containsKey(coordshaut))){
						perso.setY(yperso - 1);
					}
				}else if (perso.getPowerUp() == PowerUpType.WALLPASS){
					if (plateaujeu.getBloc(xperso, yperso - 1).getType() == BlocType.MURBRIQUE && plateaujeu.getBloc(xperso, yperso - 2).getType() == BlocType.VIDE ){
						perso.setY(yperso - 2);
				}else if (perso.getPowerUp() == PowerUpType.BOMBPASS){
					if (plateaujeu.getBloc(xperso, yperso - 1).getType() == BlocType.VIDE && (hashbombes.containsKey(coordshaut))){
						perso.setY(yperso - 1);
					}
				}
			}
				break;
			case BAS:
				Integer [] coordsbas = {xperso, yperso + 1};
				if (perso.getPowerUp() != PowerUpType.WALLPASS && perso.getPowerUp() != PowerUpType.BOMBPASS){
					if (plateaujeu.getBloc(xperso, yperso + 1).getType() == BlocType.VIDE && !(hashbombes.containsKey(coordsbas))){
						perso.setY(yperso + 1);
					}
				}else if (perso.getPowerUp() == PowerUpType.WALLPASS){
					if (plateaujeu.getBloc(xperso, yperso + 1).getType() == BlocType.MURBRIQUE && plateaujeu.getBloc(xperso, yperso + 2).getType() == BlocType.VIDE ){
						perso.setY(yperso + 2);
				}else if (perso.getPowerUp() == PowerUpType.BOMBPASS){
					if (plateaujeu.getBloc(xperso, yperso - 1).getType() == BlocType.VIDE && (hashbombes.containsKey(coordsbas))){
						perso.setY(yperso + 1);
					}
				}
			}
			break;
			case BOMBE:
			BombeService bombe = new Bombe();
			bombe.init(getNbBombes() + 1, xperso, yperso, perso.getForceVitale());
			addHashBombes(coordperso, bombe);
			bombes.add(bombe);
			break;
			}
			//Recuperation du PowerUp présent sur la case
			if (plateaujeu.getBloc(perso.getX(), perso.getY()).getPowerUpType() != PowerUpType.RIEN){
				powerup = plateaujeu.getBloc(perso.getX(), perso.getY()).getPowerUpType();
				perso.setPowerUp(powerup);
			}
			// Actions en fonction du powerup obtenu
			switch (powerup){
			case BOMBUP:
				perso.addBombe();
				break;
			case FIRESUIT:
				perso.setCompteurFireSuit(100);
				break;
			case FIREUP:
				if (perso.getForceVitale() <= 9){
					perso.setForceVitale(perso.getForceVitale() +2);
				break;
			}
			}
		}
			
			//Deplacement des vilains
			for (VilainService vil : getVilains()){
			Commande com = vil.getCommande();
			HashVilComm.put(vil, com);
			int xvilain = vil.getX();
			int yvilain = vil.getY();
			switch (com){
			case DROITE : 
				if ((vil.getType() == VilainType.BALLONORANGE) && (plateaujeu.getBloc(xvilain + 1, yvilain).getType() == BlocType.VIDE)){
					vil.setX(xvilain + 1);
				} else if ((vil.getType() == VilainType.FANTOMEBLEU) && (plateaujeu.getBloc(xvilain + 1, yvilain).getType() != BlocType.MURMETAL)){
					vil.setX(xvilain + 1);
				}
				break;
			case GAUCHE : 
				if ((vil.getType() == VilainType.BALLONORANGE) && (plateaujeu.getBloc(xvilain - 1, yvilain).getType() == BlocType.VIDE)){
					vil.setX(xvilain - 1);
				} else if ((vil.getType() == VilainType.FANTOMEBLEU) && (plateaujeu.getBloc(xvilain - 1, yvilain).getType() != BlocType.MURMETAL)){
					vil.setX(xvilain - 1);
				}
				break;
			case HAUT : 
				if ((vil.getType() == VilainType.BALLONORANGE) && (plateaujeu.getBloc(xvilain, yvilain - 1).getType() == BlocType.VIDE)){
					vil.setY(yvilain - 1);
				} else if ((vil.getType() == VilainType.FANTOMEBLEU) && (plateaujeu.getBloc(xvilain, yvilain - 1).getType() != BlocType.MURMETAL)){
					vil.setY(yvilain - 1);
				}
				break;
			case BAS : 
				if ((vil.getType() == VilainType.BALLONORANGE) && (plateaujeu.getBloc(xvilain, yvilain + 1).getType() == BlocType.VIDE)){
					vil.setY(yvilain + 1);
				} else if ((vil.getType() == VilainType.FANTOMEBLEU) && (plateaujeu.getBloc(xvilain, yvilain + 1).getType() != BlocType.MURMETAL)){
					vil.setY(yvilain + 1);
				}
				break;		
			}
			//Personnage meurt s'il croise un vilain
			for (PersonnageJouableService perso : getListeJoueurs()){
				if ((vil.getX() == coordperso[0] && vil.getY() == coordperso[1] && perso.getX() == xvilain && perso.getY() == yvilain) || (vil.getX() == perso.getX() && vil.getY() == perso.getY())){
					perso.setSante(Sante.MORT);
				}
			}
		}
			pasJeuCourant ++;
			
		
		}else{
		resultatFinal();
		System.out.println("Fin de la partie");
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
	public boolean bombeExiste(int num) {
		if (getBombe(num) != null){
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
		for (BombeService bom : getBombes()){
			if (bom.getNumero() == num){
				return bom;
			}
		}
		return null;
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
			if (x==xB){
				if (y<yB){
					for (int i=y+1;i<yB;i++){
						if (plateaujeu.getBloc(x, i).getType() == BlocType.MURMETAL || plateaujeu.getBloc(x, i).getPowerUpType() != PowerUpType.RIEN){
							return false;
						}
					}
				} else if (y>yB){
					for (int i=y-1;i>yB;i--){
						if (plateaujeu.getBloc(x, i).getType() == BlocType.MURMETAL || plateaujeu.getBloc(x, i).getPowerUpType() != PowerUpType.RIEN){
							return false;
						}
					}
				}
			} else if (y == yB){
				if (x<xB){
					for (int i=x+1;i<xB;i++){
						if (plateaujeu.getBloc(i, y).getType() == BlocType.MURMETAL || plateaujeu.getBloc(i, y).getPowerUpType() != PowerUpType.RIEN){
							return false;
						}
					}
				} else if (x>xB){
					for (int i=x-1;i>xB;i--){
						if (plateaujeu.getBloc(i, y).getType() == BlocType.MURMETAL || plateaujeu.getBloc(i, y).getPowerUpType() != PowerUpType.RIEN){
							return false;
						}
					}
				}
			}
		} else {
			return false;
		}
		return true;
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
		return persos;
	}

	@Override
	public HashMap<Integer[], BombeService> getHashBombes() {
		return hashbombes;
	}
	public void addHashBombes(Integer[] coords,BombeService bombe){
		hashbombes.put(coords, bombe);
	}

	@Override
	public ArrayList<VilainService> getVilains() {
		return vilains;
	}
	
	public MoteurJeuImpl clone(){
		MoteurJeuImpl clone = new MoteurJeuImpl();;
		try {
		    clone = (MoteurJeuImpl) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clone;
	}

	@Override
	public Commande getHerosCommande() {
		return herosCommande;
	}

	@Override
	public Commande getKidnappeurCommande() {
		return kidnappeurCommande;
	}
	
	public HashMap getVilainsCommande(){
		return HashVilComm;
	}
	
	
	}

