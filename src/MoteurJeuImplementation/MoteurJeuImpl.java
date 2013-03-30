package MoteurJeuImplementation;

import java.util.ArrayList;

import java.util.HashMap;


import Services.BombeService;
import Services.Commande;
import Services.MoteurJeuService;
import Services.Resultat;
import Services.Sante;
import Services.TerrainService;
import Services.VilainService;

/**
 * 
 * Implémentation du moteur de jeu
 * 
 * @author Antoine FLINOIS
 *
 */
public class MoteurJeuImpl implements MoteurJeuService {
	private int pasJeuCourant;
	private int maxPasJeu;
	private int xheros;
	private int yheros;
	private int xkidnappeur;
	private int ykidnappeur;
	private Sante santeHeros;
	private Sante santeKidnappeur;
	private TerrainService terrain;
	private int herosforcevitale;
	private int kidnappeurforcevitale;
	private ArrayList<Integer> indexbombes;
	private ArrayList<BombeService> bombes;
	private HashMap vilainscoords;
	private BombeService bombe;
	private int nbBombesKidnappeur;
	private int nbBombesHeros;
	private VilainService vilain;

	@Override
	public int getPasJeuCourant() {
		return pasJeuCourant;
	}

	@Override
	public int getMaxPasJeu() {
		return maxPasJeu;
	}

	@Override
	public void init(int maxPasJeu) {
		herosforcevitale = 3;
		kidnappeurforcevitale = 3;
		terrain.init(15, 13);
		xheros = 1;
		yheros = 1;
		xkidnappeur = 1;
		ykidnappeur = 1;
		santeHeros = Sante.VIVANT;
		santeKidnappeur = Sante.VIVANT;
		nbBombesKidnappeur = 1;
		nbBombesHeros = 1;
		// Initialisation des coordonnees des vilains
		for (int j=0;j<4;j++){
			for (int i=2; i<terrain.getNombreColonnes() - 2; j++){
				for (int y=2; y<terrain.getNombreLignes() - 2; y++){
					if (terrain.getBloc(i,y).getType() == BlocType.VIDE){
							int [] coord = {i,y};
							vilainscoords.put(vilain, coord);
							break;
				}
				break;	
			}
		}
		
	}
	}


	@Override
	public int getHerosX() {
		return xheros;
		}

	@Override
	public int getHerosY() {
		return yheros;
		}

	@Override
	public int getKidnappeurX() {
		return xkidnappeur;
	}

	@Override
	public int getKidnappeurY() {
		return ykidnappeur;
	}

	@Override
	public void pasJeu(Commande com) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TerrainService getTerrain() {
		return terrain;
		}

	@Override
	public Sante getHerosSante() {
		return santeHeros;
	}
	@Override
	public Sante getKidnappeurSante() {
		return santeKidnappeur;
	}

	@Override
	public int getHerosForceVitale() {
		return herosforcevitale;
		}
	

	@Override
	public int getKidnappeurForceVitale() {
		return kidnappeurforcevitale;
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
		if (santeHeros == Sante.MORT || santeKidnappeur == Sante.MORT || pasJeuCourant == maxPasJeu){
			return true;
		}
		return false;
	}

	@Override
	public Resultat resultatFinal() {
		if (santeHeros == Sante.MORT){
			return Resultat.KIDNAPPEURGAGNE;
		}else if (santeKidnappeur == Sante.MORT){
			return Resultat.HEROSGAGNE;
		}else{
			return Resultat.PARTIENULLE;
		}
	}


	@Override
	public ArrayList<Integer> getBombeNumeros() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean misEnJoue(int x, int y, int num) {
		// TODO Auto-generated method stub
		return false;
	}


}
