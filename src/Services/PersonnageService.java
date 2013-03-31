package Services;

public interface PersonnageService {
	
	public void init(int x, int y);
	public Sante getSante();
	public Commande getCommande();
	public int getX();
	public int getY();
	public void setX();
	public void setY();
}
